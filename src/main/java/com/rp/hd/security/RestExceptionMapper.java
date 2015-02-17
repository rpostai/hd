package com.rp.hd.security;

import javax.ejb.EJBException;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.deltaspike.security.api.authorization.AccessDeniedException;
import org.picketlink.Identity;

@Provider
public class RestExceptionMapper implements ExceptionMapper<Throwable> {

	@Inject
	private Instance<Identity> identityInstance;

	@Override
	public Response toResponse(Throwable exception) {
		if (EJBException.class.isInstance(exception)) {
			exception = exception.getCause();
		}

		String message = exception.getMessage();

		if (message == null) {
			message = "Unexpected error from server.";
		}

		MessageBuilder builder;

		if (AccessDeniedException.class.isInstance(exception)) {
			if (getIdentity().isLoggedIn()) {
				builder = MessageBuilder.accessDenied().message(
						"Access Denied.");
			} else {
				builder = MessageBuilder.authenticationRequired();
			}
		} else {
			builder = MessageBuilder.badRequest();
		}

		return builder.message(message).build();
	}

	private Identity getIdentity() {
		return this.identityInstance.get();
	}
}
