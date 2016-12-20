package _09_community.Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _01_register.model.register_bean;
import _09_community.model.HolderImageBean;
import _09_community.model.HolderManagementDAO;

@WebServlet("/_09_community/DeleteImage.do")
public class DeleteImage extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
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
		String holder = request.getParameter("holderID");
		
		if ( holder == null || holder.trim().length() == 0 || !String.valueOf(userID).equals(holder)) {
			request.setAttribute("Errors", "Errors");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}
		int holderInt = Integer.parseInt(holder);
		String imgID = request.getParameter("imageID");
		String from = request.getParameter("from");
		if ( imgID == null || imgID.trim().length() == 0||from == null ||from.trim().length() == 0) {
			request.setAttribute("Errors", "Errors");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}
		from = from.trim();
		int imgIDint = Integer.parseInt(imgID);
		HolderImageBean hib = new HolderImageBean();
		hib.setType(from);
		hib.setUserID(userID);
		hib.setImage_id(imgIDint);
		HolderManagementDAO hmd = new HolderManagementDAO();
		int count = hmd.deleteImg(hib);
		
		if (count == 1) {
			response.sendRedirect(response.encodeRedirectURL(
					request.getContextPath() + "/_09_community/ShowEnvPic.jsp?result=OK&holderID=" + userID+"&from="+from));
			return;
		} else {
			request.setAttribute("Errors", "Failed to delete");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}
		
	}

}
