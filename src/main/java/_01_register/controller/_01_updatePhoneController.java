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

/**
 * Servlet implementation class _01_updatePhoneController
 */
@WebServlet("/_01_updatePhoneController.do")
public class _01_updatePhoneController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> errorMsg = new HashMap<String, String>();
		request.setAttribute("MsgMap", errorMsg);
		ServletContext sc = getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);

		HttpSession session = request.getSession();
		register_bean rb = (register_bean) session.getAttribute("LoginOK");
		int userID = rb.getUserID();
		String account = rb.getAccount().trim();
		String phone = request.getParameter("phone");
		String checkpassword = request.getParameter("checkpassword");
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
			errorMsg.put("errCheckPassword", "【Password must enter】");
		} else if (RegularExpressionUtil.isPwValid(checkpassword) == false) {
			errorMsg.put("errCheckPassword", " 【Password Error】");
		}
		register_DAO rs = (register_DAO) context.getBean("Register_DAO");
	
		if (phone == null || phone.trim().length() == 0) {
			errorMsg.put("errphone", "【Mobile must enter】");
		} else if (RegularExpressionUtil.isPhoneValid(phone) == false) {
			errorMsg.put("errphone", " 【Please confirm your Mobile number】");
		} else if (rs.phoneExists(phone)) {
			errorMsg.put("errphone", " 【This Mobile already exists, please change Mobile】");
		}
		if (!errorMsg.isEmpty()) {
			RequestDispatcher rd = request.getRequestDispatcher("_01_userUpdate/UpdatePhone.jsp");
			rd.forward(request, response);
			return;
		}

		phone = phone.trim();
		checkpassword = checkpassword.trim();
		try {
			LoginServiceDB lsdb = new LoginServiceDB();
			register_bean mb = lsdb.checkAccountPassword(account, checkpassword);
			if (mb != null) {
				int n = rs.update(userID, "cellphone", phone);
				if (n == 1) {
					mb.setCellphone(phone);
					response.sendRedirect(response.encodeRedirectURL(
							"_01_userUpdate/UpdateSucess.jsp?message=" + URLEncoder.encode("Update success", "UTF-8")));
					return;
				} else {
					throw new Exception();
				}
			} else {
				errorMsg.put("errPassword", "【Password Error】");
			}
		} catch (Exception e) {
			errorMsg.put("errMessage", "【Error】");
		}
		if (!errorMsg.isEmpty()) {
			RequestDispatcher rd = request.getRequestDispatcher("_01_userUpdate/UpdatePhone.jsp");
			rd.forward(request, response);
			return;
		}
	}

}
