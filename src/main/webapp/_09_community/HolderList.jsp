<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>C.G.LOHAS</title>
</head>
<body>
	<jsp:include page="/fragment/header.jsp" />
	<jsp:useBean id="MangeHolder"
		class="_09_community.model.HolderManagementDAO" />
	<c:set var="totalPages" value="${MangeHolder.totalPages}" />
	<c:choose>
		<c:when test="${empty param.HolderPageNo}">
			<c:set var="HolderPageNo" value="1" />
		</c:when>
		<c:when test="${param.HolderPageNo > totalPages}">
			<c:set var="HolderPageNo" value="1" />
		</c:when>
		<c:otherwise>
			<c:set var="HolderPageNo" value="${param.HolderPageNo}" />
		</c:otherwise>
	</c:choose>

	<jsp:setProperty property="pageNo" name="MangeHolder"
		value="${HolderPageNo}" />



	<section class="sm-parallax-window" style="  background-color:rgba(0,0,0,0.4); padding:0 0; margin:0 0;">
	<div class="container">
		<div class="row">
			<div class="col-md-12" style="font-family: 微軟正黑體;">
				<center>
					<div style="height: 350px">
						<h1 style="font-family:微軟正黑體 ;color:#fff; text-shadow:1px 1px 8px #000;">
							<strong>HOST List</strong>
						</h1>
						<table class="table table-hover"
							style="background-color: rgba(255,255,255,0.9); box-shadow: 10px 10px 8px #000;">
							<tr height='50'>
								<td colspan="5" align="center"><h4>
										<strong>Total ${MangeHolder.recordCounts} HOST</strong>
									</h4></td>
							</tr>
							<tr height='36'>
								<td width="150">Area</td>
								<td width="120" align="center">Name</td>
								<td width="100" align="center">Price</td>
								<td width="120" align="center">Status</td>
								<td width="300">Address</td>
							</tr>
							<c:forEach var="HolderList" varStatus="stat"
								items="${MangeHolder.pageHolder}">

								<TR height='30'>
									<TD width="150" align="left">${HolderList.city}/
										${HolderList.area}</TD>
									<jsp:useBean id="user" class="_01_register.model.register_DAO" />
									<jsp:setProperty property="selectID" name="user"
										value="${HolderList.userID}" />


									<TD width="120" align="center"><a
										href='<c:url value="HolderDetail.jsp?holderID=${HolderList.userID}"/>'>${user.select.name}</a>
									</TD>
									<TD width="100" align="center">${HolderList.engPrice}</TD>
									<TD width="120" align="center">${HolderList.engOpen_condition}</TD>
									<TD width="300" align="left">${HolderList.city}
										${HolderList.area}${HolderList.hideAddress}</TD>
								</TR>
							</c:forEach>

						</TABLE>
					</div>
				</center>
			</div>
			<table class="table table-hover" align="center" style="background-color: rgba(255,255,255,0.9);">
				<tr>
					<td><c:if test="${HolderPageNo > 1}">
							<div id="pfirst">
								<a href="<c:url value='HolderList.jsp?HolderPageNo=1'/>">First Page</a>&nbsp;&nbsp;&nbsp;
							</div>
						</c:if></td>
					<td><c:if test="${HolderPageNo > 1}">
							<div id="pprev">
								<a
									href="<c:url value='HolderList.jsp?HolderPageNo=${HolderPageNo-1}' />">Previous page</a>&nbsp;&nbsp;&nbsp;
							</div>
						</c:if></td>
					<td><c:if test="${HolderPageNo != totalPages}">
							<div id="pnext">
								<a
									href="<c:url value='HolderList.jsp?HolderPageNo=${HolderPageNo+1}' />">Next page</a>&nbsp;&nbsp;&nbsp;
							</div>
						</c:if></td>
					<td><c:if test="${HolderPageNo != totalPages}">
							<div id="plast">
								<a
									href="<c:url value='HolderList.jsp?HolderPageNo=${totalPages}' />">Last page</a>&nbsp;&nbsp;&nbsp;
							</div>
						
						</c:if></td>
						<td>
							<div align="center">
								<a href="<c:url value='../index.jsp' />">Home page</a>
							</div>
						
						
						</td>
					<td align="center">${HolderPageNo} page / Total ${totalPages} pages</td>
				</tr>
			</table>
		</div>
	</div>
	<c:if test="${param.from eq 'del'}">
		<script type="text/javascript">
			alert("Delete success");
		</script>
	</c:if>
	<form>
		<input type="hidden" name="a" />
	</form>

	<span style="color: red;">${result}</span> <jsp:include
		page="/fragment/newfooter.jsp" /> </section>
		
			<script>
		$('.sm-parallax-window')
				.parallax(
						{
							imageSrc : '${pageContext.request.contextPath}/image/shutterstock.jpg'
						});
	</script>
</body>
</html>