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

@WebServlet("/AD_ProcessImgServlet")
public class AD_ProcessImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
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
		int processid = jsonObject.get("processid").getAsInt();
		int recipeid = jsonObject.get("recipeid").getAsInt();
		int imageSize = jsonObject.get("imageSize").getAsInt();

		OutputStream os = response.getOutputStream();
		recipes_DAO rdao;
		try {
			rdao = new recipes_DAO();
			byte[] image = rdao.getProcessImage(processid, recipeid);
			System.out.println(image.length);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
			}
			os.write(image);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
