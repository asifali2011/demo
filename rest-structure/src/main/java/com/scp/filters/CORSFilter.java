package com.scp.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.scp.util.CommonUtil;
import com.scp.util.Logger4j;
import com.scp.util.MacAddress;

@Component
public class CORSFilter implements Filter {

	public CORSFilter() {
		Logger4j.getLogger().info("CORSFilter init");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		Logger4j.getLogger()
				.info("IP:::" + request.getRemoteAddr() + "##MAC:::" + MacAddress.getMac(request.getRemoteAddr())
						+ "##DATE:::" + CommonUtil.getCurrentTimestamp().toString() + "##SERVICE:::"
						+ request.getRequestURI() + "##METHOD:::" + request.getMethod());

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, x-requested-with");
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}

}
