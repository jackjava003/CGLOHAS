package _09_community.Controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import _00_init.HibernateUtil;
import _00_init.RegularExpressionUtil;
import _00_init.VerifyUtils;
import _01_register.model.Hibernate_register_DAO;
import _01_register.model.register_bean;
import _09_community.model.JoinBean;
import _09_community.model.JoinDAO;
import _11_notification.model.NotifyBean;
import _11_notification.model.NotifyDAO;

@WebServlet("/_09_community/JoinMessageServlet.do")
public class JoinMessageServlet extends HttpServlet {
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
		Map<String, String> errorMsg = new HashMap<String, String>();
		request.setAttribute("MsgMap", errorMsg); // 顯示錯誤訊息
		String update = request.getParameter("UpdateJoiner");  //檢查是第一次建立流言還是修改
		
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		String holderID = request.getParameter("holderID");
		int userID = rb.getUserID();
		String startDate = request.getParameter("startDate");
		String[] favourType = request.getParameterValues("favourType");
		// if 其他 須獲得text
		String favour_other = request.getParameter("favour_other");
		String[] weekDay = request.getParameterValues("weekday");
		String[] times = request.getParameterValues("times");
		String pplNums = request.getParameter("pplNums");
		String vegan = request.getParameter("vegan");
		String contact = request.getParameter("contact");
		String message = request.getParameter("message");

		if (gRecaptchaResponse == null || gRecaptchaResponse.trim().length() == 0) {
			errorMsg.put("errorRecaptcha", " 【Please verify】");
		} else {
			boolean valid = VerifyUtils.verify(gRecaptchaResponse);
			if (valid == false) {
				errorMsg.put("errorRecaptcha", " 【Verify failed, Please re-verify】");
			}
		}
		if (startDate == null || startDate.trim().length() == 0) {
			errorMsg.put("errorStartDate", " 【Please select Expected date】");
		}
		if (favourType == null) {
			errorMsg.put("errorFavourType", " 【Please select Favorite food】");
		}
		if (favourType != null && favourType[favourType.length - 1].equals("其他")) {
			if (favour_other == null || favour_other.trim().length() == 0) {
				errorMsg.put("errorFavour_other", " 【Please enter info】");
			}
		} else if ((favour_other != null && favour_other.trim().length() != 0)
				&& !favourType[favourType.length - 1].equals("其他")) {
			errorMsg.put("errorFavourType", " 【Please select 'Other' or delete input field data】");
		}else if (favour_other != null && favour_other.trim().length() > 20){
			errorMsg.put("errorProType", " 【Can not exceed 20 characters】");
		}

		if (weekDay == null) {
			errorMsg.put("errorWeekday", " 【Please select Expected day】");
		}
		if (times == null) {
			errorMsg.put("errorTimes", " 【Please select Expected time】");
		}
		if (pplNums == null) {
			errorMsg.put("errorPplNums", " 【Please select Number of people】");
		}
		if (vegan == null) {
			errorMsg.put("errorVegan", " 【Please select box】");
		}
		if (contact == null || contact.trim().length() == 0) {
			errorMsg.put("errorContact", " 【Please enter Contact detail】");
		}else if(RegularExpressionUtil.isPhoneValid(contact)==false && RegularExpressionUtil.isEmailValid(contact)==false){
			errorMsg.put("errorContact", " 【Please confirm Contact detail】");
		}
		if(message!=null && message.length()>100){
			errorMsg.put("errorMessage", " 【Can not exceed 100 characters】");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date2 = null;
		java.sql.Date sqdate = null;
		try {
			sdf.setLenient(false);
			date2 = sdf.parse(startDate);
			System.out.println(date2);
			sqdate = new java.sql.Date(date2.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			errorMsg.put("errorStartDate", "【Wrong format】");
		}

		if (!errorMsg.isEmpty()) {
			if(update==null||update.trim().length()==0){
				request.setAttribute("JoinMessage", 1);
				RequestDispatcher rd = request.getRequestDispatcher("/_09_community/HolderDetail.jsp?holderID=" + holderID);
				rd.forward(request, response);
				return;
			}else{
				RequestDispatcher rd = request.getRequestDispatcher("/_09_community/UpdateForJoiner.jsp?holderID=" + holderID + "&joinerID="+userID);
				rd.forward(request, response);
				return;
			}
			
		}

		int holderInt = Integer.parseInt(holderID);
		String favourTypeStr = "";
		for (String str : favourType) {
			favourTypeStr = favourTypeStr + str + "|";
		}
		favour_other = RegularExpressionUtil.removeTag(favour_other).trim().replaceAll("\r\n", "<br>").replaceAll("\\s", "&nbsp;");

		String weekDayStr = "";
		for (String str : weekDay) {
			weekDayStr = weekDayStr + str + "|";
		}

		String timesStr = "";
		for (String str : times) {
			timesStr = timesStr + str + "|";
		}

		contact = RegularExpressionUtil.removeTag(contact).trim().replaceAll("\r\n", "<br>").replaceAll("\\s", "&nbsp;");
		message = RegularExpressionUtil.removeTag(message).replaceAll("\r\n", "<br>").replaceAll("\\s", "&nbsp;");
		int count = 0;

		JoinDAO jdao = new JoinDAO();
		JoinBean jb = new JoinBean(holderInt, userID, sqdate, favourTypeStr, favour_other, weekDayStr, timesStr,
				pplNums, vegan, contact, message);
		count += jdao.insert(jb);
		Hibernate_register_DAO hrdao = new Hibernate_register_DAO();
		NotifyDAO ndao = new NotifyDAO();
		String userName = hrdao.getUserName(userID);
		NotifyBean nb = new NotifyBean(userID,holderInt,userName,1,0);
		count += ndao.insertNotify(nb);
		if (count == 2) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/_09_community/HolderDetail.jsp?result=OK&holderID="+holderInt+"&JoinMessage=1"));
			return;
		} else {
			request.setAttribute("Errors", "Update failed");
			RequestDispatcher rd = request.getRequestDispatcher("/_05_ShoppingCart/CheckError.jsp");
			rd.forward(request, response);
			return;
		}

	}

}
