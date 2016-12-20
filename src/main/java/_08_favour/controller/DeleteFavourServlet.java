package _08_favour.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import _00_init.HibernateUtil;
import _01_register.model.register_bean;
import _08_favour.model.FavourBean;
import _08_favour.model.ManageMyFavourDAO;

@WebServlet("/_08_favour/DeleteFavourServlet.do")
public class DeleteFavourServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
		return;
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
		int recipeID = Integer.valueOf(request.getParameter("recipeID"));
		FavourBean fb = new FavourBean();
		fb.setUserID(userID);
		fb.setRecipesID(recipeID);
		int result = -1;
		Transaction tx = null;
		try {
			Session sessionFactory = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = sessionFactory.beginTransaction();
			ManageMyFavourDAO mf = new ManageMyFavourDAO();
			result = mf.delete(fb);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
		}

		if (result == 1) {
			response.sendRedirect(response.encodeRedirectURL(
					request.getContextPath() + "/_08_favour/DisplayPageFavour.do?from=memberCenter&result="
							+ URLEncoder.encode("Deleted Successfully", "UTF-8")));
			return;
		} else {
			request.setAttribute("result", "Failed to delete");
			RequestDispatcher rd = request.getRequestDispatcher("/_08_favour/ShowMyFavour.jsp");
			rd.forward(request, response);
			return;
		}

	}

}
