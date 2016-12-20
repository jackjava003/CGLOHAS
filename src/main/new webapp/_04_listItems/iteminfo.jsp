<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>C.G.LOHAS</title>
<script
	src="${pageContext.request.contextPath}/javascript/ajax.ShoppingCart.js "></script>
	<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>
<body>
	<jsp:include page="/fragment/header.jsp" />

	<div class="sm-parallax-window1">
		<div style="font-family: '微軟正黑體'">
			<div class="va-box-bg1">
				<div class="container">
					<div class="row">
						<c:choose>
							<c:when test="${ShoppingCart.itemNumber > 0}">
								<!-- 購物車內有一項以上的商品 -->
								<c:set var="cartContent"
									value="Cart contain ${ShoppingCart.itemNumber} items" />
							</c:when>
							<c:otherwise>
								<!-- 購物車內沒有商品 -->
								<c:set var="cartContent" value="You have not purchased any items" />
							</c:otherwise>
						</c:choose>
						<!-- 						<TABLE class="table table-hover col-md-12 sm-text" -->
						<!-- 							style="background: #fff; font-size: 20px;"> -->
						<!-- 							<TR> -->
						<%-- 								<TD style="color: red; font-weight: bold;" align="center">${erroMessage}</TD> --%>
						<!-- 							</TR> -->
						<!-- 						</TABLE> -->

						<jsp:useBean id="iaBean" class="_04_listItems.model.item_DAO" />
						<c:set value="${param.id}" property="selectID" target="${iaBean}" />
						<c:set value="${iaBean.select}" var="items" />
						<c:set var="rowColor" value="#e0fede" />
						<!-- 				<img height='400' width='400' -->
						<%-- 					src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${items.itemID}&type=ITEM'> --%>
						<div class="col-md-6">
							<table class="table table-hover col-md-12 sm-text"
								style="background: #fff; font-size: 20px;">
								<tr>
									<td><img height='100%' width='100%'
										src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${items.itemID}&type=ITEM'>
									</td>
								</tr>
							</TABLE>
						</div>




						<div class="col-md-6">
							<TABLE class="table table-hover col-md-6 sm-text" border='0'
								style="background: #fff; font-size: 20px;">

								<%-- 								<TD width='140'>編號：${items.itemID}</TD> --%>
								<TD style="text-align: center;"><font id="errorMessage"
									style="color: red; font-weight: bold;" align="center">
										${erroMessage}</font> <br> Price：<fmt:formatNumber
										value="${items.price}" pattern="####" />NTD per 100 grams<br> <c:if
										test="${ items.discount != 1 }">
										<Font color='red'>Discount: ${items.discount},&nbsp; Sale price<fmt:formatNumber
												value="${items.price*items.discount}" pattern="####" />
											NTD,&nbsp;save<fmt:formatNumber
												value="${items.price - items.price*items.discount}"
												pattern="####" /> NTD 
									</c:if></TD>
								<%-- 								<c:if test="${ items.discount != 1 }"> --%>
								<!-- 									<TD width='374'><Font color='red'> -->
								<%-- 											${items.discount}折,&nbsp; 實售<fmt:formatNumber --%>
								<%-- 												value="${items.price*items.discount}" pattern="####" /> --%>
								<%-- 											元,&nbsp;省<fmt:formatNumber --%>
								<%-- 												value="${items.price - items.price*items.discount}" --%>
								<%-- 												pattern="####" />元 --%>
								<!-- 									</Font></TD> -->
								<%-- 								</c:if> --%>
								<%-- 								<c:if test="${ items.discount == 1 }"> --%>
								<!-- 									<TD width='260'>&nbsp;</TD> -->
								<%-- 								</c:if> --%>
							</TABLE>


							<TABLE class="table table-hover col-md-6 sm-text" border='0'
								style="background: #fff; font-size: 20px;">
								<tr>
									<td>Purchase quantity: <select name='qty' id='qty${items.itemID}'>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
											<option value="5">5</option>
											<option value="6">6</option>
											<option value="7">7</option>
											<option value="8">8</option>
											<option value="9">9</option>
											<option value="10">10</option>
									</select>
										<button class="btn btn-success" id=""
											onclick="AjaxShoppingCart(${items.itemID},${items.price},${items.discount})">Add to Cart</button>
										Remaining storage: ${items.storage}
									</TD>
								</TR>
							</table>
							<TABLE class="table table-hover col-md-6 sm-text"
								style="background: #fff; font-size: 20px;">
								<tr>
									<td><span id="cartContent">${cartContent} </span><br> <A
										href="<c:url value='/_04_listItems/listItem.jsp?pageNo=${param.pageNo}&type=${param.type}' />">Continue shopping</A>

										<A
										href="<c:url value='../_05_ShoppingCart/ShowCartContent.jsp?pageNo=${pageNo}&type=${param.type}' />">Shopping list</A><br>
										<FONT color='red'> Subtotal: <c:choose>
												<c:when test="${ ShoppingCart.subtotal==0.0}">
													<c:set var="subtotal" value="0" />
												</c:when>
												<c:otherwise>
													<c:set var="subtotal" value="${ShoppingCart.subtotal}"/>
												</c:otherwise>
											</c:choose> <span id='currentTotal'>${subtotal}</span>  NTD

									</FONT><br>
									</td>
								</tr>
							</TABLE>
						</div>
						<TABLE class="table table-hover col-md-12 sm-text"
							style="background: #fff; font-size: 20px;">
							<TR>


								<TD>
									<div style="font-size: 28px; width: 300px; text-align: center;">
										<strong>${items.name}<br>Product Description
										</strong>
									</div>
								</TD>
								<TD>
									<div style="text-align: left;">${items.info}</div>
								</TD>
							</TR>
						</TABLE>
					</div>
				</div>
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