package _04_listItems.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import _04_listItems.model.item_DAO;
import _04_listItems.model.item_bean;

@WebServlet("/ItemServlet")
public class AD_ItemServlet extends HttpServlet {
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
		item_DAO itemDao;
		try {
			itemDao = new item_DAO();

			String action = jsonObject.get("action").getAsString();
			System.out.println("action: " + action);

			if (action.equals("getAll")) {
				String type = jsonObject.get("type").getAsString();
				List<item_bean> items = null;
				if (type == null || type.trim().length() == 0) {
					items = itemDao.getAll();
				} else {
					itemDao.setType(type);
					items = itemDao.getAll();
				}
				writeText(response, gson.toJson(items));
			} else if (action.equals("getImage")) {
				OutputStream os = response.getOutputStream();
				int id = jsonObject.get("id").getAsInt();
				System.out.println(id);
				int imageSize = jsonObject.get("imageSize").getAsInt();
				byte[] image = itemDao.getImage(id);
				System.out.println(image.length);
				if (image != null) {
					 image = ImageUtil.shrink(image, imageSize);
					 response.setContentType("image/jpeg");
					 response.setContentLength(image.length);
					
				}
				os.write(image);
			} else if (action.equals("itemInsert") || action.equals("itemUpdate")) {
				String itemJson = jsonObject.get("item").getAsString();
				item_bean item = gson.fromJson(itemJson, item_bean.class);
				String imageBase64 = jsonObject.get("imageBase64").getAsString();
				byte[] image = Base64.getMimeDecoder().decode(imageBase64);
				int count = 0;
				if (action.equals("itemInsert")) {
					count = itemDao.insert(item, image);
				} else if (action.equals("itemUpdate")) {
					count = itemDao.update(item, image);
				}
				writeText(response, String.valueOf(count));
			} else if (action.equals("itemDelete")) {
				String itemJson = jsonObject.get("item").getAsString();
				item_bean item = gson.fromJson(itemJson, item_bean.class);
				int count = itemDao.delete(item.getItemID());
				writeText(response, String.valueOf(count));
			} else if (action.equals("findById")) {
				int id = jsonObject.get("id").getAsInt();
				item_bean item = itemDao.findById(id);
				writeText(response, gson.toJson(item));
			} else {
				writeText(response, "");
			}
		} catch (NamingException e) {
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
