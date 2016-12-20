<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
<script type="text/javascript">
function confirmDelete(n) {
	if (confirm("Do you want to delete this order ? ") ) {
		document.forms[0].action="<c:url value='DeleteOrder.do?orderID=" + n +"' />" ;
		document.forms[0].method="POST";
		document.forms[0].submit();
	} else {
	
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:if test="${empty LoginOK}">
	<c:redirect url="/_02_login/login.jsp" />
</c:if>
<jsp:useBean id="orderBeans" class="_05_ShoppingCart.model.OrderDAO"
	scope="page" />
<jsp:setProperty property="memberId" name="orderBeans"
	value="${LoginOK.userID}" />
<title>Order list</title>
</head>
<body>

	<c:set var="funcName" value="ORD" scope="session" />
	<!-- 引入共同的頁首 -->
	<jsp:include page="/fragment/header.jsp" />
	<p />
	<div class="sm-parallax-window1">
		<div class="container">
			<div class="row" style="font-size:20px; font-family:'微軟正黑體';padding-top:10px;margin-bottom:300px;">
				
					

					
						<h1 style="text-align:center; color:#fff; text-shadow:1px 1px 8px #000;"><strong>${LoginOK.name}'s Order List</strong></h1>
					
							<table class="table col-md-12 sm-text">
						<tr style="text-align:center; color:#fff; text-shadow:1px 1px 8px #000;">
							<th width="100">Order ID</th>
							<th width="200" style="text-align:center;">Order Date</th>
							<th width="80" style="text-left;">Subtotal</th>
							<th width="100">Address</th>
							<th></th>
						</tr>
						<c:forEach var="anOrderBean" varStatus="stat"
							items="${orderBeans.memberOrders}">

							<TR height='30'>
								<TD width="100" align="center" style="font-size:20px;"><a
									href='<c:url value='orderDetail.do?memberId=${LoginOK.userID}&orderNo=${anOrderBean.orderID}' />'>
										${anOrderBean.orderID} </a></TD>
								<TD width="200"style="color:#fff; text-shadow:1px 1px 8px #000;text-align:center;">${anOrderBean.orderDate}</TD>
								<TD width="80" style="color:#fff; text-shadow:1px 1px 8px #000;"text-left;">${anOrderBean.totalAmount}</TD>
								<TD width="400" style="color:#fff; text-shadow:1px 1px 8px #000;" align="left">&nbsp;${anOrderBean.shippingAddress}</TD>
								<TD width="100"><Input type="button" name="delete" class="btn btn-success" 
									value="Delete" onClick="confirmDelete(${anOrderBean.orderID})"></TD>
								<!--
							<%-- 
							<TD width="100" align="center"><input type="hidden"
								value="${anOrderBean.orderNo}" name="OrderNo"> <input
								type="hidden" value="${anOrderBean.totalAmount}"
								name="totalAmount"> <input type="hidden"
								value="${anOrderBean.orderDate}" name="orderDate"> <input
								type="submit" value="詳細資料"></TD>
								--%>
  						 -->
							</TR>



						</c:forEach>
							</TABLE>
						<table class="table table-hover col-md-12 sm-text">
						<tr height='36'>
							<td align="center" colspan="4"><a
								href="<c:url value='../index.jsp' />">Home Page</a></td>
						</tr>
					</TABLE>
		
				<form>
					<input type="hidden" name="a" />
				</form>

				<span style="color: red;">${result}</span>
				<!--
		<%-- 
		<c:forEach var="anOrderBean" varStatus="stat"
			items="${orderBeans.allOrders}">
			<c:choose>
				<c:when test="${ stat.count % 2 == 0 }">
					<c:set var="aColor" value="#556677" />
				</c:when>
				<c:otherwise>
					<c:set var="aColor" value="#AA0077" />
				</c:otherwise>
			</c:choose>
			<TR bgColor="${aColor}">
				<TD>${anOrderBean.orderNo}</TD>
				<TD>${anOrderBean.userId}</TD>
				<TD>${anOrderBean.totalAmount}</TD>
				<TD>${anOrderBean.orderDate}</TD>
			</TR>
		</c:forEach>
	</TABLE>
	--%>
	 -->
			</div>
		</div>
		<jsp:include page="/fragment/newfooter.jsp" />
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