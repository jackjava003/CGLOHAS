package _00_init;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

//尚未修改完成
//本類別會依據傳入的ID 以及Type(類型)  進入不同TABLE 抓取圖片
//然後傳回給提出請求的瀏覽器
@WebServlet("/_00_init/getImage")
public class GetImageFromDB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		byte[] bt;
		// 讀取瀏覽器傳送來的userID
		String id = request.getParameter("id");
		// 分辨讀取哪個表格的圖片欄位
		String type = request.getParameter("type");
		String recipeID = request.getParameter("recipeID");
		
		 //System.out.println("GetImageFromDB, Type==>" + type);
		// System.out.println("GetImageFromDB, ID==>" + id);
		// 取得能夠執行JNDI的Context物件
		try {

			Context context = new InitialContext();
			// =======================================
			// 透過JNDI取得DataSource物件
			DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/cglohas"); // 需更改******************
			try (Connection conn = ds.getConnection();) {
				PreparedStatement pstmt = null;
				if (type.equalsIgnoreCase("userImg")) { // 讀取user_photo表格
					pstmt = conn.prepareStatement("SELECT userID, userphoto from register_user where userID = ?");
				}
				// ========================================
				else if (type.equalsIgnoreCase("ITEM")) { // 讀取ITEM表格
					pstmt = conn.prepareStatement("SELECT itemID, image from item where itemID = ?");
				}else if (type.equalsIgnoreCase("Recipes")) { // 讀取ITEM表格
					pstmt = conn.prepareStatement("SELECT recipesID, image from recipes where recipesID = ?");
				}else if (type.equalsIgnoreCase("PROCESS")){
					pstmt = conn.prepareStatement("SELECT PROCESSID, image from PROCESS where PROCESSID = ? and recipeID =" + recipeID);
				}else if (type.equalsIgnoreCase("storeImg"))
				{
					pstmt = conn.prepareStatement("SELECT STOREID, image from store_image where storeid = ?" );
				}
				else if (type.equalsIgnoreCase("locationImg"))
				{
					String locationID = request.getParameter("lid");
					pstmt = conn.prepareStatement("SELECT locationid, image from location where storeid = ? and locationid =" + locationID);
				}else if(type.equalsIgnoreCase("holderEnvPic")){
					pstmt = conn.prepareStatement("SELECT image_id, image from holder_image where image_id = ?");
				}else if(type.equalsIgnoreCase("holderFoodPic")){
					pstmt = conn.prepareStatement("SELECT image_id, image from holder_image where image_id = ?");
				}
				// ========================================
				
				pstmt.setString(1, id);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					// ==============================
					//String fileName = rs.getString(1);
					bt = rs.getBytes(2);
					//String mimeType = getServletContext().getMimeType(fileName);
					// 設定輸出資料的型態
					//response.setContentType(mimeType);
					// ==============================

					// 取得能寫出非文字資料的OutputStream物件
					try (OutputStream os = response.getOutputStream();) {
						os.write(bt);
					}

				}
			}

		} catch (NamingException e) {
			throw new ServletException(e);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}