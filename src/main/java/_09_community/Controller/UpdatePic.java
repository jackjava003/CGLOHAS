package _09_community.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;

import _00_init.CountStrUtil;
import _00_init.GlobalService;
import _00_init.RegularExpressionUtil;
import _00_init.VerifyUtils;
import _01_register.model.register_bean;
import _09_community.model.HolderImageBean;
import _09_community.model.HolderManagementDAO;

@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024
		* 1024 * 5 * 5)

@WebServlet("/_09_community/UpdatePic.do")
public class UpdatePic extends HttpServlet {
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
		String from = request.getParameter("from");
		String picID = request.getParameter("picID");
		if (from == null || from.trim().length() == 0 || picID == null || picID.trim().length() == 0) {
			response.sendRedirect(
					response.encodeRedirectURL(request.getContextPath() + "/_09_community/HolderList.jsp"));
			return;
		}
		int userID = rb.getUserID();
		Map<String, String> errorMsg = new HashMap<String, String>();
		request.setAttribute("MsgMap", errorMsg); // 顯示錯誤訊息

		Part pic = null;
		InputStream isPic = null;
		long picSize = 0;
		String title = null;
		if (from.equalsIgnoreCase("ENV")) {
			Part envPicture = request.getPart("environment");
			InputStream isEnv = envPicture.getInputStream();
			long sizeEnv = isEnv.available();
			String environmentTitle = request.getParameter("environmentTitle");
			pic = envPicture;
			isPic = isEnv;
			picSize = sizeEnv;
			title = environmentTitle;
		} else if (from.equalsIgnoreCase("FOOD")) {
			Part foodPicture = request.getPart("food");
			InputStream isFood = foodPicture.getInputStream();
			long sizeFood = isFood.available();
			String foodTitle = request.getParameter("foodTitle");
			pic = foodPicture;
			isPic = isFood;
			picSize = sizeFood;
			title = foodTitle;
		}
		String fileName;
		if (picSize != 0) {
			fileName = GlobalService.getFileName(pic); // 此為圖片檔的檔名
			if (VerifyUtils.verifyPicFile(fileName)== false) {
				errorMsg.put("errorPicSize", " 【Please confirm file type】");
			}
		}
		if (title == null || title.trim().length() == 0) {
			errorMsg.put("errorPicTitle", " 【Please enter Title】");
		} else if (CountStrUtil.countSum(title) > 10) {
			errorMsg.put("errorPicTitle", " 【Can not exceed 10 characters】");
		}

		if (!errorMsg.isEmpty()) {
			if (from.equalsIgnoreCase("ENV") || from.equalsIgnoreCase("FOOD")) {
				RequestDispatcher rd = request.getRequestDispatcher(
						"/_09_community/ChangeImg.jsp?holderID=" + userID + "&imgID=" + picID + "&from=" + from);
				rd.forward(request, response);
				return;
			} else {
				request.setAttribute("Errors", "Error");
				RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
				rd.forward(request, response);
				return;
			}
		}
		Blob picBlob = null;
		if (picSize != 0 && isPic != null) {
			try {
				byte[] picByte = new byte[(int) picSize];
				isPic.read(picByte);
				picBlob = new SerialBlob(picByte);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		title = RegularExpressionUtil.removeTag(title).trim().replaceAll("\r\n", "<br>").replaceAll("\\s", "&nbsp;");
		int picIdInt = Integer.parseInt(picID);
		HolderManagementDAO hmd = new HolderManagementDAO();
		int count = -1;
		if (picBlob != null) {
			count = hmd.updateImg(picIdInt,picBlob,title);
		} else {
			count = hmd.updateImg(picIdInt, title);
		}

		if (count == 1) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath()
					+ "/_09_community/ShowEnvPic.jsp?result=OK&holderID=" + userID + "&from=" + from));
			return;
		} else {
			request.setAttribute("Errors", "Update failed");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}

	}

}
