<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:if test="${empty param.logout}">
	<meta http-equiv="refresh" content="2; url=ShowMember.jsp">
</c:if>
<title>C.G.LOHAS</title>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>
<body>
	<jsp:include page="/fragment/header.jsp" />
	<section class="software sm-parallax-window" data-parallax="scroll" style="margin:0 0; padding:0 0;">

	<div style="font-family: '微軟正黑體'; margin-bottom: 300px;">

		<div class="container">
			<div class="row" style="text-shadow:1px 1px 10px #000; color:#fff;">
				<div class="col-md-12 sm-text">
					<h1><strong>Update Membership</strong></h1>
				</div>
				<table class="table table-hover col-md-12 sm-text">
					<tr>
						<td>
							<h4 style="font-size: 30px;">
								<c:if test="${param.type == 'reverified'}">
	${param.message}
	</c:if>
								<br>
								<c:if test="${empty param.logout and empty param.type}">
	${param.message}
<strong>Update successful! Redirect to Member Center in 2 seconds</strong>
									<br>
								</c:if>
							</h4>
						</td>
						<td>
							<h4 style="font-size: 30px;">
								<c:if test="${param.logout==true}">
	${param.message}
Update successful! Please re-login<br>
									<%
										session.invalidate();
									%>
									<a href="<c:url value='/_02_login/login.jsp' />"> Login </a>
								</c:if>
							</h4>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<jsp:include page="/fragment/newfooter.jsp" /> </section>

	<script>
		$('.sm-parallax-window')
				.parallax(
						{
							imageSrc : '${pageContext.request.contextPath}/image/blue-sky.jpg'
						});
	</script>
</body>
</html>