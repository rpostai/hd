package com.rp.hd.domain.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
}
