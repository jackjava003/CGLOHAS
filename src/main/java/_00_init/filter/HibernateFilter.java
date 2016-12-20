package _00_init.filter;

//import java.io.ByteArrayOutputStream;
//
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import _00_init.HibernateUtil;

@WebFilter("/*")

public class HibernateFilter implements Filter {
	private FilterConfig fConfig;
	private SessionFactory factory;

	public void init(FilterConfig fConfig) throws ServletException {
		this.fConfig = fConfig;
		factory = HibernateUtil.getSessionFactory();
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String servletPath = req.getServletPath();
		String[] noHiberFilter ={"/_06_orderProcess/DeleteOrder.do","/_08_favour/addFavour.do","/_08_favour/DeleteFavourServlet.do"}; 
		
		if (!servletPath.equalsIgnoreCase(noHiberFilter[0])&&!servletPath.equalsIgnoreCase(noHiberFilter[1])&&!servletPath.equalsIgnoreCase(noHiberFilter[2])) {
			Transaction tx = null;
			try {
				Session session = factory.getCurrentSession();
				tx = session.beginTransaction();
				chain.doFilter(request, response);
				tx.commit();

			} catch (Exception e) {
				if (tx != null) {
					try {
						tx.rollback();
					} catch (Exception ex) {

					}
				}
				e.printStackTrace();
			}

		}else{
			chain.doFilter(request, response);
		}
	}

}
