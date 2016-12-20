package _09_community.Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _01_register.model.Hibernate_register_DAO;
import _01_register.model.register_bean;
import _09_community.model.JoinBean;
import _09_community.model.JoinDAO;
import _11_notification.model.NotifyBean;
import _11_notification.model.NotifyDAO;

@WebServlet("/_09_community/DeleteJoiner.do")
public class DeleteJoinerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
		String joiner = request.getParameter("joinerID");
		int joinerInt = Integer.parseInt(joiner);
		String holder = request.getParameter("holderID");
		if (userID != joinerInt || holder == null || holder.trim().length() == 0) {
			request.setAttribute("Errors", "Please re-login");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}
		int holderInt = Integer.parseInt(holder);
		JoinDAO jd = new JoinDAO();
		JoinBean jb = new JoinBean();
		jb.setHolderID(holderInt);
		jb.setJoinerID(joinerInt);
		int count = jd.delete(jb);
		NotifyDAO ndao = new NotifyDAO();
		count += ndao.delNotify(holderInt, userID);
		
		if(count == 2){
			response.sendRedirect(response.encodeRedirectURL(
					request.getContextPath() + "/_09_community/HolderDetail.jsp?result=OK&from=del&holderID="+ holder));
			return;
		}else{
			request.setAttribute("Errors", "Errors");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}
	}

}
