package _07_storeInfo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import _07_storeInfo.model.MainStoreAccessBean;
import _07_storeInfo.model.StoreAccessDAO;
import _07_storeInfo.model.StoreAccessDetailBean;

@WebServlet("/ShopServlet")
public class AD_ShopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StoreAccessDAO shopDAO;
		try {
			shopDAO = new StoreAccessDAO();
			List<StoreAccessDetailBean> shops = shopDAO.getAll();
			writeText(response, new Gson().toJson(shops));
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
		StoreAccessDAO shopDao;
		try {
			shopDao = new StoreAccessDAO();

			String action = jsonObject.get("action").getAsString();
			System.out.println("action: " + action);

			if (action.equals("getAll")) {
				List<StoreAccessDetailBean> shops = shopDao.getAll();
				writeText(response, gson.toJson(shops));
			} else if(action.equals("getMainShopAll")){
				List<MainStoreAccessBean> shops = shopDao.getSelectMainStoreALL();
				writeText(response, gson.toJson(shops));
			} else if(action.equals("getSpecificStores")){
				int storeid = jsonObject.get("storeid").getAsInt();
				List<StoreAccessDetailBean> shops = shopDao.getSelectLocDetailAD(storeid);
				writeText(response, gson.toJson(shops));
			} else if (action.equals("getImage")) {
				OutputStream os = response.getOutputStream();
				int id = jsonObject.get("id").getAsInt();
				int imageSize = jsonObject.get("imageSize").getAsInt();
				int storeid = jsonObject.get("storeid").getAsInt();
				byte[] image = shopDao.getImage(id, storeid);
				if (image != null) {
					 image = ImageUtil.shrink(image, imageSize);
					 response.setContentType("image/jpeg");
					 response.setContentLength(image.length);
				}
				os.write(image);
			} else if (action.equals("getMainStoreImage")) {
				OutputStream os = response.getOutputStream();
				int id = jsonObject.get("id").getAsInt();
				int imageSize = jsonObject.get("imageSize").getAsInt();
				byte[] image = shopDao.getMainStoreImage(id);
				if (image != null) {
					// image = ImageUtil.shrink(image, imageSize);
					// response.setContentType("image/jpeg");
					// response.setContentLength(image.length);
				}
				os.write(image);	
			} else if (action.equals("shopInsert") || action.equals("shopUpdate")) {
				String shopJson = jsonObject.get("shop").getAsString();
				StoreAccessDetailBean shop = gson.fromJson(shopJson, StoreAccessDetailBean.class);
				String imageBase64 = jsonObject.get("imageBase64").getAsString();
				byte[] image = Base64.getMimeDecoder().decode(imageBase64);
				int count = 0;
				if (action.equals("shopInsert")) {
					count = shopDao.insert(shop, image);
				} else if (action.equals("shopUpdate")) {
					count = shopDao.update(shop, image);
				}
				writeText(response, String.valueOf(count));
			} else if (action.equals("shopDelete")) {
				String spotJson = jsonObject.get("shop").getAsString();
				StoreAccessDetailBean shop = gson.fromJson(spotJson, StoreAccessDetailBean.class);
				int count = shopDao.delete(shop.getLocationid());
				writeText(response, String.valueOf(count));
			} else if (action.equals("findById")) {
				int id = jsonObject.get("id").getAsInt();
				StoreAccessDetailBean shop = shopDao.findById(id);
				writeText(response, gson.toJson(shop));
			} else {
				writeText(response, "");
			}
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
