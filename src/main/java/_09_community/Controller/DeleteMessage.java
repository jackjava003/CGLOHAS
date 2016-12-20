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
import _09_community.model.MessageBean;
import _09_community.model.MessageDAO;
import _11_notification.model.NotifyDAO;

@WebServlet("/_09_community/DeleteMessage.do")
public class DeleteMessage extends HttpServlet {

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
		String messageID = request.getParameter("messageID");
		String from = request.getParameter("from");
		if (messageID == null || messageID.trim().length() == 0 || from == null || from.trim().length() == 0) {
			request.setAttribute("Errors", "Errors");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}
		String senderID = request.getParameter("userID");
		String replierID = request.getParameter("replierID");
		if ((senderID == null || senderID.trim().length() == 0)
				&& (replierID == null || replierID.trim().length() == 0)) {
			request.setAttribute("Errors", "Errors");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}
		String holderID = request.getParameter("holderID");
		from = from.trim();
		int messageIDInt = Integer.parseInt(messageID);
		MessageDAO mdao = new MessageDAO();
		int count = -1;
		if (from.equalsIgnoreCase("user")) {
			int userIDInt = Integer.parseInt(senderID);
			if (userIDInt != userID) {
				request.setAttribute("Errors", "Errors");
				RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
				rd.forward(request, response);
				return;
			}
			MessageBean mb = new MessageBean();
			mb.setMessageID(messageIDInt);
			count = mdao.deletMessage(mb);
			NotifyDAO ndao = new NotifyDAO();
			count += ndao.delNotify(Integer.parseInt(holderID), userIDInt);
		} else {
			int replierIDInt = Integer.parseInt(replierID);
			if (replierIDInt != userID) {
				request.setAttribute("Errors", "Errors");
				RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
				rd.forward(request, response);
				return;
			}
			count = mdao.deletReply(messageIDInt);
			MessageBean mb = mdao.getMessage(messageIDInt);
			NotifyDAO ndao = new NotifyDAO();
			count += ndao.delNotify(mb.getUserID(), Integer.parseInt(holderID));
		}

		if (count == 2) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath()
					+ "/_09_community/HolderDetail.jsp?result=OK&holderID=" + holderID + "&Message=1"));;
			return;
		} else {
			request.setAttribute("Errors", "Failed to delete");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}

	}

}
