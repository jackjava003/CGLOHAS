<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:useBean id="saBean" class="_07_storeInfo.model.StoreAccessDAO" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cooperator</title>
</head>
<body>
	<jsp:include page="/fragment/header.jsp" />
	<section style="padding:0 0; margin:0 0;">
	<div class="container-fuild si-parallax-window1">
		<div class="row"></div>
	</div>
	</section>

	<div class="si_bg" style="padding-bottom:15px; padding-top:15px;">
	
		<div class="container">
			<div class="row si_font1">
				<c:forEach var="aStore" items="${saBean.selectALL}">
					<div class="col-md-4 si_mar va-js-box">
						<!--<a href="${pageContext.servletContext.contextPath}/_07_storeInfo/storeInfoDetail.jsp?id=${aStore.storeid}">-->
						<a
							href="<c:url value='/_07_storeInfo/DisplayStores.do?id=${aStore.storeid}' />">
							<img height='170' width=360px
							src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${aStore.storeid}&type=storeImg'>
						</a>
							<div>${aStore.storename}</div>
					
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

	<c:forEach var="aStore" items="${saBean.selectALL}">
		<div class="modal fade si_font" id="${aStore.storeid}" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
					</div>
					<div class="modal-body">
						<h3>${aStore.shortdesc}</h3>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-success" data-dismiss="modal">Close</button>

					</div>
				</div>
			</div>
		</div>
	</c:forEach>
	<div >
	<div class="container-fuild si-parallax-window1">
		<div class="row"></div>
	</div>
	</div>

	<c:forEach var="aStore" items="${saBean.selectALL}">


	</c:forEach>
	<jsp:include page="/fragment/newfooter.jsp" />
</body>
</html>