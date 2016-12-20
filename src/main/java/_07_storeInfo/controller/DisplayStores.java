package _07_storeInfo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import _07_storeInfo.model.StoreAccessDAO;
import _07_storeInfo.model.StoreAccessDetailBean;

@WebServlet("/_07_storeInfo/DisplayStores.do")
public class DisplayStores extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int storeid;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("123123123");
		
		 doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		response.setContentType("application/json; charset=utf-8");

		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("UTF-8");
		String sid = request.getParameter("id");
		System.out.println("storeid(sid)為" + sid);
		int id = 0 ;
		try {
			id = Integer.parseInt(sid);
		}catch(Exception e){
			e.printStackTrace();
			id = 0;
		}
		
		try {
			StoreAccessDAO sad = new StoreAccessDAO();
			sad.setSelectStoreID(id);
			List<StoreAccessDetailBean> list = sad.getSelectLocDetail();
			String placeJson = new Gson().toJson(list);
			request.setAttribute("placeJson", placeJson);
			request.setAttribute("id", String.valueOf(id));
			RequestDispatcher rd = request.getRequestDispatcher("/_07_storeInfo/storeInfoDetail.jsp");
			rd.forward(request, response);
			return;
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
			// throw new ServletException("DB error", e);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

}
