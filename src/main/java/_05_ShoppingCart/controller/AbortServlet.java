package _05_ShoppingCart.controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import _05_ShoppingCart.model.ShoppingCart;
// 當進行『結帳』時，如果按下『放棄購物』超連結，瀏覽器會要求此程式
@WebServlet("/_04_listItems/abort.do")
public class AbortServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart)session.getAttribute("ShoppingCart");
		if (cart != null) {
			//由session物件中移除ShoppingCart物件
			session.removeAttribute("ShoppingCart");
		}
		response.sendRedirect(response.encodeRedirectURL ("../index.jsp"));
		return;
	}
}
