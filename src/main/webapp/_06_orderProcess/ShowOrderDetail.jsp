<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${empty LoginOK}">
	<c:redirect url="/_02_login/login.jsp" />
</c:if>
<title>Order Detail</title>
<style type="text/css">
#main {
	position: absolute;
	top: 110px;
	left: 210px;
}
</style>
</head>
<body>
	<jsp:include page="/fragment/header.jsp" />

	<div class="sm-parallax-window1">
		<div class="container">
			<div class="row"
				style="font-size: 20px; font-family: '微軟正黑體'; padding-top: 10px; margin-bottom: 300px;">


				<h1 style=" color:#fff; text-shadow:1px 1px 8px #000;text-align: center;"><strong>${LoginOK.name}'s Order Detail</strong></h1>


				<table class="table table-hover col-md-12 sm-text">


					<tr>
						<td align="Left" width="500px">Address:${OrderBean.shippingAddress}</td>
						<td align="center" width="300px">Order Date:${OrderBean.orderDate}</td>


						<td align="right" width="500px">Order ID:${OrderBean.orderID}</td>
					</tr>
				</table>
				
				<table class="table col-md-12 sm-text">
					<tr >
						<th width="300px" style="color:#fff; text-shadow:1px 1px 8px #000;text-align:center; ">Ingredient ID</th>
						<th width="1200px" style="color:#fff; text-shadow:1px 1px 8px #000;text-align:center;">Ingredient Info</th>
						<th width="100px" style="color:#fff; text-shadow:1px 1px 8px #000;text-align:center;">Price</th>
						<th width="100px" style="color:#fff; text-shadow:1px 1px 8px #000;text-align:center;">Quantity</th>
						<th width="100px" style="color:#fff; text-shadow:1px 1px 8px #000;text-align:center;">Total</th>
						<th width="50px" style="color:#fff; text-shadow:1px 1px 8px #000;text-align:center;">Discount</th>
						<th width="100px" style="color:#fff; text-shadow:1px 1px 8px #000;text-align:center;">Sale Price</th>
					</tr>
					<c:set var="subtotal" value="0" />
					<c:forEach var="aBean" varStatus="stat" items="${OrderBean.items}">
						<c:choose>
							<c:when test="${ stat.count % 2 == 0 }">
								<c:set var="aColor" value="#E6FFA0" />
							</c:when>
							<c:otherwise>
								<c:set var="aColor" value="#EBFFEB" />
							</c:otherwise>
						</c:choose>


						<tr bgColor="${aColor}" height='30'>
							<td style="text-align:center;">${aBean.itemID}</td>
							<jsp:useBean id="item" class="_04_listItems.model.item_DAO"></jsp:useBean>
							<jsp:setProperty property="selectID" name="item"
								value="${aBean.itemID}" />
							<td align="left">${item.select.name}</td>
							<td align="right">${aBean.unitPrice}&nbsp;</td>
							<td align="right">${aBean.amount}&nbsp;</td>
							<td align="right">${aBean.unitPrice*aBean.amount}&nbsp;</td>
							<td align="center">${aBean.discount}&nbsp;</td>
							<td align="right"><fmt:formatNumber
									value="${aBean.unitPrice*aBean.discount*aBean.amount}"
									pattern="#,###,###" />NTD</td>
						</tr>


					</c:forEach>
						
				
					<tr height='30'>
						<TD align="center" rowspan="3" colspan="5">&nbsp;</TD>
						<TD width="300px" align="right" style="color:#fff; text-shadow:1px 1px 8px #000;">Total Price</TD>
						<TD width="300px" align="right" style="color:#fff; text-shadow:1px 1px 8px #000;"><fmt:formatNumber
								value="${OrderBean.totalAmount+OrderBean.discount}"
								pattern="#,###,###" />NTD</TD>
					</tr>
					<tr height='30'>
					<TD width="300px" align="right" style="color:#fff; text-shadow:1px 1px 8px #000;">Dirct Discount</TD>
						<TD colspan='4' style="color:#fff; text-shadow:1px 1px 8px #000;text-align: right; ">
							${OrderBean.discount }NTD</TD>
					</tr>
					<tr height='40'>
						<TD width="300px" align="right" style="color:#fff; text-shadow:1px 1px 8px #000;">Subtotal</TD>
						<TD width="300px" align="right" style="color:#fff; text-shadow:1px 1px 8px #000;"><fmt:formatNumber
								value="${OrderBean.totalAmount}" pattern="#,###,###" />NTD</TD>
					</tr>
				</TABLE>
				<p />
				<table class="table table-hover col-md-12 sm-text">
					<tr >
					<td>
				<center>
					<a href="<c:url value='OrderList.jsp' />">previous page</a>&nbsp;&nbsp;<a
						href="<c:url value='../index.jsp' />">Home Page</a>
				</center>
				</td>
				</tr>
				</table>
			</div>
		</div>
		<jsp:include page="/fragment/newfooter.jsp"></jsp:include>
	</div>
	<script>
		$('.sm-parallax-window1')
				.parallax(
						{
							imageSrc : '${pageContext.request.contextPath}/image/blue.jpg'
						});
	</script>
</body>
</html>



