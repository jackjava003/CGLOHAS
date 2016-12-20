package _09_community.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _00_init.RegularExpressionUtil;
import _00_init.VerifyUtils;
import _01_register.model.Hibernate_register_DAO;
import _01_register.model.register_bean;
import _09_community.model.MessageBean;
import _09_community.model.MessageDAO;
import _11_notification.model.NotifyBean;
import _11_notification.model.NotifyDAO;

@WebServlet("/_09_community/MessageServlet.do")
public class MessageServlet extends HttpServlet {
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
		Map<String, String> errorMsg = new HashMap<String, String>();
		request.setAttribute("MsgMap", errorMsg); // 顯示錯誤訊息
		String messageFrom = request.getParameter("messageFrom");
		String holderID = request.getParameter("holderID");
		if (messageFrom == null || messageFrom.trim().length() == 0 || holderID == null
				|| holderID.trim().length() == 0) {
			request.setAttribute("Errors", "Error");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}
		int holderInt = Integer.parseInt(holderID);
		String message ="";
		String hidden ="T";
		String replyMessage ="";
		String messageID="";
		String senderID="";
		int userID = rb.getUserID();
		if (messageFrom.equalsIgnoreCase("normalUser")) {
			message = request.getParameter("message");
			String visibility = request.getParameter("visibility");
			if(visibility ==null || visibility.trim().length()==0){
				hidden ="F";
			}
			String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
			if (gRecaptchaResponse == null || gRecaptchaResponse.trim().length() == 0) {
				errorMsg.put("errorRecaptcha2", " 【Please verify】");
			} else {
				boolean valid = VerifyUtils.verify(gRecaptchaResponse);
				if (valid == false) {
					errorMsg.put("errorRecaptcha2", " 【Verify failed, Please re-verify】");
				}
			}
			if (message == null || message.trim().length() == 0) {
				errorMsg.put("errorMessage", " 【Please enter message】");
			}else if(message.length()>100){
				errorMsg.put("errorMessage", " 【Can not exceed 100 characters】");
			}
		}else{
			holderInt = userID;
			replyMessage = request.getParameter("replyMessage");
			messageID = request.getParameter("messageID");
			senderID = request.getParameter("userID");
			if (replyMessage == null || replyMessage.trim().length() == 0) {
				errorMsg.put("errorreplyMessage", " 【Please enter reply message】");
			}else if(replyMessage.length()>100){
				errorMsg.put("errorreplyMessage", " 【Can not exceed 100 characters】");
			}
		}
		
		if (!errorMsg.isEmpty()) {
			request.setAttribute("Message", 1);
			RequestDispatcher rd = request.getRequestDispatcher("/_09_community/HolderDetail.jsp?holderID=" + holderID);
			rd.forward(request, response);
			return;
		}
		int count = 0;
		
		MessageDAO mdao = new MessageDAO();
		if(messageFrom.equalsIgnoreCase("normalUser")){
			message = RegularExpressionUtil.removeTag(message).trim().replaceAll("\r\n", "<br>").replaceAll("\\s", "&nbsp;");
			MessageBean mb = new MessageBean(userID,holderInt,message,hidden);
			count = mdao.insertMessage(mb);
			Hibernate_register_DAO hrdao = new Hibernate_register_DAO();
			NotifyDAO ndao = new NotifyDAO();
			String userName = hrdao.getUserName(userID);
			NotifyBean nb = new NotifyBean(userID,holderInt,userName,3,0);
			count += ndao.insertNotify(nb);
		}else{
			int messageIDInt = Integer.parseInt(messageID);
			replyMessage = RegularExpressionUtil.removeTag(replyMessage).trim().replaceAll("\r\n", "<br>").replaceAll("\\s", "&nbsp;");
			count = mdao.updateReplyMessage(messageIDInt, replyMessage);
			
			Hibernate_register_DAO hrdao = new Hibernate_register_DAO();
			NotifyDAO ndao = new NotifyDAO();
			String userName = hrdao.getUserName(holderInt);
			NotifyBean nb = new NotifyBean(holderInt,Integer.parseInt(senderID),userName,4,0);
			count += ndao.insertNotify(nb);
		}
		 
		if (count == 2) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath()
					+ "/_09_community/HolderDetail.jsp?result=OK&holderID=" + holderInt + "&Message=1"));
			return;
		} else {
			request.setAttribute("Errors", "Update failed");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}

	}

}
