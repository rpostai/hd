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

import javax.enterprise.event.Observes;

import org.picketlink.config.SecurityConfigurationBuilder;
import org.picketlink.event.SecurityConfigurationEvent;
import org.picketlink.idm.jpa.model.sample.simple.AttributeTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.GroupTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.IdentityTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.PartitionTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.PasswordCredentialTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.PermissionTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.RelationshipIdentityTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.RelationshipTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.RoleTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.TokenCredentialTypeEntity;
import org.picketlink.idm.model.Relationship;
import org.picketlink.idm.model.basic.Grant;
import org.picketlink.idm.model.basic.GroupMembership;

/**
 * <p>
 * A simple CDI observer for the
 * {@link org.picketlink.event.SecurityConfigurationEvent}.
 * </p>
 *
 * <p>
 * The event is fired during application startup and allows you to provide any
 * configuration to PicketLink before it is initialized.
 * </p>
 *
 * <p>
 * All the configuration to PicketLink Identity Management is provided from this
 * bean.
 * </p>
 *
 */
public class IdentityManagementConfiguration {

	public void configureIdentityManagement(
			@Observes SecurityConfigurationEvent event) {
		SecurityConfigurationBuilder builder = event.getBuilder();

		builder.identity()
				.stateless()
				.http()
				.forPath("/services/*")
				.authenticateWith()
				.token()
				
				.forPath("/services/modulos")
				.unprotected()
				
				.forPath("/html/*")
				.authenticateWith()
				.token()
				
				.idmConfig()
				.named("default")
				.stores()
				.jpa()
				.mappedEntity(Usuario.class,RoleTypeEntity.class, GroupTypeEntity.class, IdentityTypeEntity.class, RelationshipTypeEntity.class,
						RelationshipIdentityTypeEntity.class, AttributeTypeEntity.class, PartitionTypeEntity.class,PasswordCredentialTypeEntity.class,TokenCredentialTypeEntity.class,
						PermissionTypeEntity.class
						)
				.supportGlobalRelationship(Relationship.class, Grant.class, GroupMembership.class)
				.supportType(HdUser.class).supportAllFeatures();
	}
}
