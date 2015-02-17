/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.rp.hd.security;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.PermissionManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.credential.Token;
import org.picketlink.idm.model.Account;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Role;
import org.picketlink.idm.query.IdentityQuery;
import org.picketlink.idm.query.IdentityQueryBuilder;

/**
 * <p>
 * This class provides an abstraction point to the Identity Management
 * operations required by the application./p>
 *
 * <p>
 * The main objective of this class is avoid the spread use of the
 * <code>IdentityManager</code> by different components of the application and
 * code duplication, providing a centralized point of access for the most common
 * operations like create/update/query users and so forth.
 * </p>
 *
 * <p>
 * Also it is very useful to understand how PicketLink Identity Management is
 * being used and what is being used by the application from a IDM perspective.
 * </p>
 *
 * <p>
 * Please note that PicketLink IDM provides a very flexible and poweful identity
 * model and API, from which you can extend and fulfill your own requirements.
 * </p>
 *
 */
@Stateless
public class IdentityModelManager {

	@Inject
	private IdentityManager identityManager;

	@Inject
	private RelationshipManager relationshipManager;

	@Inject
	private Token.Provider<JWSToken> tokenProvider;

	@Inject
	private PartitionManager partitionManager;
	
//	@Inject
//	private EntityManager em;

	public static HdUser findByLoginName(String loginName,
			IdentityManager identityManager) {
		if (loginName == null) {
			throw new IllegalArgumentException("Invalid login name.");
		}

		IdentityQueryBuilder queryBuilder = identityManager.getQueryBuilder();
		IdentityQuery<HdUser> query = queryBuilder
				.createIdentityQuery(HdUser.class);

		query.where(queryBuilder.equal(HdUser.USER_NAME, loginName));

		List<HdUser> result = query.getResultList();

		if (!result.isEmpty()) {
			return result.get(0);
		}

		return null;
	}

	public HdUser createAccount(UserRegistration request) {
		if (!request.isValid()) {
			throw new IllegalArgumentException("Insuficient information.");
		}

		// Usuario person = new Usuario();
		//
		// person.setLogin(request.getEmail());
		// person.setNome(request.getFirstName() + " " + request.getLastName());

		HdUser newUser = new HdUser(request.getEmail());

		// newUser.setUsuario(person);

		String activationCode = UUID.randomUUID().toString();

		newUser.setActivationCode(activationCode); // we set an activation code
													// for future use.

		this.identityManager.add(newUser);

		updatePassword(newUser, request.getPassword());

		disableAccount(newUser);

		return newUser;
	}

	public void updatePassword(Account account, String password) {
		this.identityManager.updateCredential(account, new Password(password));
	}

	public void grantRole(HdUser account, String roleName) {
		Role storedRole = BasicModel.getRole(this.identityManager, roleName);
		
		BasicModel.grantRole(this.relationshipManager, account, storedRole);
	}

	public void configureRole() {
		Role role = BasicModel.getRole(this.identityManager,
				ApplicationRole.ADMINISTRATOR);
		PermissionManager pm = partitionManager.createPermissionManager();
		
		
		//pm.grantPermission(role, Telefone.class, "create");
//		pm.grantPermission(role, Telefone.class, "update");
//		pm.grantPermission(role, Telefone.class, "read");

		pm.grantPermission(role, "table-sbm_telefone-field-ddd", "update");
		pm.grantPermission(role, "table-sbm_telefone-field-ddd", "read");
		pm.grantPermission(role, "table-sbm_telefone-field-numero", "read");

	}

	public boolean hasRole(HdUser account, String roleName) {
		Role storedRole = BasicModel.getRole(this.identityManager, roleName);
		return BasicModel
				.hasRole(this.relationshipManager, account, storedRole);
	}

	public Token activateAccount(String activationCode) {
		HdUser user = findUserByActivationCode(activationCode);

		if (user == null) {
			throw new IllegalArgumentException("Invalid activation code.");
		}

		user.setEnabled(true);
		user.invalidateActivationCode();

		this.identityManager.update(user);

		return issueToken(user);
	}

	public HdUser findByLoginName(String loginName) {
		return findByLoginName(loginName, this.identityManager);
	}

	public HdUser findUserByActivationCode(String activationCode) {
		if (activationCode == null) {
			throw new IllegalArgumentException("Invalid activation code.");
		}

		IdentityQueryBuilder queryBuilder = identityManager.getQueryBuilder();
		IdentityQuery<HdUser> query = queryBuilder
				.createIdentityQuery(HdUser.class);
		List<HdUser> result = query.where(
				queryBuilder.equal(HdUser.ACTIVATION_CODE,
						activationCode.replaceAll("\"", ""))).getResultList();

		if (!result.isEmpty()) {
			return result.get(0);
		}

		return null;
	}

	public void disableAccount(HdUser user) {
		if (hasRole(user, ApplicationRole.ADMINISTRATOR)) {
			throw new IllegalArgumentException(
					"Administrators can not be disabled.");
		}

		user.setEnabled(false);

		if (user.getId() != null) {
			issueToken(user); // we invalidate the current token and create a
								// new one. so any token stored by clients will
								// be no longer valid.
			this.identityManager.update(user);
		}
	}

	public void enableAccount(HdUser user) {
		if (hasRole(user, ApplicationRole.ADMINISTRATOR)) {
			throw new IllegalArgumentException(
					"Administrators can not be enabled.");
		}

		user.setEnabled(true);
		user.invalidateActivationCode();

		if (user.getId() != null) {
			this.identityManager.update(user);
		}

	}

	private Token issueToken(Account account) {
		return this.tokenProvider.issue(account);
	}
}
