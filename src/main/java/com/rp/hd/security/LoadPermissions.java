package com.rp.hd.security;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.picketlink.Identity;
import org.picketlink.authorization.annotations.LoggedIn;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.PermissionManager;
import org.picketlink.idm.permission.Permission;

@LoggedIn
@Stateless
@Path("permissions")
public class LoadPermissions {

	@Inject
	private Identity identity;

	@Inject
	private PartitionManager pm;

	@EJB
	private IdentityModelManager manager;

	@GET
	public Response loadPermissionsFromUser() {
		PermissionManager perm = pm.createPermissionManager();
		List<Permission> permissions = perm.listPermissions(identity
				.getAccount());
		long[] ids = new long[permissions.size()];
		int i = 0;
		for (Permission p : permissions) {
			ids[i++] = i;
		}
		// permissions.forEach(x -> {
		// ids
		// System.out.println("Seguranca da entidade: " +
		// x.getResourceIdentifier()
		// + " na operacão " + x.getOperation());
		// });
		Hashids h = new Hashids();
		String encodedSecurityContext = h.encode(ids);

		return Response.status(Status.OK).entity(encodedSecurityContext)
				.build();

		// manager.configureRole();
		//
		// manager.grantRole((SabiumUser)identity.getAccount(),
		// ApplicationRole.ADMINISTRATOR);

	}
}
