package _04_listItems.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _04_listItems.model.OrderItemBean;
import _05_ShoppingCart.model.ShoppingCart;

//無使用  改用ajax
@WebServlet("/_04_listItems/BuyItemDetail.do")
public class BuyItemDetailServlet extends HttpServlet {
	// 當使用者按下『加入購物車』時，瀏覽器會送出請求到本程式
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
//		if (session == null) {      // 使用逾時
//			request.setAttribute("Errors", "使用逾時，請重新登入(BuyBookServlet:SessionTimeOut)");
//			RequestDispatcher rd = request.getRequestDispatcher("/_02_login/login.jsp");
//			rd.forward(request, response);
//			return;
//		}
		// 取出存放在session物件內的ShoppingCart物件
		ShoppingCart cart = (ShoppingCart)session.getAttribute("ShoppingCart");
		// 如果找不到ShoppingCart物件
		if (cart == null) {
			// 就新建ShoppingCart物件
			cart = new ShoppingCart();
			// 將此新建ShoppingCart的物件放到session物件內
			session.setAttribute("ShoppingCart", cart);   // ${ShoppingCart.zzz}	
		}
//		String pageNo 		= request.getParameter("pageNo");
		String qtyStr 		= request.getParameter("qty");
		String itemidStr 		= request.getParameter("itemID");
		String priceStr 	= request.getParameter("price");
		String discountStr 	= request.getParameter("discount");
		System.out.println("123123123   "+qtyStr+"  "+itemidStr+"  "+"   "+priceStr+"  "+discountStr);
//		if (pageNo == null){
//			pageNo = "1";
//		}
		
		
		int qty = 0 ; 
		int itemID = 0 ;
		double price = 0 ; 
		double discount = 0 ;
		
		try{
			// 進行資料型態的轉換
			qty = Integer.parseInt(qtyStr.trim());
			itemID = Integer.parseInt(itemidStr.trim());
			price = Double.parseDouble(priceStr.trim());
			discount = Double.parseDouble(discountStr.trim());
			
		} catch(NumberFormatException e){
			throw new ServletException(e); 
		}
		// 將訂單資料封裝到OrderItemBean內
		OrderItemBean oi = new OrderItemBean(itemID, qty, price, discount);
		// 將OrderItemBean加入ShoppingCart的物件內
		boolean result = cart.addToCart(itemID, oi);
		
		if(result == true){
			RequestDispatcher rd = request.getRequestDispatcher("/_04_listItems/iteminfo.jsp?id=" + itemidStr);
			rd.forward(request, response);
			return;
			}else{
				request.setAttribute("erroMessage", "This item is out of stock");
				RequestDispatcher rd = request.getRequestDispatcher("/_04_listItems/iteminfo.jsp?id=" + itemidStr);
				rd.forward(request, response);
				return;
			}
		

	}
}