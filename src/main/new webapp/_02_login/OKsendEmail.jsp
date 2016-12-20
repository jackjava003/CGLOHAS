<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>C.G.LOHAS</title>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>
<body>
	<jsp:include page="/fragment/header.jsp" />
	<section class="software sm-parallax-window" data-parallax="scroll">
	<div style="font-family: '微軟正黑體'; margin-bottom: 300px;">

		<div class="container">
			<div class="row">
				<div class="col-md-12 sm-text">
					<h1>查詢會員資訊</h1>
				</div>
				<table class="table table-hover col-md-12 sm-text">
					<tr>
						<td>
							<h3>
								<c:if test="${param.from eq 'pw' }">Your new password has been sent to your mailbox.</c:if>
								<c:if test="${param.from eq 'ac' }">Your account has been sent to your mailbox.</c:if>
							</h3>
						</td>
					</tr>
					<tr>
						<td><h3><a href="<c:url value='/index.jsp'/>">Home Page</a></h3></td>
					</tr>
				</table>
			</div>
		</div>
	</div>

	<jsp:include page="/fragment/newfooter.jsp" /> </section>

</body>
</html>