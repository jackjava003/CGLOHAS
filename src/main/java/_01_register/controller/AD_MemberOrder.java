package _01_register.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
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

import _04_listItems.model.item_DAO;
import _04_listItems.model.item_bean;
import _05_ShoppingCart.model.OrderBean;
import _05_ShoppingCart.model.OrderDAO;
import _05_ShoppingCart.model.OrderItemDAOBean;
import _06_orderProcess.model.ManageOrderDAO;

@WebServlet("/AD_MemberOrder")
public class AD_MemberOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		item_DAO idao;
		try {
			idao = new item_DAO();
			List<item_bean> items = idao.getAll();
			writeText(response, new Gson().toJson(items));
		} catch (NamingException e) {
			e.printStackTrace();
		}
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
		
		try {
			int userID = jsonObject.get("userID").getAsInt();
			ManageOrderDAO mpd = (ManageOrderDAO) context.getBean("ManageOrderDAO");
			mpd.setOrderUserID(userID);
			List<OrderBean> list = new ArrayList<>(mpd.getUserOrder());
			List<OrderBean> listBean = new ArrayList<>();
			OrderDAO ordDAO = (OrderDAO) context.getBean("OrderDAO");
			item_DAO idao = (item_DAO) context.getBean("item_DAO");
			for(OrderBean ob:list){
				OrderBean obNew = (OrderBean) context.getBean("OrderBean");
				obNew = ordDAO.getOrder(ob.getOrderID());
				obNew.setShippingDate(null);
				for(OrderItemDAOBean oib:obNew.getItems()){
					oib.setName(idao.select(oib.getItemID()).getName());
				}
				listBean.add(obNew);
			}
			writeText(response, gson.toJson(listBean));
		} catch (SQLException  e) {
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
