<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:useBean id="iaBean" class="_04_listItems.model.item_DAO" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ingredients</title>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>
<script
	src="${pageContext.request.contextPath}/javascript/ajax.ShoppingCart.js "></script>


<body>
	<jsp:include page="/fragment/header.jsp" />
	<jsp:useBean id="ShoppingCart"
		class="_05_ShoppingCart.model.ShoppingCart" scope="session" />
	<c:choose>
		<c:when test="${ShoppingCart.itemNumber > 0}">
			<!-- 購物車內有一項以上的商品 -->
			<c:set var="cartContent" value="Cart contain${ShoppingCart.itemNumber} items" />
		</c:when>
		<c:otherwise>
			<!-- 購物車內沒有商品 -->
			<c:set var="cartContent" value="You have not purchased any items" />
		</c:otherwise>
	</c:choose>

	<jsp:useBean id="DisplayProduct" class="_04_listItems.model.item_DAO" />
	<c:if test="${!empty param.type }">
		<jsp:setProperty property="type" name="DisplayProduct"
			value="${param.type}" />
	</c:if>
	<c:set var="totalPages" value="${DisplayProduct.totalPages}" />
	<c:choose>
		<c:when test="${empty param.pageNo}">
			<c:set var="pageNo" value="1" />
		</c:when>
		<c:when test="${param.pageNo > totalPages}">
			<c:set var="pageNo" value="1" />
		</c:when>
		<c:otherwise>
			<c:set var="pageNo" value="${param.pageNo}" />
		</c:otherwise>
	</c:choose>
	<jsp:setProperty property="pageNo" name="DisplayProduct"
		value="${pageNo}" />
	<TABLE class="table table-hover sm-text poster1-js-text-resetoff"
		style="z-index: 1; font-family: '微軟正黑體'; background: #fff; font-size: 20px; position:fixed; width: 250px; left: 0; top: 50%; border-radius: 10px; attachment: fixed;">
		<tr>
			<td id="cartContent">${cartContent}</td>
		</tr>
		<TR>
			<TD><A
				href="<c:url value='../_05_ShoppingCart/ShowCartContent.jsp?pageNo=${pageNo}&type=${param.type}' />">Shopping list</A></TD>
		</TR>

		<TR>
			<TD align='center'><FONT color='red'> Subtotal:<c:choose>
						<c:when test="${ ShoppingCart.subtotal==0.0}">
							<c:set var="subtotal" value="0" />
						</c:when>
						<c:otherwise>
							<c:set var="subtotal" value="${ShoppingCart.subtotal}" />
						</c:otherwise>
					</c:choose> <span id='currentTotal'>${subtotal}</span> NTD
			</FONT></TD>
		</TR>
		<TR>
			<TD id="errorMessage" style="color: red; font-weight: bold;"
				align="center">${erroMessage}</TD>
		</TR>
	</table>

	<section style="margin: 0 0;padding: 0 0;">
	<div class="container-fuild">
		<div class="row">
			<div class="col-md-12">
				<div class="cou2">
					<div class="co-text">
						<h1 class="ab_us_9">
							<strong>C.G. LOHAS Online Shop</strong>
						</h1>
						<br>
						<h4 class="ab_us_10">
							<strong>Fresh fruit and vegetable straight to your place</strong>
						</h4>
						<h4 class="ab_us_11">
							<strong>Delivery to your place within one day, free shipping</strong>
						</h4>

						<h3 class="ab_us_12">
							<strong>Shopping over a thousand get one hundred direct discount.</strong>
						</h3>
					</div>
				</div>
			</div>
		</div>
	</div>
	</section>
	<section
		style="margin:30 0; ;  margin-bottom: 60px; height:200px; position:relative;">
	<div class="container-fuild">
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover"
					style="border: none; text-align: center; font-size: 30px;">
					<tr style="border: 3px soild;">
						<td style="border: none;" class="va-js-box">
							<div class="va-image1">
								<a href="<c:url value='listItem.jsp?pageNo=1' />"> <img
									src="${pageContext.request.contextPath}/image/png/photos.png"
									class="va-image ">All
								</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">
							<div class="va-image1">
								<a href="<c:url value='listItem.jsp?pageNo=1&type=蔬菜' />"> <img
									src="${pageContext.request.contextPath}/image/png/lettuce.png"
									class="va-image ">Veget
								</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">
							<div class="va-image1">
								<a href="<c:url value='listItem.jsp?pageNo=1&type=調味料' />">
									<img
									src="${pageContext.request.contextPath}/image/png/jam.png"
									class="va-image ">Seasoning
								</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">

							<div class="va-image1">
								<a href="<c:url value='listItem.jsp?pageNo=1&type=蛋' />"> <img
									src="${pageContext.request.contextPath}/image/png/boiled-egg.png"
									class="va-image ">Egg
								</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">

							<div class="va-image1">

								<a href="<c:url value='listItem.jsp?pageNo=1&type=肉類' />"> <img
									src="${pageContext.request.contextPath}/image/png/steak1.png"
									class="va-image ">Meat
								</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">
							<div class="va-image1">
								<a href="<c:url value='listItem.jsp?pageNo=1&type=甜點' />"> <img
									src="${pageContext.request.contextPath}/image/png/ice-cream-1.png"
									class="va-image ">Dessert
								</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">
							<div class="va-image1">
								<a href="<c:url value='listItem.jsp?pageNo=1&type=海鮮' />"> <img
									src="${pageContext.request.contextPath}/image/png/lobster.png"
									class="va-image ">Seafood
								</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">
							<div class="va-image1">
								<a href="<c:url value='listItem.jsp?pageNo=1&type=水果' />"> <img
									src="${pageContext.request.contextPath}/image/png/lemon-1.png"
									class="va-image ">Fruit
								</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">
							<div class="va-image1">
								<a href="<c:url value='listItem.jsp?pageNo=1&type=果汁' />"> <img
									src="${pageContext.request.contextPath}/image/png/yogurt.png"
									class="va-image ">Juice
								</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">
							<div class="va-image1">
								<a href="<c:url value='listItem.jsp?pageNo=1&type=乾貨' />"> <img
									src="${pageContext.request.contextPath}/image/png/rice.png"
									class="va-image ">Dry goods
								</a>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	</section>

	<div class="sm-parallax-window1">
		<div class="va-sign va-box-bg">
			<div class="container" style="font-family: 微軟正黑體">
				<div class="row">
					<c:forEach varStatus="stVar" var="aBookBean"
						items="${DisplayProduct.pageItems}">
						<div class="col-md-3 col-sm-6 col-xs-12 ">
							<div class="va-box  va-js-box">
								<a
									href="${pageContext.servletContext.contextPath}/_04_listItems/iteminfo.jsp?id=${aBookBean.itemID}&pageNo=${pageNo}&type=${param.type}">
									<div class="va-box-image"
										style="background-image:url(  
         ${pageContext.servletContext.contextPath}/_00_init/getImage?id=${aBookBean.itemID}&type=ITEM)">
										<div class="va-row ">
											<h2 class="va-box-text left">${aBookBean.price}</h2>
											<div class="va-box-text-ntd left">NTD</div>
											<div class="right"></div>
										</div>
									</div>
								</a>
								<div class="va-box-text1">
									<div class="va-box-text1-1">
										<div class="va-box-text1-2">
											<strong>${aBookBean.name}</strong>
										</div>
										Purchase quantity: <select name='qty' id='qty${aBookBean.itemID}'>
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
										<!--                這些隱藏欄位都會送到後端 -->

										<button class="btn btn-success" id=""
											onclick="AjaxShoppingCart(${aBookBean.itemID},${aBookBean.price},${aBookBean.discount})">Add to Cart</button>
									</div>

									<div class="va-box-text1-3">
										<c:if test="${ aBookBean.discount != 1 }">
											<fmt:formatNumber var="VAT" type="number"
												value="${aBookBean.discount*10}" maxFractionDigits="0" />
											<Font color='red'>Discount: ${VAT},&nbsp; Sale price<fmt:formatNumber
													value="${aBookBean.price*aBookBean.discount}"
													pattern="####" /> NTD,&nbsp;save<fmt:formatNumber
													value="${aBookBean.price - aBookBean.price*aBookBean.discount}"
													pattern="####" />NTD
											</Font>
										</c:if>
										<c:if test="${ aBookBean.discount == 1}">
											<BR>
										</c:if>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
					<div id="paging">
						<!-- 以下為控制第一頁、前一頁、下一頁、最末頁 等超連結-->
						<table class="table table-hover col-md-12 sm-text"
							style="background: #fff">
							<tr class="table-text">
								<td><c:if test="${pageNo > 1}">
										<div id="pfirst">
											<a
												href="<c:url value='listItem.jsp?pageNo=1&type=${param.type}' />">First Page</a>&nbsp;&nbsp;&nbsp;
										</div>
									</c:if></td>
								<td><c:if test="${pageNo > 1}">
										<div id="pprev">
											<a
												href="<c:url value='listItem.jsp?pageNo=${pageNo-1}&type=${param.type}' />">Previous page</a>&nbsp;&nbsp;&nbsp;
										</div>
									</c:if></td>
								<td><c:if test="${pageNo != totalPages}">
										<div id="pnext">
											<a
												href="<c:url value='listItem.jsp?pageNo=${pageNo+1}&type=${param.type}' />">Next page</a>&nbsp;&nbsp;&nbsp;
										</div>
									</c:if></td>
								<td><c:if test="${pageNo != totalPages}">
										<div id="plast">
											<a
												href="<c:url value='listItem.jsp?pageNo=${totalPages}&type=${param.type}' />">Last page</a>&nbsp;&nbsp;&nbsp;
										</div>
									</c:if></td>
								<td align="center">${pageNo} page / Total ${totalPages} pages</td>
							</tr>
						</table>
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