package _08_favour.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import _00_init.ImageUtil;
import _03_recipes.model.recipesBean;
import _03_recipes.model.recipes_DAO;
import _04_listItems.model.item_DAO;
import _04_listItems.model.item_bean;
import _08_favour.model.FavourBean;
import _08_favour.model.ManageMyFavourDAO;

@WebServlet("/AD_addFav")
public class AD_AddFavServlet extends HttpServlet {
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
		int result = -1;
		int userID = jsonObject.get("userID").getAsInt();
		int recipeID = jsonObject.get("recipeID").getAsInt();
		ManageMyFavourDAO fbd = new ManageMyFavourDAO();
		FavourBean fb = new FavourBean();
		fb.setUserID(userID);
		fb.setRecipesID(recipeID);
		result = fbd.insert(fb);
		if (result == 1) {
			writeText(response, gson.toJson("OK"));
		}else{
			writeText(response, gson.toJson("NG"));
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
