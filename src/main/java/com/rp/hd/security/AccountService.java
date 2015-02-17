package com.rp.hd.security;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/private/account")
@RolesAllowed(ApplicationRole.ADMINISTRATOR)
public class AccountService {

	@Inject
	private IdentityModelManager identityModelManager;

	@POST
	@Path("enableAccount")
	@Produces(MediaType.APPLICATION_JSON)
	public Response enable(Usuario usuario) {
		
		HdUser user = this.identityModelManager.findByLoginName(usuario
				.getLogin());

		if (user == null) {
			return MessageBuilder.badRequest().message("Invalid account.")
					.build();
		}

		if (user.isEnabled()) {
			return MessageBuilder.badRequest()
					.message("Account is already enabled.").build();
		}

		this.identityModelManager.enableAccount(user);

		return MessageBuilder.ok().message("Account is now enabled.").build();
	}

	@POST
	@Path("disableAccount")
	@Produces(MediaType.APPLICATION_JSON)
	public Response disable(Usuario usuario) {

		HdUser user = this.identityModelManager.findByLoginName(usuario
				.getLogin());

		if (user == null) {
			return MessageBuilder.badRequest().message("Invalid account.")
					.build();
		}

		if (!user.isEnabled()) {
			return MessageBuilder.badRequest()
					.message("Accound is already disabled.").build();
		}

		this.identityModelManager.disableAccount(user);

		return MessageBuilder.ok().message("Account is now disabled.").build();
	}
	
}
