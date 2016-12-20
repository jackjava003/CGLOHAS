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

import _00_init.VerifyUtils;
import _01_register.model.register_DAO;
import _01_register.model.register_bean;

@WebServlet("/_01_updateNameController.do")
public class _01_updateNameController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		doPost(request, response);
		return;
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext sc = getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);
		HttpSession session = request.getSession();
		register_bean rb = (register_bean) session.getAttribute("LoginOK");
		int userID = rb.getUserID();
		String name = request.getParameter("name");
		Map<String, String> errorMsg = new HashMap<String, String>();
		request.setAttribute("MsgMap", errorMsg);
		
//	     System.out.println("1");
//		
//		PrintWriter os = response.getWriter();
//		response.setContentType("text/xml");
//		os.println("<?xml version='1.0' encoding='utf-8' standalone='yes' ?>");
//		os.println("<response>");
//		boolean test = false;
//		 System.out.println("2");
//			if(name == null || name.trim().length()==0){
//				os.println("請輸入名稱");	
//				test = true;
//			}
//		
//			os.println("</response>");
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		if (gRecaptchaResponse == null || gRecaptchaResponse.trim().length() == 0) {
			errorMsg.put("errorRecaptcha", " 【Please verify】");
		} else {
			boolean valid = VerifyUtils.verify(gRecaptchaResponse);
			if (valid == false) {
				errorMsg.put("errorRecaptcha", " 【Verify failed, Please re-verify】");
			}
		}
		

		if (name == null || name.trim().length()==0 || name.length() > 8) {
			errorMsg.put("errName", "【Name must enter and must less than 8 characters】");
		}
		
		if(!errorMsg.isEmpty()){
			RequestDispatcher rd = request.getRequestDispatcher("/_01_userUpdate/UpdateName.jsp");
			rd.forward(request, response);
			return;
		}

		register_DAO rdao = (register_DAO) context.getBean("Register_DAO");
		try {
			int n = rdao.update(userID, "name", name.trim());
			if (n == 1) {
				rb.setName(name.trim());
				response.sendRedirect(response.encodeRedirectURL("_01_userUpdate/UpdateSucess.jsp?message="+URLEncoder.encode("Update success", "UTF-8")));
				return;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			request.setAttribute("errName", "【Error】");
			RequestDispatcher rd = request.getRequestDispatcher("UpdateName.jsp");
			rd.forward(request, response);
			return;
		}
		
	}

}
