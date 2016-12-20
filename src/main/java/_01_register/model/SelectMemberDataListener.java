package _01_register.model;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;

@WebListener
public class SelectMemberDataListener implements ServletContextListener {
	public void contextDestroyed(ServletContextEvent sce) {
	}
	public void contextInitialized(ServletContextEvent event) {
		// 由傳入的參數取出ServletContext物件
		ServletContext context = event.getServletContext();
		// 讀取系統啟始參數: DBString
		String dbString = context.getInitParameter("account");
		// 新建MemberDAO類別的物件: dao (Model元件)
		register_DAO dao = new register_DAO();
//		request.setCharacterEncoding("UTF-8");
//		HttpSession session = request.getSession();
//		String requestURI = (String) session.getAttribute("requestURI");
		// 請dao物件讀取資料庫內的Member資料
		Collection<register_bean> col = dao.getSelectALL();
		// 將傳回的Collection物件col放入context物件內
		context.setAttribute("contextMemberBean", col);
	}
}
