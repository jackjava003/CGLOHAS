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


<noscript>
	<meta http-equiv="Refresh" CONTENT="0;URL=${pageContext.request.contextPath}/openJS.jsp">
</noscript>
<script>
	if (navigator.cookieEnabled == false) {
		window.location.assign("${pageContext.request.contextPath}/openCookie.jsp");
	}
</script>


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
											<strong style="color:#000;" id="unreadCount">${notifyDAO.recordUnreadCounts}</strong>
										</c:if>
										<span class="caret"></span>
									</div>
								</button>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel"
									style="text-align: right;">
<!-- 									<a href="#" onclick="changeToRead()"> -->
<!-- 										<div style="color: #000;"></div> -->
<!-- 									</a> -->
									<li>
										<a style="color:#000; text-align:left;" id="notify_info"><div ></div> </a>
										
										<a style="color: #000; text-align:left;"
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




<div class='dt-loading'>
	<div class='laying'></div>
	<div class='layout'></div>
</div>











