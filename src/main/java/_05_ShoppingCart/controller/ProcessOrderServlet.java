package _05_ShoppingCart.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _00_init.GlobalService;
import _00_init.RegularExpressionUtil;
import _00_init.VerifyUtils;
import _01_register.model.register_bean;
import _04_listItems.model.OrderItemBean;
import _05_ShoppingCart.model.OrderBean;
import _05_ShoppingCart.model.OrderDAO;
import _05_ShoppingCart.model.OrderItemDAOBean;
import _05_ShoppingCart.model.ShoppingCart;

// OrderConfirm.jsp 呼叫本程式
@WebServlet("/_05_ShoppingCart/ProcessOrder.do")
public class ProcessOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");

			String finalDecision = request.getParameter("finalDecision");
			HttpSession session = request.getSession(false);
			if (session == null) { // 使用逾時
				response.sendRedirect(getServletContext().getContextPath() + "/index.jsp");
				return;
			}
			register_bean mb = (register_bean) session.getAttribute("LoginOK");
			if (mb == null) {
				response.sendRedirect(getServletContext().getContextPath() + "/index.jsp");
				return;
			}
			ShoppingCart sc = (ShoppingCart) session.getAttribute("ShoppingCart");
			if (sc == null) {
				// 如果找不到購物車(通常是Session逾時)，沒有必要往下執行
				// 導向首頁
				response.sendRedirect(getServletContext().getContextPath() + "/index.jsp");
				return;
			}
			if (finalDecision.equals("CANCEL")) {
				session.removeAttribute("ShoppingCart");
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath()));
				return; // 一定要記得 return
			}

			Map<String, String> errorMsg = new HashMap<String, String>();
			request.setAttribute("MsgMap", errorMsg); // 顯示錯誤訊息
			String zipCode = request.getParameter("zipcode");
			String city = request.getParameter("zone1");
			String area = request.getParameter("zone2");
			String shippingAddress = request.getParameter("address");
			String description = request.getParameter("Description");
			String payOption = request.getParameter("payOption");
			boolean codeCheck = true;
			String cardOwnerName = "";
			String cardno = "";
			String cardMonth = "";
			String cardYear = "";
			String cardThreedNum ="";
			if (zipCode.trim().length() == 0 || city.trim().length() == 0 || area.trim().length() == 0) {
				errorMsg.put("errorArea", " 【Please select city】");
				codeCheck = false;
			}
			System.out.println(shippingAddress);
			if (shippingAddress == null || shippingAddress.trim().length() == 0) {
				errorMsg.put("errorAddress", " 【Please enter address】");
			} 
//			else if (shippingAddress.indexOf("號") == -1 && shippingAddress.indexOf("号") == -1) {
//				errorMsg.put("errorAddress", " 【請確認出貨地址】");
//			} 
//			else if (codeCheck == true) {
//				shippingAddress = zipCode + city + area + shippingAddress;
//				if (VerifyUtils.verifyAdd(shippingAddress) == null
//						|| !VerifyUtils.verifyAdd(shippingAddress).equalsIgnoreCase("OK")) {
//					errorMsg.put("errorAddress", " 【請重新確認出貨地址】");
//				}
//			}

			if (payOption==null||payOption.trim().length() == 0) {
				errorMsg.put("errorPayOption", " 【Please select payment option】");
			} else if (payOption.equalsIgnoreCase("信用卡")) {
				cardOwnerName = request.getParameter("cardOwnerName");
				cardno = request.getParameter("cardno");
				cardMonth = request.getParameter("cardMonth");
				cardYear = request.getParameter("cardYear");
				cardThreedNum = request.getParameter("cardThreedNum");
				if (cardOwnerName == null || cardOwnerName.trim().length() == 0) {
					errorMsg.put("errorCardOwnerName", " 【Please enter Name】");
				}
				if (cardno == null || cardno.trim().length() == 0) {
					errorMsg.put("errorCardno", " 【Please enter card number】");
				} else if (VerifyUtils.verifyCardNum(cardno) == false) {
					errorMsg.put("errorCardno", " 【Card number error】");
				}

				if (cardMonth== null || cardYear.trim().length() == 0) {
					errorMsg.put("errorCardDate", " 【Please select card expire date】");
				}
				if(cardThreedNum== null || cardThreedNum.trim().length() == 0){
					errorMsg.put("errorCardThreedNum", " 【Please enter CVV number】");
				}
			}

			if (description != null && description.trim().length() != 0 && description.trim().length() > 100) {
				errorMsg.put("errorDesc", " 【Can not exceed 100 characters】");
			}

			if (!errorMsg.isEmpty()) {
				RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/OrderConfirm.jsp");
				rd.forward(request, response);
				return;
			}

			int userId = mb.getUserID();
			String cellphone = mb.getCellphone();
			double totalAmount = (int) (sc.getSubtotal() - Math.floor(sc.getSubtotal() / 1000.0) * 100);
			System.out.println(totalAmount);
			int discount = (int) (Math.floor(sc.getSubtotal() / 1000.0) * 100);

			shippingAddress = RegularExpressionUtil.removeTag(shippingAddress);
			description = RegularExpressionUtil.removeTag(description).trim().replaceAll("\r\n", "<br>")
					.replaceAll("\\s", "&nbsp;");

			Date today = (Date) session.getAttribute("today");
			java.sql.Date orderDate = new java.sql.Date(today.getTime());
			long time = today.getTime() + (3 * 24 * 60 * 60 * 1000);
			java.sql.Date shippingDate = new java.sql.Date(time);
			List<OrderItemDAOBean> items = new ArrayList<OrderItemDAOBean>();
			Map<Integer, OrderItemBean> cart = sc.getContent();
			Set<Integer> set = cart.keySet();
			for (Integer k : set) {
				OrderItemBean oib = cart.get(k);
				OrderItemDAOBean oiDAO = new OrderItemDAOBean(oib.getItemid(), oib.getQty(), oib.getUnitPrice(),
						oib.getDiscount());
				items.add(oiDAO);
			}
			// OrderBean:封裝一筆訂單資料的容器(包含訂單主檔與訂單明細檔的資料)
			description = description.trim().replaceAll("\r\n", "<br>").replaceAll("\\s", "&nbsp;");
			OrderBean ob = new OrderBean(userId, shippingAddress, totalAmount, cellphone, "F", shippingDate,
					description, discount, items);
			OrderDAO order = new OrderDAO();
			String result = order.insertOrder(ob);
			if (payOption.equalsIgnoreCase("信用卡")) {
				System.out.println("持卡人:" + cardOwnerName + " 卡號加密:" + GlobalService.encryptString(cardno)
						+ " 有效日期:" + cardMonth + "月/" + cardYear + "年");
			} else {
				System.out.println("貨到付款");
			}
			if (result.equalsIgnoreCase("T")) {
				session.removeAttribute("ShoppingCart");
				response.sendRedirect(
						response.encodeRedirectURL(getServletContext().getContextPath() + "/ThanksForOrdering.jsp"));
				return;
			} else if (result.indexOf("6666666") != -1) {
				result = result.substring(result.indexOf(":") + 2, result.indexOf("6666666"));
				request.setAttribute("stockError", Integer.parseInt(result));
				RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/OrderConfirm.jsp");
				rd.forward(request, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("Error", "Failed to create the order");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/OrderConfirm.jsp");
			rd.forward(request, response);
			return;
		}
	}
}