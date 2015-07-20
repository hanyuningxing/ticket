package com.cai310.security.springsecurity.jcaptcha;

import org.springframework.security.SpringSecurityException;

public class CaptchaAuthenticationException extends SpringSecurityException {
	private static final long serialVersionUID = -3840688454616655858L;

	public CaptchaAuthenticationException(String msg) {
		super(msg);
	}

	public CaptchaAuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}

}
