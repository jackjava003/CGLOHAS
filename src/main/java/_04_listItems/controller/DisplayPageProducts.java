package _04_listItems.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _04_listItems.model.item_DAO;
import _04_listItems.model.item_bean;

//import _00_init.GlobalService;
//import _01_register.model.MemberBean;
//無使用  已修改為JSP直接呼叫

@WebServlet("/_04_listItems/DisplayPageProducts")

                            
public class DisplayPageProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int pageNo = 1;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("123123123");
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 先取出session物件
		HttpSession session = request.getSession(true);
		
		// 紀錄目前請求的RequestURI,以便使用者登入成功後能夠回到原本的畫面
		// 如果session物件不存在
//		if (session == null) {
//			// 請使用者登入
//			response.sendRedirect(response
//					.encodeRedirectURL("../_02_login/login.jsp"));
//			return;
//		}
//		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");
//		String memberId = mb.getMemberId();
		//String memberId = "";
		// 本類別負責讀取資料庫內eBook表格內某一頁的紀錄，並能新增紀錄、修改紀錄、刪除記錄等
		item_DAO iab;
		try {
			iab = new item_DAO();
		
//		try {
			//pageNo控制2個邏輯,1.儲存返回頁面(如果有cookie) 2.控制SQL存取顯示在JSP頁面的item(JSP上下頁加減)
			String pageNoStr = request.getParameter("pageNo");
			String erroMessage = request.getParameter("erroMessage");
			if(erroMessage!=null && erroMessage.length()!=0 && erroMessage.equalsIgnoreCase("true")){
				request.setAttribute("erroMessage", "This item is out of stock");
			}
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
			iab.setPageNo(pageNo);
//			bab.setRecordsPerPage(GlobalService.RECORDS_PER_PAGE);
			// iab.getPageItems():讀取某一頁的所有紀錄
			Collection<item_bean> coll = iab.getPageItems();
			request.setAttribute("pageNo", pageNo);
			
			request.setAttribute("totalPages", iab.getTotalPages());
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
			RequestDispatcher rd = request
					.getRequestDispatcher("/_04_listItems/listItem.jsp");
			rd.forward(request, response);
			return;
//		} catch (SQLException e) {
//			throw new ServletException(e);
//		} catch (NamingException e) {
//			throw new ServletException(e);
//		}
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}