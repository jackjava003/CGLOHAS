package _01_register.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import _00_init.GlobalService;
import _00_init.VerifyUtils;
import _01_register.model.register_DAO;
import _01_register.model.register_bean;

@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 500, maxRequestSize = 1024
		* 1024 * 500 * 5)
@WebServlet("/_01_updatePhotoController.do")
public class _01_updatePhotoController extends HttpServlet {
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
		Part part = request.getPart("file1");
		InputStream is = part.getInputStream();
		String fileName = "";
		long sizeInBytes = part.getSize();
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

		if (gRecaptchaResponse == null || gRecaptchaResponse.trim().length() == 0) {
			errorMsg.put("errorRecaptcha", " 【Please verify】");
		} else {
			boolean valid = VerifyUtils.verify(gRecaptchaResponse);
			if (valid == false) {
				errorMsg.put("errorRecaptcha", " 【Verify failed, Please re-verify】");
			}
		}

		if (sizeInBytes != 0 && (sizeInBytes < GlobalService.UPDATE_IMG_SIZE_LIMIT)) {
			fileName = GlobalService.getFileName(part); // 此為圖片檔的檔名
			if (VerifyUtils.verifyPicFile(fileName) == false) {
				errorMsg.put("errPicture", " 【Please confirm the file type】");
			}
		} else if (sizeInBytes > GlobalService.UPDATE_IMG_SIZE_LIMIT) {
			errorMsg.put("errPicture", " 【Can not exceed 5 MB】");
		} else {
			errorMsg.put("errPicture", " 【Please select photo】");
		}

		if (!errorMsg.isEmpty()) {
			RequestDispatcher rd = request.getRequestDispatcher("_01_userUpdate/UpdatePhoto.jsp");
			rd.forward(request, response);
			return;
		}

		int result = -1;
		register_DAO rdao = (register_DAO) context.getBean("Register_DAO");
		result = rdao.updateImg(userID, is, sizeInBytes);

		if (result == 1) {
			response.sendRedirect("_01_userUpdate/UpdateSucess.jsp?message=" + URLEncoder.encode("Update success", "UTF-8"));
			return;
		} else {
			errorMsg.put("errMessage", "【Error】");
			RequestDispatcher rd = request.getRequestDispatcher("_01_userUpdate/UpdatePhoto.jsp");
			rd.forward(request, response);
			return;
		}

	}

}
