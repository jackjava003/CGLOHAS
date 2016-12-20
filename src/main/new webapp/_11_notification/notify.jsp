<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:if test="${empty LoginOK }">
	<c:redirect url="/index.jsp" />
</c:if>
<head>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
<title>C.G.LOHAS</title>
</head>
<body>
	<jsp:useBean id="notifyDAO" class="_11_notification.model.NotifyDAO" />
	<jsp:setProperty property="receiverID" name="notifyDAO"
		value="${LoginOK.userID}" />
	<input type="hidden" value="${notifyDAO.updateCheckRead}">
	<jsp:include page="/fragment/header.jsp" />

	<c:set var="totalPages" value="${notifyDAO.totalPages}" />
	<c:choose>
		<c:when test="${empty param.notifyPageNo or notifyDAO.recordCounts le (param.notifyPageNo-1)*5}">
			<c:set var="notifyPageNo" value="1" />
		</c:when>
		<c:otherwise>
			<c:set var="notifyPageNo" value="${param.notifyPageNo}" />
		</c:otherwise>
	</c:choose>
	<jsp:setProperty property="pageNo" name="notifyDAO"
		value="${notifyPageNo}" />

	<section class="sm-parallax-window3"
		style=" background-color:rgba(0,0,0,0.4); padding:0 0; margin:0 0; ">
	<div class="container" style="font-family: 微軟正黑體; text-align: center; padding-bottom:200px;">
		<div class="row">
			<div class="col-md-12" align="center">


			<strong style="color:#fff; text-shadow: 1px 1px 8px #000; " ><h1>Your Notifications</h1></strong>
	<table class="table" align="center"  style="background-color:rgba(255,255,255,0.9); font-size:22px; border-radius:10px;">
		
			
		
		<c:forEach var="notifyBean" items="${notifyDAO.pageNotify}">
			<c:set var="test"
				value="HolderDetail.jsp?holderID=${notifyBean.receiverID}" />

			<tr>
				<td><c:choose>
						<c:when test="${notifyBean.notifyType == 1 }">
							<a
								href='<c:url value="/_09_community/HolderDetail.jsp?JoinMessage=1&holderID=${notifyBean.receiverID}"/>'>
						</c:when>
						<c:when test="${notifyBean.notifyType == 2 }">
							<a
								href='<c:url value="/_09_community/HolderDetail.jsp?holderID=${notifyBean.receiverID}"/>'>
						</c:when>
						<c:when test="${notifyBean.notifyType == 3 }">
							<a
								href='<c:url value="/_09_community/HolderDetail.jsp?Message=1&holderID=${notifyBean.receiverID}"/>'>
						</c:when>
						<c:when test="${notifyBean.notifyType == 4 }">
							<a
								href='<c:url value="/_09_community/HolderDetail.jsp?Message=1&holderID=${notifyBean.senderID}'"/>'>
						</c:when>
					</c:choose>

					<div class="notiList">
						<img
							src="${pageContext.servletContext.contextPath}/_00_init/getImage?id=${notifyBean.senderID}&type=userImg"
							width="30px" height="30px"> ${notifyBean.senderName}
						<c:choose>
							<c:when test="${notifyBean.notifyType == 1}">
						 has applied your activity
					</c:when>
							<c:when test="${notifyBean.notifyType == 2}">
						 has evaluated your activity
					</c:when>
							<c:when test="${notifyBean.notifyType == 3}">
						 has leave message for your activity
					</c:when>
							<c:when test="${notifyBean.notifyType == 4}">
						 has replied your message
					</c:when>
						</c:choose>
						- ${notifyBean.sendRealTime}
					</div> </a></td>

			</tr>
		</c:forEach>
		<tr>
			<td align="center">- Total ${notifyDAO.recordCounts} notifications -</td>
		</tr>
	</table>
	<c:if test="${notifyDAO.recordCounts ge 1}">
		<table class="table" align="center"  style="background-color:rgba(255,255,255,0.9); font-size:22px; border-radius:10px;">
			<tr>
				<td width='76'><c:if test="${notifyPageNo > 1}">

						<a href="<c:url value='notify.jsp?notifyPageNo=1'/>">First Page</a>&nbsp;&nbsp;&nbsp;
									
									</c:if></td>
				<td width='76'><c:if test="${notifyPageNo > 1}">

						<a
							href="<c:url value='notify.jsp?notifyPageNo=${notifyPageNo-1}' />">Previous page</a>&nbsp;&nbsp;&nbsp;
									
									</c:if></td>
				<td width='76'><c:if test="${notifyPageNo != totalPages}">
						<a
							href="<c:url value='notify.jsp?notifyPageNo=${notifyPageNo+1}' />">Next page</a>&nbsp;&nbsp;&nbsp;
									</c:if></td>
				<td width='76'><c:if test="${notifyPageNo != totalPages}">
						<a href="<c:url value='notify.jsp?notifyPageNo=${totalPages}' />">Last page</a>&nbsp;&nbsp;&nbsp;
									</c:if></td>
				<td width='176' align="center">${notifyPageNo} page / Total ${totalPages} pages</td>
			</tr>

		</table>
	</c:if>
	</div>
	</div>
	</div>

<jsp:include page="/fragment/newfooter.jsp"/>
	</section>
	<script>
		$('.sm-parallax-window3').parallax({
			imageSrc : '${pageContext.request.contextPath}/image/together.jpg'

		});
	</script>
	
</body>
		
</html>