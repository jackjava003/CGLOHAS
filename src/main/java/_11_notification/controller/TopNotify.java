package _11_notification.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import _01_register.model.register_bean;
import _11_notification.model.NotifyBean;
import _11_notification.model.NotifyDAO;

@WebServlet("/_11_notification/TopNotify.do")
public class TopNotify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
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
		int userID = rb.getUserID();

		// // 抓使用者的Notification，每次載入本Servlet，就要抓一次資料庫看有沒有最新通知。
		Collection<NotifyBean> coll = new ArrayList<NotifyBean>();
		NotifyDAO ndao;
		try {
			ndao = new NotifyDAO();
			ndao.setReceiverID(userID);
			coll = ndao.getPageNotify();
			Collection<NotifyBean> coll2 = new ArrayList<NotifyBean>();
			NotifyBean nb = new NotifyBean();
			nb.setCheckRead(ndao.getRecordUnreadCounts());
			coll2.add(nb);
			for (NotifyBean x : coll) {
				NotifyBean nbx = x;
				nb.setRealTime();
				coll2.add(nbx);
			}
			Gson gson = new Gson();
			String jsonDataBean = gson.toJson(coll2);
			//System.out.println(jsonDataBean);
			PrintWriter out = response.getWriter();
			out.write(jsonDataBean);
			out.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
