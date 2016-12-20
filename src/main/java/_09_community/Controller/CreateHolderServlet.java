package _09_community.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;

import _00_init.GlobalService;
import _00_init.RegularExpressionUtil;
import _00_init.VerifyUtils;
import _01_register.model.register_bean;
import _09_community.model.HolderBean;
import _09_community.model.HolderImageBean;
import _09_community.model.HolderManagementDAO;

@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024
		* 1024 * 5 * 5)

@WebServlet("/_09_community/CreateHolderServlet.do")
public class CreateHolderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		if (session == null) { // 使用逾時
			request.setAttribute("Errors", "Connection expires, Please re-login");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}
		register_bean rb = (register_bean) session.getAttribute("LoginOK");
		if (rb == null) {
			request.setAttribute("Errors", "Please re-login");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}
		int userID = rb.getUserID();
		Map<String, String> errorMsg = new HashMap<String, String>();
		request.setAttribute("MsgMap", errorMsg); // 顯示錯誤訊息

		String zipCode = request.getParameter("zipcode");
		String city = request.getParameter("zone1");
		String area = request.getParameter("zone2");
		String address = request.getParameter("address");
		String exp = request.getParameter("exp");
		String[] proType = request.getParameterValues("proType");
		// if 其他 須獲得text
		String pro_other = request.getParameter("pro_other");
		String foodIntro = request.getParameter("foodIntro");
		String[] weekDay = request.getParameterValues("weekday");
		String[] times = request.getParameterValues("times");
		String pplNums = request.getParameter("pplNums");
		String vegan = request.getParameter("vegan");
		String price = request.getParameter("price");
		String status = request.getParameter("status");

		Part envPicture = request.getPart("environment");
		InputStream isEnv = envPicture.getInputStream();
		long sizeEnv = isEnv.available();
		String environmentTitle = request.getParameter("environmentTitle");

		Part foodPicture = request.getPart("food");
		InputStream isFood = foodPicture.getInputStream();
		long sizeFood = isFood.available();
		String foodTitle = request.getParameter("foodTitle");

		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// baos.write(is.read());
		// byte[] bt = baos.toByteArray();

		String selfIntro = request.getParameter("selfIntro");
		// 檢查使用者輸入資料
		if (zipCode.trim().length() == 0 || city.trim().length() == 0 || area.trim().length() == 0) {
			errorMsg.put("errorArea", " 【Please select city】");
		}
		if (address == null || address.trim().length() == 0) {
			errorMsg.put("errorAddress", " 【Please enter address】");
		}
//		else if (address.indexOf("號") == -1 && address.indexOf("号") == -1) {
//			errorMsg.put("errorAddress", " 【請確認出貨地址】");
//		}
		
		if (exp == null) {
			errorMsg.put("errorExp", " 【Please select Cook experience】");
		}
		if (proType == null) {
			errorMsg.put("errorProType", " 【Please select the box】");
		}
		if (proType != null && proType[proType.length - 1].equals("其他")) {
			if (pro_other == null || pro_other.trim().length() == 0) {
				errorMsg.put("errorPro_other", " 【Please enter info】");
			}
		} else if ((pro_other != null && pro_other.trim().length() != 0) && !proType[proType.length - 1].equals("其他")) {
			errorMsg.put("errorProType", " 【Please select 'Other' or delete input field data】");
		}else if (pro_other != null && pro_other.trim().length() > 20){
			errorMsg.put("errorProType", " 【Can not exceed 20 characters】");
		}

		if (foodIntro == null || foodIntro.trim().length() == 0) {
			errorMsg.put("errorFoodIntro", " 【Please enter Features of the cuisine intro】");
		}else if(foodIntro.length()>100){
			errorMsg.put("errorFoodIntro", " 【Can not exceed 100 characters】");
		}
		if (weekDay == null) {
			errorMsg.put("errorWeekday", " 【Please select Available day】");
		}
		if (times == null) {
			errorMsg.put("errorTimes", " 【Please select Available time】");
		}
		if (pplNums == null) {
			errorMsg.put("errorPplNums", " 【Please select Maximum customers】");
		}
		if (vegan == null) {
			errorMsg.put("errorVegan", " 【Please select the box】");
		}
		if (price == null) {
			errorMsg.put("errorPrice", " 【Please select price】");
		}
		if (status == null) {
			errorMsg.put("errorStatus", " 【Please select Activity Status】");
		}
		// 環境圖片
		if (sizeEnv == 0) {
			errorMsg.put("errorEnvironmentSize", " 【Please confirm the photo】");
		}else{
			String fileName = GlobalService.getFileName(envPicture); // 此為圖片檔的檔名
			if (VerifyUtils.verifyPicFile(fileName)== false) {
				errorMsg.put("errorPicSize", " 【Please confirm file type】");
			}
		}

		if (environmentTitle == null || environmentTitle.trim().length() == 0) {
			errorMsg.put("errorEnvironmentTitle", " 【Please enter Title】");
		}else if (environmentTitle.trim().length() > 10){
			errorMsg.put("errorEnvironmentTitle", " 【Can not exceed 10 characters】");
		}

		// 菜色圖片

		if (sizeFood == 0) {
			errorMsg.put("errorFoodSize", " 【Please confirm the photo】");
		}else{
			String fileName = GlobalService.getFileName(foodPicture); // 此為圖片檔的檔名
			if (VerifyUtils.verifyPicFile(fileName)== false) {
				errorMsg.put("errorFoodSize", " 【Please confirm file type】");
			}
		}

		if (foodTitle == null || foodTitle.trim().length() == 0) {
			errorMsg.put("errorFoodTitle", " 【Please enter Title】");
		}else if (foodTitle.trim().length() > 10){
			errorMsg.put("errorFoodTitle", " 【Can not exceed 10 characters】");
		}

		if (selfIntro == null || selfIntro.trim().length() == 0) {
			errorMsg.put("errorSelfIntro", " 【Please enter Self intro】");
		}else if (selfIntro.trim().length() > 100){
			errorMsg.put("errorSelfIntro", " 【Can not exceed 100 characters】");
		}

		if (!errorMsg.isEmpty()) {
			RequestDispatcher rd = request.getRequestDispatcher("/_09_community/Holder.jsp");
			rd.forward(request, response);
			return;
		}
		
//		String addCheck = zipCode+city + area + address;
//		if(VerifyUtils.verifyAdd(addCheck)==null || !VerifyUtils.verifyAdd(addCheck).equalsIgnoreCase("OK")){
//			errorMsg.put("errorAddress", " 【請重新確認地址】");
//		}
		if (!errorMsg.isEmpty()) {
			RequestDispatcher rd = request.getRequestDispatcher("/_09_community/Holder.jsp");
			rd.forward(request, response);
			return;
		}

		// 型態轉換
		int code = Integer.parseInt(zipCode);
		address = RegularExpressionUtil.removeTag(address).replaceAll("\r\n", "<br>").replaceAll("\\s", "&nbsp;");
		String proTypeStr = "";
		for (String str : proType) {
			proTypeStr = proTypeStr + str + "|";
		}
		pro_other = RegularExpressionUtil.removeTag(pro_other).trim().replaceAll("\r\n", "<br>").replaceAll("\\s", "&nbsp;");
		foodIntro = RegularExpressionUtil.removeTag(foodIntro).replaceAll("\r\n", "<br>").replaceAll("\\s", "&nbsp;");

		String weekDayStr = "";
		for (String str : weekDay) {
			weekDayStr = weekDayStr + str + "|";
		}

		String timesStr = "";
		for (String str : times) {
			timesStr = timesStr + str + "|";
		}

		Blob evnBlob = null;
		try {
			byte[] evnByte = new byte[(int) sizeEnv];
			isEnv.read(evnByte);
			evnBlob = new SerialBlob(evnByte);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		environmentTitle = RegularExpressionUtil.removeTag(environmentTitle).trim().replaceAll("\r\n", "<br>").replaceAll("\\s", "&nbsp;");

		Blob foodBlob = null;
		try {
			byte[] foodByte = new byte[(int) sizeFood];
			isFood.read(foodByte);
			foodBlob = new SerialBlob(foodByte);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		foodTitle = RegularExpressionUtil.removeTag(foodTitle).trim().replaceAll("\r\n", "<br>").replaceAll("\\s", "&nbsp;");
		selfIntro = RegularExpressionUtil.removeTag(selfIntro).replaceAll("\r\n", "<br>").replaceAll("\\s", "&nbsp;");

		int count = 0;

		HolderManagementDAO hmd = new HolderManagementDAO();
		HolderBean hb = new HolderBean(userID, code, city, area, address, exp, proTypeStr, pro_other, foodIntro,
				weekDayStr, timesStr, pplNums, vegan, price, status, selfIntro);
		HolderImageBean env = new HolderImageBean(userID, "ENV", evnBlob, environmentTitle);
		HolderImageBean food = new HolderImageBean(userID, "FOOD", foodBlob, foodTitle);
		count += hmd.insert(hb);
		count += hmd.insertImage(env);
		count += hmd.insertImage(food);

		if (count == 3) {
			response.sendRedirect(response.encodeRedirectURL(
					request.getContextPath() + "/_09_community/HolderDetail.jsp?result=OK&holderID=" + userID));
			return;
		} else {
			request.setAttribute("Errors", "Update failed");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}

	}

}
