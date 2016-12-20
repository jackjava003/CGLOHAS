package _01_register.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import _00_init.RegularExpressionUtil;
import _00_init.VerifyUtils;
import _01_register.model.register_DAO;
import _01_register.model.register_bean;
import _02_login.model.LoginServiceDB;

@WebServlet("/_01_updateEmailController.do")
public class _01_updateEmailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String> errorMsg = new HashMap<String, String>();
		request.setAttribute("MsgMap", errorMsg);

		HttpSession session = request.getSession();
		register_bean rb = (register_bean) session.getAttribute("LoginOK");
		int userID = rb.getUserID();
		String account = rb.getAccount().trim();
		String email = request.getParameter("email");
		String checkpassword = request.getParameter("checkpassword");
		ServletContext sc = getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);
		register_DAO rs = (register_DAO) context.getBean("Register_DAO");

		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

		if (gRecaptchaResponse == null || gRecaptchaResponse.trim().length() == 0) {
			errorMsg.put("errorRecaptcha", " 【Please verify】");
		} else {
			boolean valid = VerifyUtils.verify(gRecaptchaResponse);
			if (valid == false) {
				errorMsg.put("errorRecaptcha", " 【Verify failed, Please re-verify】");
			}
		}

		if (checkpassword == null || checkpassword.trim().length() == 0) {
			errorMsg.put("errCheckPassword", " 【Password must enter】");
		} else if (RegularExpressionUtil.isPwValid(checkpassword) == false) {
			errorMsg.put("errCheckPassword", " 【Password Error】");
		}

		if (email == null || email.trim().length() == 0) {
			errorMsg.put("errEmail", " 【E-mail must enter】");
		} else if (RegularExpressionUtil.isEmailValid(email) == false) {
			errorMsg.put("errEmail", " 【Please confirm your E-mail】");
		} else if (rs.emailExists(email)) {
			errorMsg.put("errEmail", " 【This E-mail already exists, please change E-mail】");
		}

		if (!errorMsg.isEmpty()) {
			RequestDispatcher rd = request.getRequestDispatcher("_01_userUpdate/UpdateEmail.jsp");
			rd.forward(request, response);
			return;
		}

		email = email.trim();
		checkpassword = checkpassword.trim();
		try {
			LoginServiceDB lsdb =  (LoginServiceDB) context.getBean("LoginServiceDB");
			register_bean mb = lsdb.checkAccountPassword(account, checkpassword);
			if (mb != null) {
				int n = rs.updateEmail(userID, email, rb.getAccount(), rb.getPassword());
				if (n == 1) {
					response.sendRedirect(response.encodeRedirectURL("_01_userUpdate/UpdateSucess.jsp?message="
							+ URLEncoder.encode("Update success, verify mail has send to your mailbox ", "UTF-8")));
					rb.setEmail(email);
					rb.setVerified("F");
					return;
				} else {
					throw new Exception();
				}
			} else {
				errorMsg.put("errPassword", " 【Wrong Password】");
			}
		} catch (Exception e) {
			errorMsg.put("errMessage", " 【Update fail, This E-mail already exists】");
			e.printStackTrace();
		}
		if (!errorMsg.isEmpty()) {
			RequestDispatcher rd = request.getRequestDispatcher("_01_userUpdate/UpdateEmail.jsp");
			rd.forward(request, response);
			return;
		}
	}

}
