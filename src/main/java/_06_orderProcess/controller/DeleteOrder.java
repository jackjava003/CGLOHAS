package _06_orderProcess.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.Transaction;

import _00_init.HibernateUtil;
import _01_register.model.register_bean;
import _06_orderProcess.model.ManageOrderDAO;

@WebServlet("/_06_orderProcess/DeleteOrder.do")
public class DeleteOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		session = request.getSession(false);
		if (session == null) { // 使用逾時
			response.sendRedirect(getServletContext().getContextPath() + "/index.jsp");
			return;
		}
		register_bean rb = (register_bean) session.getAttribute("LoginOK");
		if (rb == null) {
			response.sendRedirect(getServletContext().getContextPath() + "/index.jsp");
			return;
		}

		int userID = rb.getUserID();
		int orderID = Integer.valueOf(request.getParameter("orderID"));
		boolean result = false;
		Transaction tx = null;
		try {
			ManageOrderDAO hd = new ManageOrderDAO();
			Session sessionFactory = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = sessionFactory.beginTransaction();
			result = hd.deleteOrder(orderID, userID);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
		}

		if (result == true) {

			response.sendRedirect(response.encodeRedirectURL(request.getContextPath()
					+ "/_06_orderProcess/OrderList.jsp?result=" + URLEncoder.encode("Deleted Successfully", "UTF-8")));
			return;
		} else {
			request.setAttribute("result", "Failed to delete");
			RequestDispatcher rd = request.getRequestDispatcher("/_06_orderProcess/OrderList.jsp");
			rd.forward(request, response);
			return;
		}

	}
}
