<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>C.G. LOHAS</title>
<meta http-equiv="refresh"
	content="2; url=<c:url value='/_04_listItems/DisplayPageProducts'/>">
	<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>
<body>

	<c:set var="funcName" value="END" scope="session" />
	<jsp:include page="/fragment/header.jsp" />
	<div class="sm-parallax-window1">
		<div class="container">
			<div class="row">
				<CENTER>
					<h1 style="color:#fff;  margin-top:100px; margin-bottom:350px; ">
						<div style="font-family:微軟正黑體; text-shadow:1px 1px 8px #000;"> Dear ${LoginOK.name}：</div><BR>
						<div><FONT COLOR='RED'>C.G LOHAS</FONT></div><BR> 
						<div style="font-family:微軟正黑體; text-shadow:1px 1px 8px #000;"> Thank you for your order </div><BR>
						<div style="font-family:微軟正黑體; text-shadow:1px 1px 8px #000;"> We look forward to seeing you again </div><BR>
						
						
					</h1>
					

				</CENTER>
			</div>
		</div>
		<jsp:include page="/fragment/newfooter.jsp" />
	</div>
	<script>
		$('.sm-parallax-window1')
				.parallax(
						{
							imageSrc : '${pageContext.request.contextPath}/image/Cool-Green-Natural-Desktop-HD-Wallpaper.jpg'
						});
	</script>
</body>
</html>