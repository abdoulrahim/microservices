package com.xcloud.gateway.filter;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class PreZuulFilter extends ZuulFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final Boolean SHOULD_FILTER = true;

	private static final Integer FILTER_ORDER = -2;

	private static final String FILTER_TYPE = "pre";

	@Override
	public boolean shouldFilter() {
		return SHOULD_FILTER;
	}

	@Override
	public Object run() {
		final RequestContext ctx = RequestContext.getCurrentContext();
		logger.info("in zuul filter " + ctx.getRequest().getRequestURI());

		if (ctx.getRequest().getRequestURI().contains("oauth/token")) {
			byte[] encoded;
			try {
				encoded = Base64.encode("browser:".getBytes("UTF-8"));
				ctx.addZuulRequestHeader("Authorization", "Basic " + new String(encoded));
			} catch (final UnsupportedEncodingException e) {
				logger.error("Error occured in pre filter", e);
			}
		}

		logger.info("pre filter");
		logger.info(ctx.getRequest().getHeader("Authorization"));

		final HttpServletRequest req = ctx.getRequest();

		final String refreshToken = extractRefreshToken(req);
		if (refreshToken != null) {
			final Map<String, String[]> param = new HashMap<String, String[]>();
			param.put("refresh_token", new String[] { refreshToken });
			param.put("grant_type", new String[] { "refresh_token" });

			ctx.setRequest(new CustomHttpServletRequest(req, param));
		}

		return null;
	}

	private String extractRefreshToken(HttpServletRequest req) {
		final Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equalsIgnoreCase("refreshToken")) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

	@Override
	public int filterOrder() {
		return FILTER_ORDER;
	}

	@Override
	public String filterType() {
		return FILTER_TYPE;
	}
}
