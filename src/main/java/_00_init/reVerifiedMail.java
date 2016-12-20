package _00_init;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _01_register.model.register_DAO;
import _01_register.model.register_bean;

/**
 * Servlet implementation class reVerifiedMail
 */
@WebServlet("/reVerifiedMail.do")
public class reVerifiedMail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		register_bean rb = (register_bean) session.getAttribute("LoginOK");
		register_DAO rdao = new register_DAO();
		SendMailThread smt = new SendMailThread(rb.getEmail(), rb.getAccount(), rb.getPassword());
		smt.start();

		response.sendRedirect(response.encodeRedirectURL("_01_userUpdate/UpdateSucess.jsp?message="
				+ URLEncoder.encode("信件已寄出  2秒後跳轉會員中心<br>", "UTF-8") + "&type=reverified"));
		return;
	}

}
