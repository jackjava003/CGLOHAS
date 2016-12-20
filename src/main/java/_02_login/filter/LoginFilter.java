package _02_login.filter;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.*;

import _01_register.model.*;
import _02_login.model.LoginIPBean;
import _02_login.model.LoginIPDAO;

@WebFilter(urlPatterns = { "/*" }, initParams = { @WebInitParam(name = "mustLogin1", value = "/_01_userUpdate/*"),
		@WebInitParam(name = "mustLogin2", value = "/_05_ShoppingCart/checkout.do"),
		@WebInitParam(name = "mustLogin3", value = "/_06_orderProcess/*"),
		@WebInitParam(name = "mustLogin4", value = "/_09_community/Holder.jsp") })
public class LoginFilter implements Filter {
	Collection<String> url = new ArrayList<String>();
	String servletPath;
	String contextPath;
	String requestURI;

	public void init(FilterConfig fConfig) throws ServletException {
		Enumeration<String> e = fConfig.getInitParameterNames();
		while (e.hasMoreElements()) {
			String path = e.nextElement();
			url.add(fConfig.getInitParameter(path));
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		boolean isRequestedSessionIdValid = false;
		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			servletPath = req.getServletPath();
			contextPath = req.getContextPath();
			requestURI = req.getRequestURI();
			isRequestedSessionIdValid = req.isRequestedSessionIdValid();

			if (mustLogin()) {
				if (checkLogin(req)) { // 需要登入，已經登入
					chain.doFilter(request, response);
				} else { // 需要登入，尚未登入
					HttpSession session = req.getSession();
					session.setAttribute("requestURI", requestURI);
					if (!isRequestedSessionIdValid) {
						session.setAttribute("timeOut", "Connection expires, Please re-login");
					}
					LoginIPDAO lidao = new LoginIPDAO();
					LoginIPBean lib = lidao.getIPBean(req.getRemoteAddr());
					if (lib != null) {
						long lastTryTime = lib.getLastTryTime().getTime();
						long now = System.currentTimeMillis();
						int diff = (int) (now - lastTryTime);
						if (diff > (1000 * 60 * 60 * 3)) {
							lidao.delIP(lib);
						} else if (lib.getWrongTimes() == 3) {
							int nextLoginTime = (int) (lastTryTime + (3 * 60 * 60 * 1000) - now) / 1000;
							int hour = nextLoginTime / (60 * 60);
							int min = (nextLoginTime - (hour * 60 * 60)) / 60;
							resp.sendRedirect(resp.encodeRedirectURL("/CGLOHAS/_02_login/BlockError.jsp?ip="
									+ req.getRemoteAddr() + "&timeHour=" + hour + "&timeMin=" + min));
							return;
						}
					}
					resp.sendRedirect(contextPath + "/_02_login/login.jsp");
					return;
				}
			} else { // 不需要登入
				String[] noFilter = { "/_02_login/forgetAccount.jsp", "/_02_login/forgetPassword.jsp",
						"/_02_login/login.jsp", "/_00_register/register.jsp" };
				if (servletPath.equalsIgnoreCase(noFilter[0]) || servletPath.equalsIgnoreCase(noFilter[1])
						|| servletPath.equalsIgnoreCase(noFilter[2]) || servletPath.equalsIgnoreCase(noFilter[3])) {
					LoginIPDAO lidao = new LoginIPDAO();
					LoginIPBean lib = lidao.getIPBean(req.getRemoteAddr());
					if (lib != null) {
						long lastTryTime = lib.getLastTryTime().getTime();
						long now = System.currentTimeMillis();
						int diff = (int) (now - lastTryTime);
						if (diff > (1000 * 60 * 60 * 3)) {
							lidao.delIP(lib);
						} else if (lib.getWrongTimes() == 3) {
							int nextLoginTime = (int) (lastTryTime + (3 * 60 * 60 * 1000) - now) / 1000;
							int hour = nextLoginTime / (60 * 60);
							int min = (nextLoginTime - (hour * 60 * 60)) / 60;
							resp.sendRedirect(resp.encodeRedirectURL("/CGLOHAS/_02_login/BlockError.jsp?ip="
									+ req.getRemoteAddr() + "&timeHour=" + hour + "&timeMin=" + min));
							return;
						}
					}
				}
				chain.doFilter(request, response);
			}
		} else {
			throw new ServletException("Request / Response 型態錯誤");
		}
	}

	private boolean checkLogin(HttpServletRequest req) {
		HttpSession session = req.getSession();
		register_bean loginToken = (register_bean) session.getAttribute("LoginOK");
		if (loginToken == null) {
			return false;
		} else {
			return true;
		}
	}

	private boolean mustLogin() {
		boolean login = false;
		for (String sURL : url) {
			if (sURL.endsWith("*")) {
				sURL = sURL.substring(0, sURL.length() - 1);
				if (servletPath.startsWith(sURL)) {
					login = true;
					break;
				}
			} else {
				if (servletPath.equals(sURL)) {
					login = true;
					break;
				}
			}
		}
		return login;
	}

	@Override
	public void destroy() {
	}
}