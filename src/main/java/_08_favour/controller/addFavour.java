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

@WebServlet("/_08_favour/addFavour.do")
public class addFavour extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		register_bean mb = (register_bean) session.getAttribute("LoginOK");
		String pageNo = request.getParameter("pageNo");
		String type = request.getParameter("type");
		String recipeID = request.getParameter("recipeID");
		if (mb == null) {
			System.out.println(pageNo);
			System.out.println(type);
			response.sendRedirect(
					getServletContext().getContextPath() + "/_02_login/login.jsp?fromRecipesDetail=" + recipeID +"&pageNo="+pageNo+"&type="+URLEncoder.encode(type, "UTF-8"));
			return;
		}
		FavourBean fb = new FavourBean();
		fb.setUserID(mb.getUserID());
		fb.setRecipesID(Integer.valueOf(recipeID.trim()));
		int result = -1;
		Transaction tx = null;
		try {
			ManageMyFavourDAO fbd = new ManageMyFavourDAO();
			Session sessionFactory = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = sessionFactory.beginTransaction();
			result = fbd.insert(fb);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
		}

		if (result == 1) {
			response.sendRedirect(response
					.encodeRedirectURL(request.getContextPath() + "/_03_recipes/RecipesDetail.jsp?id=" + recipeID+"&pageNo="+pageNo+"&type="+URLEncoder.encode(type, "UTF-8")));
			return;
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("RecipesDetail.jsp?id=" + recipeID + "&error=True&pageNo="+pageNo+"&type="+URLEncoder.encode(type, "UTF-8"));
			rd.forward(request, response);
			return;
		}

	}

}
