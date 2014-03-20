package com.modelsystem.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.modelsystem.filter.authority.AbstractAuthority;
import com.modelsystem.filter.authority.Authority;

public class UrlFilter implements Filter {

	private Logger log = Logger.getLogger(this.getClass());
	private String[] exceptUrl = null;
	private String errorPage = null;
	private String authorityStr = null;
	private String[] servletSuffix = null;
	private String[] actionSuffix = null;
	private AbstractAuthority authority = null;

	public void destroy() {
		log.debug("urlFilter is ending...");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		String uri = req.getServletPath();
		log.debug("uri = " + uri);
		/*
		 *  1.判断是否为例外URL（即不需要拦截的URL）
		 *  2.判断是否登录
		 *  	未登录则给出提示信息
		 *  3.对URL进行权限判断
		 *  	如果是servletURL则需要拼接方法参数，参数名fn
		 */
		if (isContain(uri)) {
			if (isAction(uri)) {
				log.debug("doFilter");
				chain.doFilter(request, response);
			} else {
				log.debug("dispatcher");
				req.getRequestDispatcher(uri).forward(request, response);
				return;
			}
		} else if(req.getSession(false) == null || req.getSession().getAttribute("loginUser") == null) {
//			log.debug("the user has not been login!");
//			prompt(response, "you has not login in !<br/><a href='" + req.getContextPath() + exceptUrl[0] + "'>返回登录界面</a>");
//			return;
			//直接跳转到目标页面
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendRedirect(req.getContextPath() + exceptUrl[0]);
		} else {
			if(isServlet(uri)) {
				uri = uri + "?fn=" + req.getParameter("fn");
			}
			if(authority.validate(req, uri)) {
				log.debug("doFilter");
				chain.doFilter(request, response);
			} else {
				log.debug("no such url be found!");
				req.getRequestDispatcher(errorPage).forward(request, response);
//				prompt(response, "no such url be found!");
			}
		}
	}

	public void init(FilterConfig config) throws ServletException {
		setAttribute(config);
		log.debug("urlFilter is starting...");
	}

	/**
	 * 设置默认参考值
	 * @param config
	 */
	private void setAttribute(FilterConfig config) {
		// 加载例外URL路径，默认为/index.jsp,/user.html
		String str = config.getInitParameter("exceptUrl");
		if(str == null || "".equals(str)) {
			exceptUrl = new String[2];
			exceptUrl[0] = "/index.jsp";
			exceptUrl[1] = "/user.html";
		} else {
			exceptUrl = str.split(",");
		}
		log.debug("exceptUrl = " + exceptUrl);
		//加载错误页面，默认为404.jsp
		str = config.getInitParameter("errorPage");
		if(str == null || "".equals(str)) {
			errorPage = "404.jsp";
		} else {
			errorPage = str;
		}
		log.debug("errorPage = " + errorPage);
		// 加载servlet后缀，用于拼接URL，没有默认值
		str = config.getInitParameter("servletSuffix;actionSuffix");
		String[] suffix = str.split(";");
		servletSuffix = splitString(suffix[0]);
		actionSuffix = splitString(suffix[1]);
		log.debug("servletSuffix = " + servletSuffix);
		log.debug("actionSuffix = " + actionSuffix);
		//加载权限验证器，默认为Authority
		authorityStr = config.getInitParameter("authority");
		if(authorityStr == null || "".equals(authorityStr)) {
			authority = new Authority();
		} else {
			try {
				authority = (AbstractAuthority) Class.forName(authorityStr).newInstance();
				log.debug("authority = " + authority);
			} catch (Exception e) {
				log.debug("无法实例化" + authorityStr + "对象");
				log.debug(e.getMessage());
			}
		}
	}
	
	/**
	 * 将字符串以逗号分割
	 */
	private String[] splitString(String str) {
		if (str != null) {
			return str.split(",");
		}
		return null;
	}

	/**
	 * URL是否在exceptUtl中存在
	 * 		是则返回true
	 * 		否则返回false
	 * @param uri
	 * @return
	 */
	private Boolean isContain(String uri) {
		for(int i=0; i<exceptUrl.length; i++) {
			if(exceptUrl[i].indexOf(uri) != -1) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 使用servlet||action约定后缀名对uri进行判断是否为servlet或者action
	 * 
	 * @param uri
	 * @return
	 */
	private Boolean isServlet(String uri) {
		return isSuffix(servletSuffix, uri);
	}

	private Boolean isAction(String uri) {
		return isSuffix(actionSuffix, uri);
	}

	private Boolean isSuffix(String[] suffix, String uri) {
		if (suffix == null) {
			return false;
		}
		for (int i = 0; i < suffix.length; i++) {
			if (uri.indexOf(suffix[i]) != -1) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 向页面展示提示信息
	 * @param response
	 * @param string
	 */
	@SuppressWarnings("unused")
	private void prompt(ServletResponse response, String string) {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(string);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
