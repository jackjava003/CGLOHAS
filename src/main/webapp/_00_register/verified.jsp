<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:if test="${empty param.verifiedResultNO}">
<meta http-equiv="refresh" content="2; url=../index.jsp">
</c:if>
<title>C.G.LOHAS</title>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>
<body>
<jsp:include page="/fragment/header.jsp" />
<section class="sm-parallax-window" style="margin:0 0; padding:0 0;">
<h1 style="color:#fff; text-align:center;font-family:微軟正黑體;  margin-bottom:200px; padding-top:150px;"><strong>${param.verifiedResultOK}</strong></h1><br>
<h1 style="color:#fff; text-align:center;font-family:微軟正黑體;  margin-bottom:200px; padding-top:150px;"><strong>${param.verifiedResultNO}</strong></h1><br>


<jsp:include page="/fragment/newfooter.jsp" />
</section>
	<script>
		$('.sm-parallax-window')
				.parallax(
						{
							imageSrc : '${pageContext.request.contextPath}/image/blue-sky.jpg'
						});
	</script>
</body>
</html>