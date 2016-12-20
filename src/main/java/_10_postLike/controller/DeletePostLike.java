package _10_postLike.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _01_register.model.Hibernate_register_DAO;
import _01_register.model.register_DAO;
import _01_register.model.register_bean;
import _10_postLike.model.PostLikeBean;
import _10_postLike.model.PostLikeDAO;
import _11_notification.model.NotifyDAO;

@WebServlet("/_10_postLike/DeletePostLike.do")
public class DeletePostLike extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) { // 使用逾時
			request.setAttribute("Errors", "Connection expires, Please re-login");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}
		register_bean rb = (register_bean) session.getAttribute("LoginOK");
		if (rb == null) {
			request.setAttribute("Errors", "Please re-login");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}
		int userID = rb.getUserID();
		String holderID = request.getParameter("holderID");
		if (holderID == null || holderID.trim().length() == 0) {
			RequestDispatcher rd = request.getRequestDispatcher("/_09_community/HolderList.jsp");
			rd.forward(request, response);
			return;
		}

		//
		// // 本程式負責接收 文章 的回應推文。
		int holderIDInt = Integer.parseInt(holderID);
		PostLikeDAO pdao = new PostLikeDAO();
		PostLikeBean plb = pdao.checkType(holderIDInt, userID);
		NotifyDAO ndao = new NotifyDAO();
		String type = plb.getPostType();
		int postID = plb.getPostLikeID();
		Hibernate_register_DAO hrd = new Hibernate_register_DAO();
		int count = 0;
		if (type.equalsIgnoreCase("LIKE")) {
			count += hrd.updateLike(holderIDInt, "del");
			count += pdao.deleteLike(plb);
			count += ndao.delNotify(holderIDInt,userID);
		} else {
			count += hrd.updateDisLike(holderIDInt, "del");
			count += pdao.deleteLike(plb);
			count += ndao.delNotify(holderIDInt,userID);
		}

		if (count == 3) {
			response.sendRedirect(response.encodeRedirectURL(
					request.getContextPath() + "/_09_community/HolderDetail.jsp?result=OK&holderID=" + holderIDInt));
			return;
		} else {
			request.setAttribute("Errors", "Update failed");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}
	}

}
