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

import _00_init.GlobalService;
import _00_init.RegularExpressionUtil;
import _00_init.VerifyUtils;
import _01_register.model.register_DAO;
import _01_register.model.register_bean;
import _02_login.model.LoginServiceDB;

@WebServlet("/_01_updatePasswordController.do")
public class _01_updatePasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext sc = getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);
		Map<String, String> errorMsg = new HashMap<String, String>();
		request.setAttribute("MsgMap", errorMsg);

		HttpSession session = request.getSession();
		register_bean rb = (register_bean) session.getAttribute("LoginOK");
		int userID = rb.getUserID();
		String account = rb.getAccount().trim();
		String password = request.getParameter("password");
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
			errorMsg.put("errCheckPassword", "【Old Password must enter】");
		} else if (RegularExpressionUtil.isPwValid(checkpassword) == false) {
			errorMsg.put("errCheckPassword", " 【Password Error】");
		}
		if (password == null || password.trim().length() == 0) {
			errorMsg.put("errpassword", "【New Password must enter】");
		} else if (RegularExpressionUtil.isPwValid(password) == false) {
			errorMsg.put("errpassword", " 【Password can not contain symbols and must between 8 and 16 characters in English and numerals】");
		}

		if (!errorMsg.isEmpty()) {
			RequestDispatcher rd = request.getRequestDispatcher("_01_userUpdate/UpdatePassword.jsp");
			rd.forward(request, response);
			return;
		}

		password = password.trim();
		checkpassword = checkpassword.trim();
		register_DAO rdao = (register_DAO) context.getBean("Register_DAO");
		try {
			LoginServiceDB lsdb = (LoginServiceDB) context.getBean("LoginServiceDB");
			register_bean mb = lsdb.checkAccountPassword(account, checkpassword);
			if (mb != null) {
				String GSpassword = GlobalService.encryptString(password);
				String GSpassword1 = GlobalService.getMD5Endocing(GSpassword);
				int n = rdao.update(userID, "password", GSpassword1);
				if (n == 1) {
					response.sendRedirect(response.encodeRedirectURL("_01_userUpdate/UpdateSucess.jsp?message="
							+ URLEncoder.encode("Update success", "UTF-8") + "&logout=true"));
					new LoginServiceDB(true);
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
			RequestDispatcher rd = request.getRequestDispatcher("_01_userUpdate/UpdatePassword.jsp");
			rd.forward(request, response);
			return;
		}
	}

}
