package _01_register.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
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
import _00_init.RegularExpressionUtil;
import _00_init.VerifyUtils;
import _01_register.model.register_DAO;
import _01_register.model.register_bean;
import _02_login.model.LoginServiceDB;

@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 500, maxRequestSize = 1024
		* 1024 * 500 * 5)

@WebServlet("/register.do")
public class _01_registerController extends HttpServlet {
	private static final long serivalVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");// 轉碼
		//Spring
		ServletContext sc = getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);
		// 新增帳戶
		// 準備存放錯誤訊息的Map物件
		Map<String, String> errorMsg = new HashMap<String, String>();
		// 準備存放註冊成功之訊息的Map物件
		Map<String, String> msgOK = new HashMap<String, String>();
		// 註冊成功後將用response.sendRedirect()導向新的畫面，所以需要
		// session物件來存放共用資料。
		HttpSession session = request.getSession();
		request.setAttribute("MsgMap", errorMsg); // 顯示錯誤訊息
		request.setAttribute("MsgOK", msgOK); // 顯示正常訊息
		// 文字輸入
		String account = "";
		String password = "";
		String sex = "";
		String name = "";
		String cellphone = "";
		String email = "";
		String birthDate = "";

		// 生日轉換
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date2 = null;
		java.sql.Date sqdate = null;

		// 存放parts值
		String fileName = "";

		// 轉圖片
		long sizeInBytes = 0;
		InputStream is = null;

		register_DAO rs = (register_DAO) context.getBean("Register_DAO");

		Collection<Part> parts = request.getParts(); // 取出HTTP multipart
														// request內所有的parts
		GlobalService.exploreParts(parts, request);
		// 由parts != null來判斷此上傳資料是否為HTTP multipart request
		if (parts != null) { // 如果這是一個上傳資料的表單
			for (Part p : parts) {
				String fldName = p.getName();
				String value = request.getParameter(fldName);
				// 1. 讀取使用者輸入資料
				if (p.getContentType() == null) {
					if (fldName.equals("account")) {
						account = value;
					} else if (fldName.equals("password")) {
						password = value;
					} else if (value.equalsIgnoreCase("boy")) {
						sex = value;
					} else if (value.equalsIgnoreCase("girl")) {
						sex = value;
					} else if (fldName.equalsIgnoreCase("name")) {
						name = value;
					} else if (fldName.equalsIgnoreCase("eMail")) {
						email = value;
					} else if (fldName.equalsIgnoreCase("cellphone")) {
						cellphone = value;
					} else if (fldName.equalsIgnoreCase("birthDate")) {
						birthDate = value;
					}
				} else {
					is = p.getInputStream();
					sizeInBytes = p.getSize();
					if (sizeInBytes != 0) {
						fileName = GlobalService.getFileName(p); // 此為圖片檔的檔名
						if (VerifyUtils.verifyPicFile(fileName) == false) {
							errorMsg.put("errPicture", " 【Please confirm the file type】");
						}
					} else {
						is = getServletContext().getResourceAsStream("/image/default.png");
						sizeInBytes = is.available();
					}
				}
			}

			String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
			// 2. 進行必要的資料轉換
			// 檢查

			try {
				sdf.setLenient(false);
				date2 = sdf.parse(birthDate);
				System.out.println(date2);
				sqdate = new java.sql.Date(date2.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
				errorMsg.put("errorFormat", "【Birthday format error, should yyyy-MM-dd】");
			}

			// 3. 檢查使用者輸入資料
			if (gRecaptchaResponse == null || gRecaptchaResponse.trim().length() == 0) {
				errorMsg.put("errorRecaptcha", " 【Please verify】");
			} else {
				boolean valid = VerifyUtils.verify(gRecaptchaResponse);
				if (valid == false) {
					errorMsg.put("errorRecaptcha", " 【Verify failed, Please re-verify】");
				}
			}
			if (account == null || account.trim().length() == 0) {
				errorMsg.put("errorIDEmpty", " 【Account must enter】");
			} else if (RegularExpressionUtil.isAcValid(account) == false) {
				errorMsg.put("errorIDEmpty", " 【Account can not contain symbols and must between 8 and 16 characters in English and numerals】");
			} else if (rs.accountExists(account)) {
				errorMsg.put("errorIDDup", " 【This account already exists, please change account】");
			}

			if (password == null || password.trim().length() == 0) {
				errorMsg.put("errorPasswordEmpty", " 【Password must enter】");
			} else if (RegularExpressionUtil.isPwValid(password) == false) {
				errorMsg.put("errorPasswordEmpty", " 【Password can not contain symbols and must between 8 and 16 characters in English and numerals】");
			}
			if (sex == null || sex.trim().length() == 0) {
				errorMsg.put("errorPassword2Empty", " 【Gender must select】");
			}

			if (name == null || name.trim().length() == 0) {
				errorMsg.put("errorName", " 【Name must enter】");
			} else if (name.length() > 8) {
				errorMsg.put("errorName", " 【Name must be less than 8 characters】");
			}

			if (email == null || email.trim().length() == 0) {
				errorMsg.put("errorEmail", " 【E-mail must enter】");
			} else if (RegularExpressionUtil.isEmailValid(email) == false) {
				errorMsg.put("errorEmail", " 【Please confirm your E-mail】");
			} else if (rs.emailExists(email)) {
				errorMsg.put("errorMailDup", " 【This E-mail already exists, please change E-mail】");
			}

			if (cellphone == null || cellphone.trim().length() == 0) {
				errorMsg.put("errorTel", " 【Mobile must enter】");
			} else if (RegularExpressionUtil.isPhoneValid(cellphone) == false) {
				errorMsg.put("errorTel", " 【Please confirm your Mobile number】");
			} else if (rs.phoneExists(cellphone)) {
				errorMsg.put("errorPhoneDup", " 【This Mobile already exists, please change Mobile】");
			}

		} else {
			errorMsg.put("errTitle", " 【ERROR】");
		}

		try {
			// 4. 進行Business Logic運算
			// 儲存會員的資料

			if (!errorMsg.isEmpty()) {
				RequestDispatcher rd = request.getRequestDispatcher("/_00_register/register.jsp");
				rd.forward(request, response);
				return;
			} else {
				String GSpassword = null;
				String GSpassword1 = null;
				GSpassword = GlobalService.encryptString(password);
				GSpassword1 = GlobalService.getMD5Endocing(GSpassword);
				name = RegularExpressionUtil.removeTag(name).trim().replaceAll("\\s", "&nbsp;");
				
				
				register_bean rb = (register_bean) context.getBean("Register");
				rb.setAccount(account);
				rb.setPassword(GSpassword1);
				rb.setName(name);
				rb.setEmail(email);
				rb.setCellphone(cellphone);
				rb.setSex(sex);
				rb.setBirthDate(sqdate);
				
				
				//register_bean rb = new register_bean(account, GSpassword1, name, email, cellphone, sex, sqdate);
				// 將MemberBean mem立即寫入Database
				System.out.println("filename:" + fileName);
				int n = rs.saveMember(rb, is, sizeInBytes);
				if (n == 1) {
					msgOK.put("InsertOK", "<Font color='red'>Sign up success!</Font>");
					LoginServiceDB lsdb = (LoginServiceDB)context.getBean("LoginServiceDB");
					lsdb.populateMemberList();
					register_bean mb = lsdb.checkAccountPasswordWithoutEncode(account, GSpassword1);
					session.setAttribute("LoginOK", mb);
					response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/index.jsp"));
					return;
				} else {
					errorMsg.put("errorIDDup", "Error");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMsg.put("errorIDDup", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/_00_register/register.jsp");
			rd.forward(request, response);
		}
	}

}
