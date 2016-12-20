package _03_recipes.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
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
import _03_recipes.model.RecipeDetailBean;
import _03_recipes.model.recipesBean;
import _03_recipes.model.recipes_DAO;
import _03_recipes.model.recipes_itemBean;
import _04_listItems.model.item_DAO;
import _04_listItems.model.item_bean;

@WebServlet("/AD_RecipesDetailServlet")
public class AD_RecipesDetailServlet extends HttpServlet {
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
		recipes_DAO rdao;
		try {
			rdao = new recipes_DAO();
			item_DAO idao = new item_DAO();

			int id = jsonObject.get("ID").getAsInt();
			System.out.println("action: " + id);
			RecipeDetailBean rdb = null;
			if (id != 0) {
				rdao.setSelectID(id);
				rdb=new RecipeDetailBean();
				Collection<recipes_itemBean> coll = rdao.getRecipesItems();
				List<item_bean> list = new ArrayList<>();
				rdb.setRecipeItems(new ArrayList<>(coll));
				rdb.setProcess(new ArrayList<>(rdao.getAndroidRecipesProcess()));
				for(recipes_itemBean rib:coll){
					item_bean ib = idao.selectName(rib.getItemID());
					list.add(ib);
				}
				rdb.setRecipeName(list);
			}
			writeText(response, gson.toJson(rdb));
		} catch (NamingException | SQLException e) {
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
