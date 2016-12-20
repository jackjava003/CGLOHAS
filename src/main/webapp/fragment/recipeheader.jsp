<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- <link rel="stylesheet" -->
<%-- 	href="${pageContext.request.contextPath}/css/eDM.css" type="text/css" /> --%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css"
	type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/freelancer.min.css"
	type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/font-awesome.min.css"
	type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Pacifico"
	rel="stylesheet">
	
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">

<script
	src="${pageContext.request.contextPath}/javascript/jquery-1.12.4.min.js"></script>
<script
	src="${pageContext.request.contextPath}/javascript/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/javascript/eDM.js"></script>

<noscript>
	<meta http-equiv="Refresh" CONTENT="0;URL=${pageContext.request.contextPath}/openJS.jsp">
</noscript>
<script>
	if (navigator.cookieEnabled == false) {
		window.location.assign("${pageContext.request.contextPath}/openCookie.jsp");
	}
</script>
<style>
.hamburger-menu{
  text-align:center;
  background: none;
  border: none;
  display: inline-block;
 
  }
  .icon-bar1{
    background-color: #fff;
    display: block;
    height: 4px;
    margin: 0 auto 4px;
    width: 30px;
  }
    .icon-bar2{
    background-color: #fff;
    display: block;
    height: 4px;
    margin: 0 auto 4px;
    width: 30px;
  }
    .icon-bar3{
    background-color: #fff;
    display: block;
    height: 4px;
    margin: 0 auto 4px;
    width: 30px;
  }
  
  
  
/* #board2222:hover .icon-bar3{ */
/*  transform: rotate(40deg); */
/*  width: 15px; */
/*   margin: 0 0 4px; */
/* } */
/* #board2222:hover .icon-bar1{ */
/*  transform: rotate(-40deg); */
/*   width: 15px; */
/*   margin: 0 0 4px; */
/* } */


/*   >div */
/*     &:icon-bar(1) */
/*     +transform(rotate(45deg) translate(9px,11px)) */
/*     &:icon-bar(2) */
/*       opacity: 0 */
/*     &:icon-bar(3) */
/*       +transform(rotate(-45deg) translate(6px,-8px)) */


</style>

<c:set var='debug' value='true' scope='application' />
<nav id="mainNav"
	class="navbar navbar-default navbar-fixed-top navbar-custom navbar1 ">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header page-scroll">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> Menu <i
					class="fa fa-bars"></i>
			</button>
			<c:set var="headerimg"
				value="<img src='${pageContext.request.contextPath}/image/logo.jpg' alt='' class='logo'>" />
			${headerimg} <a class="navbar-brand"
				href="${pageContext.request.contextPath}/index.jsp">C.G. LOHAS</a>
		</div>
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li class="hidden"><a href="#page-top"></a></li>
				<li class="page-scroll">
					<div id="checkLogin" style="display: none;">${LoginOK }</div> <a
					href="<c:url value='/_03_recipes/listRecipes.jsp' />">Recipes</a>
				</li>
				<li class="page-scroll"><a
					href="<c:url value='/_04_listItems/listItem.jsp'/>">Ingredients</a></li>
				<li class="page-scroll"><a
					href="<c:url value='/_09_community/index_community.jsp'/>">HOST</a></li>
				<!-- 				<li class="page-scroll"><a -->
				<%-- 					href="<c:url value='/index_member_center.jsp'/>">會員中心</a></li> --%>
				<li class="page-scroll"><a
					href="<c:url value='/_07_storeInfo/storeInfo.jsp'/>">Store Info</a></li>
				<li class="page-scroll"><a
					href="<c:url value='/about_us.jsp'/>">About us</a></li>
				<li class="page-scroll"><c:if test="${  empty LoginOK }">
						<a href="<c:url value='/_02_login/login.jsp' />"> Login </a>
					</c:if> <%-- 				<c:if test="${ ! empty LoginOK }"> --%> <%-- 						<a href="<c:url value='/_02_login/logout.jsp' />"> 登出 </a> --%>
					<%-- 					</c:if> --%></li>

				<c:choose>
					<c:when test="${! empty LoginOK}">
						<jsp:useBean id="notifyDAO"
							class="_11_notification.model.NotifyDAO" />
						<jsp:setProperty property="receiverID" name="notifyDAO"
							value="${LoginOK.userID}" />
						<li class="page-scroll" style="font-family: '微軟正黑體'">
							<div class="dropdown">
								<button id="dLabel" type="button" data-toggle="dropdown"
									class="btn btn-color" onclick="changeToRead()"
									style="padding-top: 12px; border: 0px; background-color: #7aa354; color: #fff;"
									aria-haspopup="true" role="button" aria-expanded="false">
									<div class="nav-color">
										<span class="fa-stack fa-1x"> <i
											class="fa fa-circle fa-stack-2x"></i> <i
											class="fa fa-envelope-o fa-stack-1x fa-inverse"
											style="color: #7aa354;"></i>
										</span>
										<c:if test="${notifyDAO.recordUnreadCounts > 0}">
											<strong style="color: #000;" id="unreadCount">${notifyDAO.recordUnreadCounts}</strong>
										</c:if>
										<span class="caret"></span>
									</div>
								</button>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel"
									style="text-align: right;">
									<!-- 									<a href="#" onclick="changeToRead()"> -->
									<!-- 										<div style="color: #000;"></div> -->
									<!-- 									</a> -->
									<li><a style="color: #000; text-align:left;" id="notify_info"><div></div>
									</a> <a style="color: #000; text-align:left;"
										href="<c:url value='/_11_notification/notify.jsp' />">All notification</a>
									</li>
								</ul>
							</div>
						</li>
						<li class="page-scroll" style="font-family: '微軟正黑體'">
							<div class="dropdown">
								<button id="dLabel" type="button" data-toggle="dropdown"
									class="btn btn-color"
									style="border: 0px; background-color: #7aa354; color: #fff;"
									aria-haspopup="true" role="button" aria-expanded="false">
									<c:set var="headerimg1"
										value="<img src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${LoginOK.userID}&type=userImg' class='logo1 img-circle '>" />
									<div style="padding-top: 5px;" class="nav-color">${headerimg1}&nbsp;${LoginOK.name}</div>
									<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel"
									style="text-align: right;">
									<li><a
										href="${pageContext.request.contextPath}/_01_userUpdate/ShowMember.jsp"
										style="color: #000;">Member center</a></li>
									<li><a href="<c:url value='/_02_login/logout.jsp' />"
										style="color: #000;"> Logout </a></li>
								</ul>
							</div>
						</li>
			</ul>
		</div>
		</c:when>
		<c:otherwise>
			<li class="page-scroll"><a
				href="<c:url value='/_00_register/register.jsp'/>">Sign Up</a></li>
		</c:otherwise>
		</c:choose>

		</ul>
	</div>
	<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>

<jsp:useBean id="recipe_items" class="_03_recipes.model.recipes_DAO" />
<jsp:setProperty property="selectID" name="recipe_items"
	value="${param.id}" />
<div id="board111"
	style="padding-bottom: 10px;font-family:微軟正黑體; margin-top: 300px; width: 200px; border-top-right-radius: 20px; border-bottom-right-radius: 20px; background-color:rgba(255,130,0,0.6); position: fixed; left: -200px; z-index: 1; padding-left: 30px;">
	<c:if test="${not empty LoginOK }">
		<jsp:useBean id="MyfavDAO" class="_08_favour.model.ManageMyFavourDAO" />
		<jsp:setProperty property="favourUserID" name="MyfavDAO"
			value="${LoginOK.userID}" />
		<c:set var="totalPages" value="${MyfavDAO.totalPages}" />
		<c:choose>
			<c:when test="${empty param.favourPageNo}">
				<c:set var="favourPageNo" value="1" />
			</c:when>
			<c:when test="${param.favourPageNo > totalPages}">
				<c:set var="favourPageNo" value="1" />
			</c:when>
			<c:otherwise>
				<c:set var="favourPageNo" value="${param.favourPageNo}" />
			</c:otherwise>
		</c:choose>
		<jsp:setProperty property="pageNo" name="MyfavDAO"
			value="${favourPageNo}" />
		<table>
			<TR>
				<TD colspan="2" align="center"
					style="text-shadow: 1px 1px 5px #000; color: #fff; font-size: 22px;">My favourite</TD>
			</TR>

			<c:forEach var="favourController" items="${MyfavDAO.pageFavour}">
				<TR>
					<TD><img height='50' width='50'
						src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${favourController.recipesID}&type=Recipes'></TD>
					<TD><jsp:useBean id="recipeNameController"
							class="_03_recipes.model.recipes_DAO" /> <jsp:setProperty
							property="selectID" name="recipeNameController"
							value="${favourController.recipesID}" />
						<div style="width: 100px; color: white;">
							<a
								style="text-shadow: 1px 1px 5px #000; color: #fff; font-size: 16px;"
								href="<c:url value='RecipesDetail.jsp?id=${favourController.recipesID}'/>">${recipeNameController.select.name}</a>
						</div></TD>
				</TR>
			</c:forEach>
		</table>
		<div id="paging">
			<!-- 以下為控制第一頁、前一頁、下一頁、最末頁 等超連結-->
			<table>
				<tr>
					<td width='76'><c:if test="${favourPageNo > 1}">
							<div id="pprev">
								<a
									href="<c:url value='/_03_recipes/RecipesDetail.jsp?favourPageNo=${favourPageNo-1}&id=${param.id}&pageNo=${param.pageNo}&type=${param.type}' />">Previous page</a>&nbsp;&nbsp;&nbsp;
							</div>
						</c:if></td>
					<td width='76'><c:if
							test="${(favourPageNo != totalPages) and (totalPages != 0)}">
							<div id="pnext">
								<a
									href="<c:url value='/_03_recipes/RecipesDetail.jsp?favourPageNo=${favourPageNo+1}&id=${param.id}&pageNo=${param.pageNo}&type=${param.type}' />">
									Next page</a>&nbsp;&nbsp;&nbsp;
							</div>
						</c:if></td>
				</tr>
			</table>
		</div>
	</c:if>
	<c:if test="${empty LoginOK }">
		<p style="color: white;">
			Please <a
				href="<c:url value='/_02_login/login.jsp?fromRecipesDetail=${param.id}&pageNo=${param.pageNo}&type=${param.type}' />">
				Login </a>or <a href="<c:url value='/_00_register/register.jsp' />">Sign up </a>
		</p>
	</c:if>
</div>
<div id="board2222"
	style="  cursor: pointer;left:0px;top: 400px;height: 50px; width: 50px; border-radius:40px;  background-color: orange; position: fixed;  z-index: 1; padding-top:14px; padding-left:10px;">
	
<div class="hamburger-menu">
  <span class="icon-bar1"></span>
  <span class="icon-bar2 icon-bar4"></span>
  <span class="icon-bar3"></span>
</div>


</div>
<div class='dt-loading'>
	<div class='laying'></div>
	<div class='layout'></div>
</div>










