package _08_favour.controller;

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

import _01_register.model.register_bean;
import _03_recipes.model.recipesBean;
import _03_recipes.model.recipes_DAO;
import _08_favour.model.FavourBean;
import _08_favour.model.ManageMyFavourDAO;
//無使用 改JSP直接useBean
//import _00_init.GlobalService;
//import _01_register.model.MemberBean;

@WebServlet("/_08_favour/DisplayPageFavour.do")

public class DisplayPageFavour extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int favourpageNo = 1;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ManageMyFavourDAO afd;
		try {
			afd = new ManageMyFavourDAO();

			// try {
			// pageNo控制2個邏輯,1.儲存返回頁面(如果有cookie) 2.控制SQL存取顯示在JSP頁面的item(JSP上下頁加減)
			String pageNoStr = request.getParameter("favourPageNo");
			register_bean rb = (register_bean) request.getSession().getAttribute("LoginOK");
			String recipeIDStr = request.getParameter("id");
			

			if (rb == null) {
				RequestDispatcher rdr = request
						.getRequestDispatcher("/_03_recipes/RecipesDetail.jsp?id=" + recipeIDStr);
				rdr.forward(request, response);
				return;
			}
			int userID = rb.getUserID();
			int recipeID = 1;
			String from = "";
			if (recipeIDStr != null) {
				if (recipeIDStr.trim().length() != 0) {
					recipeID = Integer.valueOf(recipeIDStr);
				}
			} else {
				from = request.getParameter("from");
			}
			if (pageNoStr == null) {
				favourpageNo = 1;
			} else {
				try {
					favourpageNo = Integer.parseInt(pageNoStr.trim());
					System.out.println(favourpageNo);
				} catch (NumberFormatException e) {
					favourpageNo = 1;
				}
			}

			afd.setPageNo(favourpageNo);
			afd.setFavourUserID(userID);
			Collection<FavourBean> coll = afd.getPageFavour();
			request.setAttribute("favourPageNo", favourpageNo);
			request.setAttribute("totalPages", afd.getTotalPages());
			request.setAttribute("favour", coll);
			
			System.out.println("DPF"+recipeID);
			
			if (from.equalsIgnoreCase("memberCenter")&&recipeIDStr==null) {
				RequestDispatcher rdr = request.getRequestDispatcher("/_08_favour/ShowMyFavour.jsp");
				rdr.forward(request, response);
				return;
			} else {
				RequestDispatcher rdr = request.getRequestDispatcher("/_03_recipes/RecipesDetail.jsp?id=" + recipeID);
				rdr.forward(request, response);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}