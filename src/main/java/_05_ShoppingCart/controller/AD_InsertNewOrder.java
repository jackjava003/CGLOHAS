package _05_ShoppingCart.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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
import com.google.gson.reflect.TypeToken;

import _00_init.GlobalService;
import _00_init.RegularExpressionUtil;
import _01_register.model.register_DAO;
import _01_register.model.register_bean;
import _02_login.model.LoginServiceDB;
import _05_ShoppingCart.model.OrderBean;
import _05_ShoppingCart.model.OrderDAO;

@WebServlet("/AD_InsertOrder")
public class AD_InsertNewOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println(request.getRemoteAddr());
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		OrderBean NewOrder = gson.fromJson(jsonObject.get("NewOrder").getAsString(), OrderBean.class);
		NewOrder.setShippingDate(new java.sql.Date(System.currentTimeMillis()+(3 * 24 * 60 * 60 * 1000)));
		String result = null;
		try {
			OrderDAO order = new OrderDAO();
			result = order.insertOrder(NewOrder);
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result.equalsIgnoreCase("T")) {
			writeText(response, gson.toJson("OK"));
			return;
		} else if (result.indexOf("6666666") != -1) {
			result = result.substring(result.indexOf(":") + 2, result.indexOf("6666666"));
			writeText(response, gson.toJson(result));
			return;
		}else{
			writeText(response, gson.toJson("NG"));
			return;
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
