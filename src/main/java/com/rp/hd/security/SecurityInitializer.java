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

import static org.picketlink.idm.model.basic.BasicModel.getRole;
import static org.picketlink.idm.model.basic.BasicModel.grantRole;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.picketlink.annotations.PicketLink;
import org.picketlink.event.PartitionManagerCreateEvent;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.PermissionManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.config.SecurityConfigurationException;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.Attribute;
import org.picketlink.idm.model.basic.Realm;
import org.picketlink.idm.model.basic.Role;

@Stateless
public class SecurityInitializer {

	public static final String KEYSTORE_FILE_PATH = "/keystore.jks";
	
	@Inject
	@PicketLink
	private EntityManager em;

	private KeyStore keyStore;

	public void configureDefaultPartition(
			@Observes PartitionManagerCreateEvent event) {
		PartitionManager partitionManager = event.getPartitionManager();

		createDefaultPartition(partitionManager);
		createDefaultRoles(partitionManager);
		createAdminAccount(partitionManager);
		createUserAccount(partitionManager);
	}
	
	private void createDefaultRoles(PartitionManager partitionManager) {
		IdentityManager identityManager = partitionManager
				.createIdentityManager();

		createRole(ApplicationRole.ADMINISTRATOR, identityManager);
		createRole(ApplicationRole.USER, identityManager);
	}

	private void createDefaultPartition(PartitionManager partitionManager) {
		Realm partition = partitionManager.getPartition(Realm.class,
				Realm.DEFAULT_REALM);

		if (partition == null) {
			try {
				partition = new Realm(Realm.DEFAULT_REALM);

				partition.setAttribute(new Attribute<byte[]>("PublicKey",
						getPublicKey()));
				partition.setAttribute(new Attribute<byte[]>("PrivateKey",
						getPrivateKey()));

				partitionManager.add(partition);
			} catch (Exception e) {
				throw new SecurityConfigurationException(
						"Could not create default partition.", e);
			}
		}
	}

	public static Role createRole(String roleName,
			IdentityManager identityManager) {
		Role role = getRole(identityManager, roleName);

		if (role == null) {
			role = new Role(roleName);
			identityManager.add(role);
			
			
		}

		return role;
	}

	private byte[] getPrivateKey() throws KeyStoreException,
			NoSuchAlgorithmException, UnrecoverableKeyException {
		return getKeyStore().getKey("servercert", "test123".toCharArray())
				.getEncoded();
	}

	private byte[] getPublicKey() throws KeyStoreException {
		return getKeyStore().getCertificate("servercert").getPublicKey()
				.getEncoded();
	}

	private KeyStore getKeyStore() {
		if (this.keyStore == null) {
			try {
				this.keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
				getKeyStore().load(
						getClass().getResourceAsStream(KEYSTORE_FILE_PATH),
						"store123".toCharArray());
			} catch (Exception e) {
				throw new SecurityException("Could not load key store.", e);
			}
		}

		return this.keyStore;
	}

	public void createAdminAccount(PartitionManager partitionManager) {
		IdentityManager identityManager = partitionManager
				.createIdentityManager();
		String email = "admin";

		// if admin exists dont create again
		if (IdentityModelManager.findByLoginName(email, identityManager) != null) {
			return;
		}

		Empresa e1 = em.find(Empresa.class, 1l);

		HdUser admin = new HdUser(email);

		admin.setEmpresa(e1);

		identityManager.add(admin);

		identityManager.updateCredential(admin, new Password("admin"));

		Role adminRole = getRole(identityManager, ApplicationRole.ADMINISTRATOR);
		
		RelationshipManager relationshipManager = partitionManager.createRelationshipManager();

		grantRole(relationshipManager, admin,
				adminRole);
		
//		PermissionManager pm = partitionManager.createPermissionManager();
//		pm.grantPermission(adminRole, Pessoa.class, "create");
//		pm.grantPermission(adminRole, Pessoa.class, "update");
//		pm.grantPermission(adminRole, Pessoa.class, "delete");
//		pm.grantPermission(adminRole, Pessoa.class, "read");
	}
	
	public void createUserAccount(PartitionManager partitionManager) {
		IdentityManager identityManager = partitionManager
				.createIdentityManager();
		String email = "user10";

		// if admin exists dont create again
		HdUser user = IdentityModelManager.findByLoginName(email, identityManager);
		RelationshipManager relationshipManager = partitionManager.createRelationshipManager();
		PermissionManager pm = partitionManager.createPermissionManager();
		if (user == null) {
			Empresa e1 = em.find(Empresa.class, 1l);
			user = new HdUser(email);
			user.setEmpresa(e1);
			identityManager.add(user);
			identityManager.updateCredential(user, new Password("user2"));
			//Role userRole = getRole(identityManager, ApplicationRole.USER);
		} 
		

		//SabiumUser user = new SabiumUser(email);

		//		pm.grantPermission(user, Telefone.class, "create");
//		pm.grantPermission(user, Telefone.class, "update");
//		pm.grantPermission(user, Telefone.class, "read");
		
		//pm.grantPermission(user, Modulo.class, "read");
		
//		pm.grantPermission(user, "sbm_modulos", "read");
//		pm.grantPermission(user, "sbm_modulos", "create");
//		pm.grantPermission(user, "sbm_modulos", "update");
//		pm.grantPermission(user, "sbm_modulos", "delete");
		
//		pm.grantPermission(user, "table-sbm_telefone-field-ddd", "update");
//		pm.grantPermission(user, "table-sbm_telefone-field-ddd", "read");
//		
//		pm.grantPermission(user, "table-sbm_telefone-field-numero", "read");
		
//		pm.grantPermission(user, "table-sbm_modulos-field-nome_interno", "read");
//		pm.grantPermission(user, "table-sbm_modulos-field-nome_externo", "read");
//		pm.grantPermission(user, "table-sbm_modulos-field-contexto", "read");
//		//pm.grantPermission(user, "table-sbm_modulos-field-versao", "read");
//		pm.grantPermission(user, "table-sbm_modulos-field-id_modulo_pai", "read");
		
		
//		pm.grantPermission(user, "table-sbm_empresa-field-cnpj", "read");
//		pm.grantPermission(user, "table-sbm_empresa-field-razao_social", "read");
//		
//		pm.grantPermission(user, "table-sbm_pessoa-field-id", "read");
//		pm.grantPermission(user, "table-sbm_pessoa-field-id", "update");
//		
//		pm.grantPermission(user, "table-sbm_pessoa-field-cpf", "read");
//		pm.grantPermission(user, "table-sbm_pessoa-field-cpf", "update");
//		
//		pm.grantPermission(user, "table-sbm_pessoa-field-nome", "read");
//		pm.grantPermission(user, "table-sbm_pessoa-field-nome", "update");
//		
//		pm.grantPermission(user, "sbm_telefone", "read");
//		pm.grantPermission(user, "sbm_telefone", "create");
//		pm.grantPermission(user, "sbm_telefone", "delete");
//		pm.grantPermission(user, "sbm_telefone", "update");
//		
//		pm.grantPermission(user, "sbm_pessoa", "15");
////		pm.grantPermission(user, "sbm_pessoa", "create");
////		pm.grantPermission(user, "sbm_pessoa", "delete");
////		pm.grantPermission(user, "sbm_pessoa", "update");
//		
//		pm.grantPermission(user, "table-sbm_pessoa-field-id", "read");
//		pm.grantPermission(user, "table-sbm_pessoa-field-id", "update");
//		
//		pm.grantPermission(user, "table-sbm_pessoa-field-nome", "read");
//		pm.grantPermission(user, "table-sbm_pessoa-field-nome", "update");
//		
//		pm.grantPermission(user, "table-sbm_pessoa-field-cpf", "read");
//		pm.grantPermission(user, "table-sbm_pessoa-field-cpf", "update");
//		
//		pm.grantPermission(user, "table-sbm_pessoa-field-cnpj", "read");
//		pm.grantPermission(user, "table-sbm_pessoa-field-cnpj", "update");
//		
//		pm.grantPermission(user, "table-sbm_pessoa-field-nome_fantasia", "read");
//		pm.grantPermission(user, "table-sbm_pessoa-field-nome_fantasia", "update");
//		
//		pm.grantPermission(user, "table-sbm_pessoa-field-razao_social", "read");
//		pm.grantPermission(user, "table-sbm_pessoa-field-razao_social", "update");
		
		
		//grantRole(relationshipManager, user,
		//		userRole);
		
		
	}
}
