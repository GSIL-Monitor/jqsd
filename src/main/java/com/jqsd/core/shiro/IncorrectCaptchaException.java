package com.jqsd.core.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 验证码异常类
 */

public class IncorrectCaptchaException extends AuthenticationException {

	/**
	 * serial
	 */
	private static final long serialVersionUID = 1L;

	public IncorrectCaptchaException() {
		super();
	}

	public IncorrectCaptchaException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectCaptchaException(String message) {
		super(message);
	}

	public IncorrectCaptchaException(Throwable cause) {
		super(cause);
	}

}
