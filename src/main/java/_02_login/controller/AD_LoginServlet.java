package _02_login.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import _01_register.model.register_bean;
import _02_login.model.LoginServiceDB;

@WebServlet("/AD_LoginServlet")
public class AD_LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getRemoteAddr());
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String password = jsonObject.get("password").getAsString();
		String account = jsonObject.get("account").getAsString();
		System.out.println("password: " + password + ", " + account);

		LoginServiceDB lsdb;
		try {
			lsdb = new LoginServiceDB(true);
			register_bean mb = lsdb.checkAccountPassword(account, password);
			if (mb != null) {
				mb.setMemberImage(null);
				writeText(response, gson.toJson(mb));
			} else {
				writeText(response, null);
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}

	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		// System.out.println("outText: " + outText);
		out.print(outText);
		System.out.println(outText);
	}

}
