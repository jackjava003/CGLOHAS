package _07_storeInfo.controller;

import java.io.IOException;
//import java.sql.SQLException;
import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import _03_recipes.model.recipesBean;
//import _03_recipes.model.recipes_DAO;
//import _04_listItems.model.item_bean;
import _07_storeInfo.model.StoreAccessDAO;

//import _00_init.GlobalService;
//import _01_register.model.MemberBean;


@WebServlet("/_07_storeInfo/DisplayPageRecipes.do")

                            
public class DisplayPageRecipes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int pageNo = 1;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		StoreAccessDAO rd;
		try {
			rd = new StoreAccessDAO();
		
//		try {
			//pageNo控制2個邏輯,1.儲存返回頁面(如果有cookie) 2.控制SQL存取顯示在JSP頁面的item(JSP上下頁加減)
			String pageNoStr = request.getParameter("pageNo");
			if (pageNoStr == null) {
				pageNo = 1;
//				Cookie[] cookies = request.getCookies();
//				if (cookies != null) {
//					for (Cookie c : cookies) {
//						//System.out.println("DisplayPageProducts.java, getName=" + c.getName());
//						if (c.getName().equals(memberId + "pageNo")) {
//
//							try {
//								pageNo = Integer.parseInt(c.getValue().trim());
//							} catch (NumberFormatException e) {
//								;
//							}
//							break;
//						}
//					}
//				}
			} else {
				try {
					pageNo = Integer.parseInt(pageNoStr.trim());
					System.out.println(pageNo);
				} catch (NumberFormatException e) {
					pageNo = 1;
				}
			}
			//
			rd.setPageNo(pageNo);
//			bab.setRecordsPerPage(GlobalService.RECORDS_PER_PAGE);
			// iab.getPageItems():讀取某一頁的所有紀錄
			Collection<recipesBean> coll = rd.getPageRecipes();
			request.setAttribute("pageNo", pageNo);
			
			request.setAttribute("totalPages", rd.getTotalPages());
//			request.setAttribute("recordsPerPage",
//					GlobalService.RECORDS_PER_PAGE);
			request.setAttribute("products_DPP", coll);                  
			// -----------------------
//			Cookie pnCookie = new Cookie(memberId + "pageNo",
//					String.valueOf(pageNo));
			//pnCookie.setMaxAge(30 * 24 * 60 * 60);
			//pnCookie.setPath(request.getContextPath());
			//response.addCookie(pnCookie);
			// -----------------------
			// 交由listBooks.jsp來顯示某頁的書籍資料，同時準備『第一頁』、
			// 『前一頁』、『下一頁』、『最末頁』等資料
			RequestDispatcher rdr = request
					.getRequestDispatcher("/_07_storeInfo/storeInfo.jsp");
			rdr.forward(request, response);
			return;
//		} catch (SQLException e) {
//			throw new ServletException(e);
//		} catch (NamingException e) {
//			throw new ServletException(e);
//		}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}