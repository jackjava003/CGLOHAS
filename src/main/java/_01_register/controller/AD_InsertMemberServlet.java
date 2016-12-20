package _01_register.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import _00_init.GlobalService;
import _00_init.RegularExpressionUtil;
import _01_register.model.register_DAO;
import _01_register.model.register_bean;
import _02_login.model.LoginServiceDB;

@WebServlet("/AD_InsertMemberServlet")
public class AD_InsertMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println(request.getRemoteAddr());
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		System.out.println(jsonObject.get("NewMember").getAsString());
		register_bean NewMember = gson.fromJson(jsonObject.get("NewMember").getAsString(), register_bean.class);
		String imageBase64 = jsonObject.get("image").getAsString();
		// java.util.Base64 (Java 8 supports)
		byte[] image = null;
		if(imageBase64!=null && imageBase64.length()!=0 &&(imageBase64.equals("null")!=true)){
			image = Base64.getMimeDecoder().decode(imageBase64);
		}
		 InputStream is = null;
		 long sizeInBytes = 0;
		if(image!=null && image.length!=0){
			is  = new ByteArrayInputStream(image);
			sizeInBytes =is.available();
		}else{
			is = getServletContext().getResourceAsStream("/image/default.png");
			sizeInBytes = is.available();
		}
		String account = NewMember.getAccount();
		String password = NewMember.getPassword();
		String sex = NewMember.getSex();
		String name = NewMember.getName();
		String email = NewMember.getEmail();
		String cellphone = NewMember.getCellphone();
		boolean checkErr = true;
		ServletContext sc = getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);
		register_DAO rs = (register_DAO) context.getBean("Register_DAO");

		if (account == null || account.trim().length() == 0) {
			checkErr = false;
		} else if (RegularExpressionUtil.isAcValid(account) == false) {
			checkErr = false;
		} else if (rs.accountExists(account)) {
			checkErr = false;
		}

		if (password == null || password.trim().length() == 0) {
			checkErr = false;
		} else if (RegularExpressionUtil.isPwValid(password) == false) {
			checkErr = false;
		}
		if (sex == null || sex.trim().length() == 0) {
			checkErr = false;
		}

		if (name == null || name.trim().length() == 0) {
			checkErr = false;
		} else if (name.length() > 8) {
			checkErr = false;
		}

		if (email == null || email.trim().length() == 0) {
			checkErr = false;
		} else if (RegularExpressionUtil.isEmailValid(email) == false) {
			checkErr = false;
		} else if (rs.emailExists(email)) {
			checkErr = false;
		}

		if (cellphone == null || cellphone.trim().length() == 0) {
			checkErr = false;
		} else if (RegularExpressionUtil.isPhoneValid(cellphone) == false) {
			checkErr = false;
		} else if (rs.phoneExists(cellphone)) {
			checkErr = false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date2 = null;
		java.sql.Date sqdate = null;

		try {
			sdf.setLenient(false);
			date2 = sdf.parse(NewMember.getbirthdate1());
			sqdate = new java.sql.Date(date2.getTime());
		} catch (ParseException e) {
			checkErr = false;
		}
		int n =0;
		register_bean mb = null;
		if(checkErr == false){
			writeText(response, gson.toJson("NG"));
		}else{
			String GSpassword = null;
			String GSpassword1 = null;
			GSpassword = GlobalService.encryptString(password);
			GSpassword1 = GlobalService.getMD5Endocing(GSpassword);
			name = RegularExpressionUtil.removeTag(name).trim().replaceAll("\\s", "&nbsp;");
			
			register_bean rb = (register_bean) context.getBean("Register");
			rb.setAccount(account);
			rb.setPassword(GSpassword1);
			rb.setName(name);
			rb.setEmail(email);
			rb.setCellphone(cellphone);
			rb.setSex(sex);
			rb.setBirthDate(sqdate);
			
			try {
				n = rs.saveMember(rb, is, sizeInBytes);
				LoginServiceDB lsdb = (LoginServiceDB) context.getBean("LoginServiceDB");
				lsdb.populateMemberList();
				mb = lsdb.checkAccountPasswordWithoutEncode(account, GSpassword1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(n==1){
			writeText(response, gson.toJson(mb.getUserID()));
		}else{
			writeText(response, gson.toJson("NG"));
		}
		

		// register_DAO rs = new register_DAO();
		// if (rs.accountExists(account)) {
		// writeText(response, gson.toJson("NG"));
		// } else {
		// writeText(response, gson.toJson("OK"));
		// }
	}

	private void writeText(HttpServletResponse response, String outText) throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		// System.out.println("outText: " + outText);
		out.print(outText);
		System.out.println(outText);
	}

}
