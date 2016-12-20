package _01_register.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import _01_register.model.Hibernate_register_DAO;

@WebServlet("/AD_UserImageServlet")
public class AD_UserImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		ServletContext sc = getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);
		Hibernate_register_DAO hrd = (Hibernate_register_DAO) context.getBean("Hibernate_Register");
		
		String action = jsonObject.get("action").getAsString();
		System.out.println("action: " + action);
		OutputStream os = response.getOutputStream();
		int id = jsonObject.get("id").getAsInt();
		int imageSize = jsonObject.get("imageSize").getAsInt();
		Blob image = hrd.getUserImage(id);
		try {
			os.write(image.getBytes(1, (int) image.length()));
			image.free();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
