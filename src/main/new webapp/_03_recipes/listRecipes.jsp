<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Recipes</title>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>

<body>
	<!-- 下列敘述設定變數funcName的值為SHO，top.jsp 會用到此變數 -->
	<c:set var="funcName" value="SHO" scope="session" />
	<!-- 引入共同的頁首 -->
	<jsp:include page="/fragment/header.jsp" />
	<!-- 判斷購物車內是否有商品 -->

	<!-- 
      forEach 顯示購物車的內容
      識別字串products_DPP是由_03_listBooks.controller.DisplayPageProducts.java放入
      request物件內
   -->

	<section style="margin: 0 0;padding: 0 0;">
	<div class="container-fuild">
		<div class="row">
			<div class="col-md-12">
				<div class="cou3">
					<div class="co-bg1"></div>
					<div class="co-text">
						<h1 class="ab_us_8">
							<strong>C.G. LOHAS Creative Recipes</strong>
						</h1>
						<br>
						<h4 class="ab_us_9">
							<strong>From the thoughts of home flavor, to the top of the exotic food to enjoy</strong>
						</h4>
						<h4 class="ab_us_10">
							<strong>Cuisine can be aesthetics, can be words</strong>
						</h4>
						<h4 class="ab_us_11">
							<strong>Used to carry and convey the love between people and warmth</strong>
						</h4>
						<h4 class="ab_us_12">
							<strong>This is what we called 「Cuisine」</strong>
						</h4>
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
								<a href="<c:url value='listRecipes.jsp?pageNo=1' />"><img
									src="${pageContext.request.contextPath}/image/png/photos.png"
									class="va-image">All</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">
							<div class="va-image1">
								<a href="<c:url value='listRecipes.jsp?pageNo=1&type=時蔬' />"><img
									src="${pageContext.request.contextPath}/image/png/cabbage.png"
									class="va-image ">Veget</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">
							<div class="va-image1">
								<a href="<c:url value='listRecipes.jsp?pageNo=1&type=海鮮' />"><img
									src="${pageContext.request.contextPath}/image/png/fish2.png"
									class="va-image ">Seafood</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">

							<div class="va-image1">
								<a href="<c:url value='listRecipes.jsp?pageNo=1&type=涼拌' />"><img
									src="${pageContext.request.contextPath}/image/png/spaghetti.png"
									class="va-image ">Starter</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">

							<div class="va-image1">
								<a href="<c:url value='listRecipes.jsp?pageNo=1&type=湯類' />"><img
									src="${pageContext.request.contextPath}/image/png/soup.png"
									class="va-image ">Soup</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">
							<div class="va-image1">
								<a href="<c:url value='listRecipes.jsp?pageNo=1&type=甜點' />"><img
									src="${pageContext.request.contextPath}/image/png/cupcake-2.png"
									class="va-image ">Dessert</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">
							<div class="va-image1">
								<a href="<c:url value='listRecipes.jsp?pageNo=1&type=肉類' />"><img
									src="${pageContext.request.contextPath}/image/png/steak.png"
									class="va-image ">Meat</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">
							<div class="va-image1">
								<a href="<c:url value='listRecipes.jsp?pageNo=1&type=蛋類' />"><img
									src="${pageContext.request.contextPath}/image/png/fried-egg.png"
									class="va-image ">Egg</a>
							</div>
						</td>
						<td style="border: none;" class="va-image1 va-js-box">
							<div class="va-image1">
								<a href="<c:url value='listRecipes.jsp?pageNo=1&type=麵類' />"><img
									src="${pageContext.request.contextPath}/image/png/spaguetti.png"
									class="va-image ">Noodles</a>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	</section>
	<section class="re-parallax-window"
		style="margin:0 0; padding-bottom:0px;"> <jsp:useBean
		id="RecipesDAO" class="_03_recipes.model.recipes_DAO" /> <c:if
		test="${!empty param.type }">
		<jsp:setProperty property="type" name="RecipesDAO"
			value="${param.type}" />
	</c:if> <c:set var="totalPages" value="${RecipesDAO.totalPages}" /> <c:choose>
		<c:when test="${empty param.pageNo}">
			<c:set var="pageNo" value="1" />
		</c:when>
		<c:when test="${param.pageNo > totalPages}">
			<c:set var="pageNo" value="1" />
		</c:when>
		<c:otherwise>
			<c:set var="pageNo" value="${param.pageNo}" />
		</c:otherwise>
	</c:choose> <jsp:setProperty property="pageNo" name="RecipesDAO" value="${pageNo}" />
	<div class="container">
		<div class="row">
			<c:forEach varStatus="stVar" var="recipesBean"
				items="${RecipesDAO.pageRecipes}">
				<table class="table table-hover"
					style="background: #fff; border-radius: 50px 50px; border: none;">
					<tr>
						<td style="border: none;" class="table-hover1">
							<div class="col-md-6 col-sm-12 col-xs-12">
								<div class="li_picter">
									<a
										href="<c:url value='RecipesDetail.jsp?id=${recipesBean.recipesID}&pageNo=${pageNo}&type=${param.type}' />">
										<div
											style="background-size:cover; background-position:center; border-radius: 50px 50px; height:400px; width:500px;   background-image:url(${pageContext.servletContext.contextPath}/_00_init/getImage?id=${recipesBean.recipesID}&type=Recipes) ">
											<h3 class="li_text">
												Click for Recipe steps<br>
												<div style="font-size: 40px">
													<strong>${recipesBean.name}</strong>
												</div>
											</h3>
										</div>
									</a>
								</div>
							</div>
							<div class="col-md-6 col-sm-12 col-xs-12"
								style="font-family: '微軟正黑體';">
								<div style="font-size: 36px; text-align: left; width: 400px;">
									<h1 style="font-size: 36px; margin-top: 60px;">
										<strong>${recipesBean.name}</strong>
									</h1>
									<br>
									<h4 width='100'>Recipes ID：${recipesBean.recipesID}</h4>
									<br>
									<h4 width='140'>Recipes type：${recipesBean.type}</h4>
									<br>
<%-- 									<h4 width='260'>食譜風格：${recipesBean.culture}</h4> --%>
								</div>

							</div>
						</td>
					</tr>
				</table>
			</c:forEach>
			<div id="paging" class="col-md-12">
				<!-- 以下為控制第一頁、前一頁、下一頁、最末頁 等超連結-->
				<table class="table table-hover col-md-12 sm-text"
					style="background: #fff; font-size: 20px; margin-top: 20px; border-radius: 10px;">
					<tr>
						<td width='76' style="border: none;"><c:if
								test="${pageNo > 1}">
								<div id="pfirst">
									<a
										href="<c:url value='DisplayPageRecipes.do?pageNo=1&type=${param.type }' />">First Page</a>&nbsp;&nbsp;&nbsp;
								</div>
							</c:if></td>
						<td width='76' style="border: none;"><c:if
								test="${pageNo > 1}">
								<div id="pprev">
									<a
										href="<c:url value='DisplayPageRecipes.do?pageNo=${pageNo-1}&type=${param.type }' />">Previous page</a>&nbsp;&nbsp;&nbsp;
								</div>
							</c:if></td>
						<td width='76' style="border: none;"><c:if
								test="${pageNo != totalPages}">
								<div id="pnext">
									<a
										href="<c:url value='DisplayPageRecipes.do?pageNo=${pageNo+1}&type=${param.type }' />">Next Page</a>&nbsp;&nbsp;&nbsp;
								</div>
							</c:if></td>
						<td width='76' style="border: none;"><c:if
								test="${pageNo != totalPages}">
								<div id="plast">
									<a
										href="<c:url value='DisplayPageRecipes.do?pageNo=${totalPages}&type=${param.type }' />">Last page</a>&nbsp;&nbsp;&nbsp;
								</div>
							</c:if></td>
						<td width='176' align="center" style="border: none;">${pageNo} page
							/ Total ${totalPages} pages</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<jsp:include page="/fragment/newfooter.jsp" /> </section>
	<script>
		$('.re-parallax-window').parallax({
			imageSrc : '${pageContext.request.contextPath}/image/0036228.jpeg'
		});
	</script>
</body>
</html>