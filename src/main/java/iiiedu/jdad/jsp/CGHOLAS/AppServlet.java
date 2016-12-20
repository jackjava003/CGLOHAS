package iiiedu.jdad.jsp.CGHOLAS;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AppServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	resp.setContentType("text/html; charset=UTF-8");   
    PrintWriter out = resp.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html><head><meta charset='UTF-8'>");
    out.println("<title>Jump for Joy</title></head>");
    out.println("<body><h1>專案建立成功</h1>");
    out.println("<h2>這是由Servlet送出的回應訊息</h2></body></html>");
    out.close();    
  }
}
