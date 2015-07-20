package com.cai310.security.springsecurity.jcaptcha;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.security.util.RedirectUtils;
import org.springframework.security.util.UrlUtils;
import org.springframework.util.Assert;

import com.cai310.lottery.Constant;
import com.octo.captcha.service.CaptchaService;

public class CaptchaAuthenticationProcessingFilter implements InitializingBean, Filter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "j_captcha";

	/** captcha authentication parameter. */
	private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;

	/** captcha service. */
	private CaptchaService captchaService = null;

	private String filterProcessesUrl = getDefaultFilterProcessesUrl();

	/** Where to redirect the browser to if validation fails */
	private String authenticationFailureUrl = null;

	private boolean serverSideRedirect = false;

	public void afterPropertiesSet() throws Exception {
		Assert.hasLength(filterProcessesUrl, "filterProcessesUrl must be specified");
		Assert.isTrue(UrlUtils.isValidRedirectUrl(filterProcessesUrl), filterProcessesUrl
				+ " isn't a valid redirect URL");
		Assert.hasLength(captchaParameter, "captchaParameter must be specified");
		Assert.notNull(captchaService, "captchaService cannot be null");
	}

	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		// Do we really need the checks on the types in practice ?
		if (!(request instanceof HttpServletRequest)) {
			throw new ServletException("Can only process HttpServletRequest");
		}

		if (!(response instanceof HttpServletResponse)) {
			throw new ServletException("Can only process HttpServletResponse");
		}

		doFilterHttp((HttpServletRequest) request, (HttpServletResponse) response, chain);
	}

	protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (requiresCaptchaAuthentication(request, response)) {
			logger.debug("Request is to process captcha authentication");
			try {
				attemptCaptchaAuthentication(request);
			} catch (CaptchaAuthenticationException failed) {
				unsuccessfulAuthentication(request, response, failed);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public void attemptCaptchaAuthentication(HttpServletRequest request) throws CaptchaAuthenticationException {
		HttpSession session = request.getSession();
		if (session != null) {
			String captchaResponse = request.getParameter(captchaParameter);
			if (StringUtils.isBlank(captchaResponse))
				throw new CaptchaAuthenticationException("验证不能为空.");
			 if (!captchaResponse.equals(session.getAttribute(Constant.LOGIN_CAPTCHA)))
			 throw new CaptchaAuthenticationException("验证错误.");
		} else {
			throw new CaptchaAuthenticationException("获取不到HttpSession,无法进行验证码验证.");
		}
	}

	protected boolean requiresCaptchaAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();
		int pathParamIndex = uri.indexOf(';');

		if (pathParamIndex > 0) {
			// strip everything after the first semi-colon
			uri = uri.substring(0, pathParamIndex);
		}

		if ("".equals(request.getContextPath())) {
			return uri.endsWith(filterProcessesUrl);
		}

		return uri.endsWith(request.getContextPath() + filterProcessesUrl);
	}

	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			CaptchaAuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(null);

		logger.debug("Updated SecurityContextHolder to contain null Authentication");

		try {
			HttpSession session = request.getSession(false);
			if (session != null) {
				request.getSession().setAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY, failed);
			}
		} catch (Exception ignored) {
		}

		if (authenticationFailureUrl == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed:" + failed.getMessage());
		} else if (serverSideRedirect) {
			request.getRequestDispatcher(authenticationFailureUrl).forward(request, response);
		} else {
			sendRedirect(request, response, authenticationFailureUrl);
		}
	}

	protected void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
			throws IOException {
		RedirectUtils.sendRedirect(request, response, url, false);
	}

	/**
	 * Does nothing. We use IoC container lifecycle services instead.
	 * 
	 * @param filterConfig ignored
	 * @throws ServletException ignored
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * Does nothing. We use IoC container lifecycle services instead.
	 */
	public void destroy() {
	}

	public String getCaptchaParameter() {
		return captchaParameter;
	}

	public void setCaptchaParameter(String captchaParameter) {
		this.captchaParameter = captchaParameter;
	}

	private String getDefaultFilterProcessesUrl() {
		return "/j_spring_security_check";
	}

	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

	public String getAuthenticationFailureUrl() {
		return authenticationFailureUrl;
	}

	public void setAuthenticationFailureUrl(String authenticationFailureUrl) {
		this.authenticationFailureUrl = authenticationFailureUrl;
	}

	/**
	 * Tells if we are to do a server side include of the error URL instead of a
	 * 302 redirect.
	 * 
	 * @param serverSideRedirect
	 */
	public void setServerSideRedirect(boolean serverSideRedirect) {
		this.serverSideRedirect = serverSideRedirect;
	}

	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}

}
