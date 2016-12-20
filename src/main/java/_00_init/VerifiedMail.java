package _00_init;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import _01_register.model.register_bean;
import _02_login.model.LoginServiceDB;

@WebServlet("/VerifiedMail")
public class VerifiedMail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String user = request.getParameter("user");
		String verified = request.getParameter("verifiedCode");
		try {

			Context context = new InitialContext();
			// =======================================
			// 透過JNDI取得DataSource物件
			DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/cglohas");
			try (Connection conn = ds.getConnection();) {
				PreparedStatement pstmt = null;
				if (user != "" && verified != "") { // 讀取user_photo表格
					pstmt = conn.prepareStatement(
							"UPDATE register_user SET verified = 'T' where account = ? and password = ?");
				}
				System.out.println(user + " " + verified);
				pstmt.setString(1, user);
				pstmt.setString(2, verified);
				int n = pstmt.executeUpdate();
				System.out.println(n);
				if (n == 1) {
					String result = "Validation was successful, please wait 2 seconds redirect to home page";
					// request.setAttribute("verifiedResultOK", result);
					HttpSession session = request.getSession();
					LoginServiceDB lsdb;
					lsdb = new LoginServiceDB();
					// 呼叫 ms物件的 checkIDPassword()，要記得傳入userid與password兩個參數

					register_bean mb = lsdb.checkAccountPasswordWithoutEncode(user, verified);
					if (mb != null) {
						// OK, 將mb物件放入Session範圍內，識別字串為"LoginOK"
						mb.setVerified("T");
						session.setAttribute("LoginOK", mb);
					} else {
						System.out.println("mb = null");
					}
					response.sendRedirect(
							response.encodeRedirectURL("_00_register/verified.jsp?verifiedResultOK=" + URLEncoder.encode(result, "UTF-8")));
					return;
				} else {
					String result = "Validation was failed, please wait 2 seconds redirect to home page";
					// request.setAttribute("verifiedResultNO", result);
					response.sendRedirect(
							response.encodeRedirectURL("_00_register/verified.jsp?verifiedResultNO=" + URLEncoder.encode(result, "UTF-8")));
					return;
				}
			}

		} catch (NamingException e) {
			throw new ServletException(e);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

}
