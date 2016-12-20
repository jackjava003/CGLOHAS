package _00_init;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;

public class SendMailThread extends Thread {
	private String m_account;
	private String username;
	private String password;
	private String newPw ="";
	private String from ="";
	private String account ="";
	
	public SendMailThread(String email, String account, String password) {
		this.m_account = email;
		this.username = account;
		this.password = password;
	}

	public SendMailThread(String email, String newPw, String from, String account) {
		this.m_account = email;
		this.newPw = newPw;
		this.from=from;
		this.account=account;
	}

	public void run() {
		String from = "java00303@gmail.com";
		List<String> to = Arrays.asList(new String[] { m_account });
		List<String> cc = Arrays.asList(new String[] {});
		List<String> bcc = Arrays.asList(new String[] {});
		String subject = "";
		String text = "";
		if(this.from.trim().length()==0 && newPw.trim().length()==0 ){
			subject = "Welcome to join C.G.LOHAS";
			text ="<link href=\"https://fonts.googleapis.com/css?family=Pacifico\" rel=\"stylesheet\">"+
		    "<div style=\"font-family:微軟正黑體; background-image: url('http://i2.mirror.co.uk/incoming/article4542688.ece/ALTERNATES/s615/BarbadosQuantum.jpg'); height:auto; width:900px; background-position: cover; background-repeat: no-repeat;\">"+
	        "<h1 style=\"text-align: right; text-shadow:1px 1px 8px #000; color:#fff; padding-top:20px; padding-right: 325px;\">Thank you for joining the membership</h1>"+
	        "<h2 style=\"text-align: right; text-shadow:1px 1px 8px #000; color:#fff; padding-right: 325px;\"></h2>"+
	        "<div style=\"background-size:cover; background-image: url('http://fullhdpictures.com/wp-content/uploads/2016/08/Excellent-Blue-Sky-Wallpaper.jpg'); height:409px; width:615px; position: relative; margin: auto;\">"+
	            "<h1 style=\"text-shadow:1px 1px 8px #000; color:#fff; text-align: right; padding-right:40px; padding-top: 40px; padding-left: 40px; padding-right: 40px;\"><div style=\"font-family: Pacifico; padding-bottom: 15px;\" >C.G. LOHAS</div> </h1>" +
	            "<div style=\"background-image: url('http://wallpaperlayer.com/img/2015/10/good-morning-sunshine-wallpaper-7478-7767-hd-wallpapers.jpg'); height:409px; width:615px; position: relative; margin-left:150px;\">" +
	              "<h1 style=\"text-align: right; text-shadow:1px 1px 8px #000; color:#fff; padding-top:20px; padding-right: 40px; color:#fff;\">" + 
	             "<a style=\"color:#fff;\" href='http://localhost:8080/CGLOHAS/VerifiedMail?user=" + username + "&verifiedCode=" + password
					+ "'>Click me to verify your account </a></h1>" +
	                "<h2 style=\"text-align: right; text-shadow:1px 1px 8px #000; color:#fff;  padding-right: 40px; color:#fff;\">Best Regards</h2>" +
	                "<h2 style=\"text-align: right; text-shadow:1px 1px 8px #000; color:#fff;  padding-right: 40px; color:#fff;\">C.G.LOHAS TEAM</h2>" +
	            "</div>"+
	        "</div>"+
	    "</div>";
		}else if(this.from.trim().length()!=0 && this.from.equalsIgnoreCase("Pw")){
			subject = " C.G.LOHAS  Your query password";
			text =  "<link href=\"https://fonts.googleapis.com/css?family=Pacifico\" rel=\"stylesheet\">"+
				    "<div style=\"font-family:微軟正黑體; background-image: url('http://i2.mirror.co.uk/incoming/article4542688.ece/ALTERNATES/s615/BarbadosQuantum.jpg'); height:auto; width:900px; background-position: cover; background-repeat: no-repeat;\">"+
			        "<h1 style=\"text-align: right; text-shadow:1px 1px 8px #000; color:#fff; padding-top:20px; padding-right: 325px;\">Your new password:</h1>"+
			        "<h2 style=\"text-align: right; text-shadow:1px 1px 8px #000; color:#fff; padding-right: 325px;\">"+newPw+"</h2>"+
			        "<div style=\" background-size:cover; background-image: url('http://fullhdpictures.com/wp-content/uploads/2016/08/Excellent-Blue-Sky-Wallpaper.jpg'); height:409px; width:615px; position: relative; margin: auto;\">"+
			            "<h1 style=\"text-shadow:1px 1px 8px #000; color:#fff; text-align: right; padding-top: 40px; padding-right: 40px;\"><div style=\"font-family: Pacifico; padding-bottom: 15px;\" >C.G. LOHAS</div> </h1>" +
			            "<div style=\"background-image: url('http://wallpaperlayer.com/img/2015/10/good-morning-sunshine-wallpaper-7478-7767-hd-wallpapers.jpg'); height:409px; width:615px; position: relative; margin-left:150px;\">" +
			              "<h1 style=\"text-align: right; text-shadow:1px 1px 8px #000; color:#fff; padding-top:20px; padding-right: 40px; color:#fff;\">" + 
			             "<a style=\"color:#fff;\" href='http://localhost:8080/CGLOHAS/_02_login/login.jsp'>Click me to Login</a></h1>" +
			                "<h2 style=\"text-align: right; text-shadow:1px 1px 8px #000; color:#fff;  padding-right: 40px; color:#fff;\">Best Regards</h2>" +
			                "<h2 style=\"text-align: right; text-shadow:1px 1px 8px #000; color:#fff;  padding-right: 40px; color:#fff;\">C.G.LOHAS TEAM</h2>" +
			            "</div>"+
			        "</div>"+
			    "</div>";
					
					
					
					
					
//					"<h1>您的新密碼為:</h1>" + "<h2>"+newPw+"</h2>"
//				+ "<a href='http://192.168.11.11:8080/CGLOHAS/_02_login/login.jsp" 
//				+ "'>登入</a><br>" + "<br><br><font color='blue'> 再次感謝, </font><br>工作小組敬上";
		}else{
			subject = " C.G.LOHAS  Your query account";
			text =   "<link href=\"https://fonts.googleapis.com/css?family=Pacifico\" rel=\"stylesheet\">"+
				    "<div style=\"font-family:微軟正黑體; background-image: url('http://i2.mirror.co.uk/incoming/article4542688.ece/ALTERNATES/s615/BarbadosQuantum.jpg'); height:auto; width:900px; background-position: cover; background-repeat: no-repeat;\">"+
			        "<h1 style=\"text-align: right; text-shadow:1px 1px 8px #000; color:#fff; padding-top:20px; padding-right: 325px;\">Your Account:</h1>"+
			        "<h2 style=\"text-align: right; text-shadow:1px 1px 8px #000; color:#fff; padding-right: 325px;\">"+this.account+"</h2>"+
			        "<div style=\"background-size:cover; background-image: url('http://fullhdpictures.com/wp-content/uploads/2016/08/Excellent-Blue-Sky-Wallpaper.jpg'); height:409px; width:615px; position: relative; margin: auto;\">"+
			            "<h1 style=\"text-shadow:1px 1px 8px #000; color:#fff; text-align: right; padding-top: 40px; padding-left: 40px; padding-right: 40px;\"><div style=\"font-family: Pacifico; padding-bottom: 15px;\" >C.G. LOHAS</div> </h1>" +
			            "<div style=\"background-image: url('http://wallpaperlayer.com/img/2015/10/good-morning-sunshine-wallpaper-7478-7767-hd-wallpapers.jpg'); height:409px; width:615px; position: relative; margin-left:150px;\">" +
			              "<h1 style=\"text-align: right; text-shadow:1px 1px 8px #000; color:#fff; padding-top:20px; padding-right: 40px; color:#fff;\">" + 
			             "<a style=\"color:#fff;\" href='http://localhost:8080/CGLOHAS/_02_login/login.jsp'>Click me to Login</a></h1>" +
			                "<h2 style=\"text-align: right; text-shadow:1px 1px 8px #000; color:#fff;  padding-right: 40px; color:#fff;\">Best Regards</h2>" +
			                "<h2 style=\"text-align: right; text-shadow:1px 1px 8px #000; color:#fff;  padding-right: 40px; color:#fff;\">C.G.LOHAS TEAM</h2>" +
			            "</div>"+
			        "</div>"+
			    "</div>";
					
					
					
					
					
//					"<h1>您的帳號為:</h1>" + "<h2>"+this.account+"</h2>"
//				+ "<a href='http://192.168.11.11:8080/CGLOHAS/_02_login/login.jsp" 
//				+ "'>登入</a><br>" + "<br><br><font color='blue'> 再次感謝, </font><br>工作小組敬上";
		}
		List<String> attachment = Arrays.asList(new String[] {});
		JavaMailUtil util = new JavaMailUtil(from, to, cc, bcc, subject, text, attachment);
		try {
			if (util.send()) {
				System.out.println("發信成功");
			} else {
				System.out.println("發信失敗");
			}
		} catch (IOException | MessagingException e) {
			System.out.println("發信失敗");
			e.printStackTrace();
		}
	}

}
