<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IP Blocking</title>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>
<body>
	<jsp:include page="/fragment/header.jsp" />
	<section class="software sm-parallax-window" data-parallax="scroll"
		style="margin:0 0; padding:0 0;">

	<div style="margin-bottom: 200px; font-family: 微軟正黑體;">
		<div class="container">
			<div class="row login-from">
				<h1>
					<strong>IP address Blocking</strong>
				</h1>
				<h3>
					Current IP address: ${param.ip} has been blocked. The reason is as follows： <br> <strong>Insert the wrong password
					 or provide wrong detail when inquiring account/password, over three times.</strong><br><br> 
					 You currently not able to Login/Register, remaining <strong
						style="color: red;">${param.timeHour} hours ${param.timeMin} minutes to unblock your IP address</strong><br>
					We care your account securely. Please use the correct detail to login or inquire<br>
					CGLOHAS cares our customer's safety and privacy and we committed to protecting member's personal information <br>
					Please contact us if you have any further questions.
				</h3>
			</div>
		</div>
	</div>
	</section>


	<jsp:include page="/fragment/newfooter.jsp" />
</body>
</html>