package _04_listItems.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import _04_listItems.model.OrderItemBean;
import _05_ShoppingCart.model.ShoppingCart;
//ajax使用
@WebServlet("/_04_listItems/BuyItem.do")
public class BuyItemServlet extends HttpServlet {
	// 當使用者按下『加入購物車』時，瀏覽器會送出請求到本程式
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null) { // 使用逾時
			session = request.getSession(true);
		}
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		// 取出存放在session物件內的ShoppingCart物件
		ShoppingCart cart = (ShoppingCart) session.getAttribute("ShoppingCart");

		// 如果找不到ShoppingCart物件
		if (cart == null) {
			// 就新建ShoppingCart物件
			cart = new ShoppingCart();
			// 將此新建ShoppingCart的物件放到session物件內
			session.setAttribute("ShoppingCart", cart); // ${ShoppingCart.zzz}
		}
		String itemIDStr = request.getParameter("itemID");
		String priceStr = request.getParameter("price");
		String discountStr = request.getParameter("discount");
		String qtyStr = request.getParameter("qty");

		int qty = 0;
		int itemID = 0;
		double price = 0;
		double discount = 0;

		try {
			// 進行資料型態的轉換
			qty = Integer.parseInt(qtyStr.trim());
			itemID = Integer.parseInt(itemIDStr.trim());
			price = Double.parseDouble(priceStr.trim());
			discount = Double.parseDouble(discountStr.trim());
		} catch (NumberFormatException e) {
			throw new ServletException(e);
		}
		OrderItemBean oi = new OrderItemBean(itemID, qty, price, discount);
		boolean result = cart.addToCart(itemID, oi);
		if (result == true) {
			Gson gson = new Gson();
			
			String jsonDataBean = gson.toJson(new ArrayList<>(cart.getContent().values()));
			 //System.out.println(jsonDataBean);
			PrintWriter out = response.getWriter();
			out.write(jsonDataBean);
			out.close();
		} else {
			Collection<String> error = new ArrayList<>();
			error.add("This item is out of stock");
			Gson gson = new Gson();
			String jsonDataBean = gson.toJson(error);
			PrintWriter out = response.getWriter();
			out.write(jsonDataBean);
			out.close();
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		if (session == null) { // 使用逾時
			session = request.getSession(true);
			// request.setAttribute("Errors",
			// "使用逾時，請重新登入(BuyBookServlet:SessionTimeOut)");
			// RequestDispatcher rd =
			// request.getRequestDispatcher("/_02_login/login.jsp");
			// rd.forward(request, response);
			// return;
		}
		// 取出存放在session物件內的ShoppingCart物件
		ShoppingCart cart = (ShoppingCart) session.getAttribute("ShoppingCart");

		// 如果找不到ShoppingCart物件
		if (cart == null) {
			// 就新建ShoppingCart物件
			cart = new ShoppingCart();
			// 將此新建ShoppingCart的物件放到session物件內
			session.setAttribute("ShoppingCart", cart); // ${ShoppingCart.zzz}
		}
		String pageNo = request.getParameter("pageNo");
		String qtyStr = request.getParameter("qty");
		String itemidStr = request.getParameter("itemID");
		String priceStr = request.getParameter("price");
		String discountStr = request.getParameter("discount");
		if (pageNo == null) {
			pageNo = "1";
		}
		int qty = 0;
		int itemID = 0;
		double price = 0;
		double discount = 0;

		try {
			// 進行資料型態的轉換
			qty = Integer.parseInt(qtyStr.trim());
			itemID = Integer.parseInt(itemidStr.trim());
			price = Double.parseDouble(priceStr.trim());
			discount = Double.parseDouble(discountStr.trim());
		} catch (NumberFormatException e) {
			throw new ServletException(e);
		}
		// 將訂單資料封裝到OrderItemBean內
		OrderItemBean oi = new OrderItemBean(itemID, qty, price, discount);
		// 將OrderItemBean加入ShoppingCart的物件內
		boolean result = cart.addToCart(itemID, oi);
		if (result == true) {
			RequestDispatcher rd = request
					.getRequestDispatcher("/_04_listItems/DisplayPageProducts?pageNo=" + pageNo + "&erroMessage=false");
			rd.forward(request, response);
			return;
		} else {
			RequestDispatcher rd = request
					.getRequestDispatcher("/_04_listItems/DisplayPageProducts?pageNo=" + pageNo + "&erroMessage=true");
			rd.forward(request, response);
			return;
		}
	}
}