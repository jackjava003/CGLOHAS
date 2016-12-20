<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error</title>
<style type="text/css">
#main {
	position: absolute;
	top: 90px;
	left: 180px;
}
</style>


<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>
<body>
	<c:set var="funcName" value="CHE" scope="session" />
	<jsp:include page="/fragment/header.jsp" />
	<P>
		<section>
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 style="text-align:center;">
						<strong>Error：${Errors}</strong>
					</h1>
					<Table class="table" width='580'
						style="background: #E5FFCD; border-style: double; border-color: #EF02A4;">
						<TR height='250'>
							<TD width="580" align="CENTER"><A
								href="../_02_login/login.jsp"><h3><strong>Re-Login</strong></h3></A></TD>
						</TR>
					</Table>

				</div>
			</div>

		</div>

		</section>
		<jsp:include page="/fragment/newfooter.jsp" />
</body>
</html>