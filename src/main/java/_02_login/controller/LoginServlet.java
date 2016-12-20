package _02_login.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _00_init.GlobalService;
import _00_init.RegularExpressionUtil;
import _01_register.model.register_bean;
import _02_login.model.LoginIPBean;
import _02_login.model.LoginIPDAO;
import _02_login.model.LoginServiceDB;

@WebServlet("/_02_login/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		LoginIPDAO lidao = new LoginIPDAO();
		LoginIPBean lib = lidao.getIPBean(request.getRemoteAddr());
		boolean del = false;
		if (lib != null) {
			long lastTryTime = lib.getLastTryTime().getTime();
			long now = System.currentTimeMillis();
			long diff = now - lastTryTime;
			if (diff > (1000 * 60 * 60 * 3)) {
				lidao.delIP(lib);
				System.out.println("DEL");
				del = true;
			} else if (lib.getWrongTimes() == 3) {
				int nextLoginTime = (int) (lastTryTime + (3 * 60 * 60 * 1000) - now) / 1000;
				int hour = nextLoginTime / (60 * 60);
				int min = (nextLoginTime - (hour * 60 * 60)) / 60;
				response.sendRedirect(response.encodeRedirectURL("/CGLOHAS/_02_login/BlockError.jsp?ip="
						+ request.getRemoteAddr() + "&timeHour=" + hour + "&timeMin=" + min));
				return;
			}
		} else {
			// 第一次登入失敗 或登入失敗小於3 可執行查詢
		}

		HttpSession session = request.getSession();
		// 準備存放錯誤訊息的Map物件
		Map<String, String> errorMsgMap = new HashMap<String, String>();
		// 將errorMsgMap放入request物件內，識別字串為 "ErrorMsgKey"
		request.setAttribute("ErrorMsgKey", errorMsgMap);
		// 1. 讀取使用者輸入資料
		String Account = request.getParameter("Account");
		String password = request.getParameter("pswd");
		// String rm = request.getParameter("rememberMe");
		String requestURI = (String) session.getAttribute("requestURI");
		System.out.println(Account);
		System.out.println(password);
		// 2. 進行必要的資料轉換
		// 無
		// 3. 檢查使用者輸入資料
		// 如果 userId 欄位為空白，放一個錯誤訊息到 errorMsgMap 之內
		boolean block = false;
		if (Account == null || Account.trim().length() == 0) {
			errorMsgMap.put("AccountEmptyError", "Account must enter");
		} else if (RegularExpressionUtil.isAcValid(Account) == false) {
			errorMsgMap.put("AccountEmptyError", "Account error");
			block = true;
		}
		// 如果 password 欄位為空白，放一個錯誤訊息到 errorMsgMap 之內
		if (password == null || password.trim().length() == 0) {
			errorMsgMap.put("PasswordEmptyError", "Password must enter");
		} else if (RegularExpressionUtil.isPwValid(password) == false) {
			errorMsgMap.put("PasswordEmptyError", "Password error");
			block = true;
		}

		// **********Remember Me****************************
		// Cookie cookieUser = null;
		// Cookie cookiePassword = null;
		// Cookie cookieRememberMe = null;
		// String encodePassword = null;
		// String encodePassword2 = null;
		// if (rm != null) { // rm存放瀏覽器送來之RememberMe的選項
		// cookieUser = new Cookie("user", Account);
		// cookieUser.setMaxAge(30 * 60 * 60);
		// cookieUser.setPath(request.getContextPath());
		// // 稍微編碼(不算是加密)
		// // String encodePassword =
		// // DatatypeConverter.printBase64Binary(password.getBytes());
		// encodePassword = GlobalService.encryptString(password);
		// encodePassword2 = GlobalService.getMD5Endocing(encodePassword);
		// System.out.println("--->" + encodePassword2 + "<---");
		// cookiePassword = new Cookie("password", encodePassword2);
		// cookiePassword.setMaxAge(30 * 60 * 60);
		// cookiePassword.setPath(request.getContextPath());
		// cookieRememberMe = new Cookie("rm", "true");
		// cookieRememberMe.setMaxAge(30 * 60 * 60);
		// cookieRememberMe.setPath(request.getContextPath());
		// } else {
		// cookieUser = new Cookie("user", Account);
		// cookieUser.setMaxAge(0); // MaxAge==0 表示要請瀏覽器刪除此Cookie
		// cookieUser.setPath(request.getContextPath());
		// // String encodePassword =
		// // DatatypeConverter.printBase64Binary(password.getBytes());
		// encodePassword = GlobalService.encryptString(password);
		// encodePassword2 = GlobalService.getMD5Endocing(encodePassword);
		// System.out.println("--->" + encodePassword2 + "<---");
		// cookiePassword = new Cookie("password", encodePassword2);
		// cookiePassword.setMaxAge(0);
		// cookiePassword.setPath(request.getContextPath());
		// cookieRememberMe = new Cookie("rm", "false");
		// cookieRememberMe.setMaxAge(30 * 60 * 60);
		// cookieRememberMe.setPath(request.getContextPath());
		// }
		// response.addCookie(cookieUser);
		// response.addCookie(cookiePassword);
		// response.addCookie(cookieRememberMe);
		// ********************************************
		// 如果 errorMsgMap 不是空的，表示有錯誤，交棒給login.jsp
		if (!errorMsgMap.isEmpty()) {
			
			int timeLeft = 0;
			if ((lib == null || del == true)&&block==true) {
				lib = new LoginIPBean(request.getRemoteAddr(), 1, new Timestamp(System.currentTimeMillis()));
				lidao.insertIP(lib);
				timeLeft = 2;
			} else if(block==true) {
				lib.setWrongTimes(lib.getWrongTimes() + 1);
				timeLeft = 3 - (lib.getWrongTimes());
				lib.setLastTryTime(new Timestamp(System.currentTimeMillis()));
			} else {
				 timeLeft=3;
			}
			if (timeLeft != 0) {
				if(timeLeft != 3){
					request.setAttribute("timeLeft", timeLeft);
				}
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
				return;
			} else {
				response.sendRedirect(response.encodeRedirectURL("BlockError.jsp?ip="
						+ request.getRemoteAddr() + "&timeHour=3&timeMin=00"));
				return;
			}
		}
		// 4. 進行 Business Logic 運算
		// 將LoginServiceDB類別new為物件，存放物件參考的變數為 lsdb
		LoginServiceDB lsdb;
		try {
			lsdb = new LoginServiceDB();
			// 呼叫 ms物件的 checkIDPassword()，要記得傳入userid與password兩個參數

			register_bean mb = lsdb.checkAccountPassword(Account, password);
			if (mb != null) {
				// OK, 將mb物件放入Session範圍內，識別字串為"LoginOK"
				session.setAttribute("LoginOK", mb);
			} else {
				// NG, userid與密碼的組合錯誤，放一個錯誤訊息到 errorMsgMap 之內
				errorMsgMap.put("LoginError", "Account does not exist or the password is incorrect");
			}
		} catch (NamingException e) {
			errorMsgMap.put("LoginError", "LoginServlet->NamingException:" + e.getMessage());
		} catch (SQLException e) {
			errorMsgMap.put("LoginError", "LoginServlet->SQLException:" + e.getMessage());
			e.printStackTrace();
		}

		// 5.依照 Business Logic 運算結果來挑選適當的畫面
		// 如果 errorMsgMap 是空的，表示沒有任何錯誤，交棒給下一棒
		if (errorMsgMap.isEmpty()) {
			// 此時不要用下面兩個敘述，因為網址列的URL不會改變
			// RequestDispatcher rd = request.getRequestDispatcher("...");
			// rd.forward(request, response);

			String recipes = request.getParameter("fromRecipes");
			// recipes給我的最愛使用 登入會員後自動跳回觀看食譜頁面
			if (recipes != null && recipes.length() != 0) {
				String pageNo = request.getParameter("pageNo");
				String type = request.getParameter("type");
				response.sendRedirect(
						response.encodeRedirectURL(request.getContextPath() + "/_03_recipes/RecipesDetail.jsp?id="
								+ recipes + "&pageNo=" + pageNo + "&type=" + URLEncoder.encode(type, "UTF-8")));
				return;
			} else if (requestURI != null) {

				requestURI = (requestURI.length() == 0 ? request.getContextPath() : requestURI);
				// System.out.print(requestURI);
				response.sendRedirect(response.encodeRedirectURL(requestURI));
				return;
			} else {
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath()));
				return;
			}
		} else {
			// 如果errorMsgMap不是空的，表示有錯誤，交棒給login.jsp
			int timeLeft = 0;
			System.out.println(del);
			if (lib == null || del == true) {
				lib = new LoginIPBean(request.getRemoteAddr(), 1, new Timestamp(System.currentTimeMillis()));
				lidao.insertIP(lib);
				timeLeft = 2;
			} else {
				lib.setWrongTimes(lib.getWrongTimes() + 1);
				timeLeft = 3 - (lib.getWrongTimes());
				lib.setLastTryTime(new Timestamp(System.currentTimeMillis()));
			}
			if (timeLeft != 0) {
				request.setAttribute("timeLeft", timeLeft);
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
				return;
			} else {
				response.sendRedirect(response.encodeRedirectURL("BlockError.jsp?ip="
						+ request.getRemoteAddr() + "&timeHour=3&timeMin=00"));
				return;
			}
		}
	}
}
