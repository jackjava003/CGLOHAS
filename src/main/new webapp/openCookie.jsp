<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>C.G.LOHAS</title>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>
<body>
	<jsp:include page="fragment/header.jsp" />
	<div class="sm-parallax-window1" style="background-color:rgba(0,0,0,0.4);">
	<h1 style="font-size:80px; color:red; text-align:center; font-family:微軟正黑體;">Please Open Browser's Cookie</h1>		
	<h1 style="margin-top:500px;font-size:40px; color:#fff; text-align:right; font-family:微軟正黑體;">By C.G. LOHAS</h1>
	
	<jsp:include page="fragment/newfooter.jsp" />
	</div>
	<script>
		$('.sm-parallax-window1')
				.parallax(
						{
							imageSrc : '${pageContext.request.contextPath}/image/41333713.jpg'
						});
	</script>
</body>

</html>