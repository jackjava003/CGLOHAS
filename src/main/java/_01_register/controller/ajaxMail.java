package _01_register.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import _01_register.model.register_DAO;

@WebServlet("/_01_register/ajaxEmail.do")
public class ajaxMail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession(true);
		}
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		Gson gson = new Gson();
		List<String> result = new ArrayList<>();
		String mail = request.getParameter("mail");
		//System.out.println(mail);
		register_DAO rs = new register_DAO();
		if(rs.emailExists(mail)){
			result.add("RepeatedEmail");
		}else {
			result.add("OK");
		}
		String jsonDataBean = gson.toJson(result);
		PrintWriter out = response.getWriter();
		out.write(jsonDataBean);
		out.close();
		return;
	}
}
