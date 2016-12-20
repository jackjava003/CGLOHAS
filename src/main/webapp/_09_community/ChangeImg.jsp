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
<c:if
	test="${empty param.holderID or empty LoginOK or LoginOK.userID != param.holderID or empty param.imgID or empty param.from}">
	<c:redirect url="/_09_community/HolderList.jsp" />
</c:if>

</head>
<body>
	<jsp:include page="/fragment/header.jsp" />
	<jsp:useBean id="MangeHolder"
		class="_09_community.model.HolderManagementDAO" />

	<jsp:setProperty property="holderID" name="MangeHolder"
		value="${LoginOK.userID}" />
	<c:if test="${empty MangeHolder.holder}">
		<c:redirect url="/_09_community/HolderList.jsp" />
	</c:if>


	<section class="sm-parallax-window3"
		style=" background-color:rgba(0,0,0,0.4); padding:0 0; margin:0 0;">
	<div class="container" style="font-family: 微軟正黑體; text-align: center;">
		<div class="row">
			<div class="col-md-12" align="center">
				<form ENCTYPE="multipart/form-data" method="POST"
					action="<c:url value='/_09_community/UpdatePic.do' />">
					<c:choose>
						<c:when test="${param.from eq 'ENV'}">
							<title>C.G.LOHAS</title>
							<h1 style="text-align: center; font-size: 40px; color: #fff;">
								<strong>Replace environment picture</strong>
							</h1>
							<input type="hidden" name="from" value="ENV">
							<input type="hidden" name="picID" value="${param.imgID}">
							<jsp:setProperty property="imageID" name="MangeHolder"
								value="${param.imgID}" />
							<c:set var="imgBean" value="${MangeHolder.singlePic}" />
							<c:if test="${empty imgBean || !imgBean.type eq 'ENV'}">
								<c:redirect url="/_09_community/HolderList.jsp" />
							</c:if>
							<c:if test="${!empty imgBean}">
								<table class="table">
									<tr align="center">
										<td style="border: none;">
											<div>
												<img height='150px' width='150px'
													src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${imgBean.image_id}&type=holderEnvPic' />
											</div>
											<div>
												<label style="font-size: 22px; color: #fff;">Picture</label>
											</div>
										</td>
									</tr>
									<tr align="center">
										<td style="border: none;">
											<div>
												<div style="margin-top: 20px; margin-bottom: 20px;">
													<Input Type="file" size="40"
														style="font-size: 22px; color: #fff; width:400px;"
														class="fieldWidth btn btn-warning" 
														name="environment" accept="image/*"> 
														<font
														color="red" size="-1" style="background-color: #fff; font-size: 18px;">${MsgMap.errorPicSize}</font>
												</div>
												<c:choose>
													<c:when test="${!empty param.environmentTitle }">
														<c:set var="info" value="${param.environmentTitle}" />
													</c:when>
													<c:otherwise>
														<c:set var="info" value="${imgBean.imageInfo}" />
													</c:otherwise>
												</c:choose>
												<div style="margin-top: 20px; margin-bottom: 20px;">
													<label style="font-size: 22px; color: #fff;">Image Title (10 characters limit)
													</label> <input type="text" style="font-size: 22px;" name="environmentTitle" value="${info}"><br>
													<font color="red" size="-1"
														style="background-color: #fff; font-size: 18px;">${MsgMap.errorPicTitle}</font>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
						</c:when>

						<c:when test="${param.from eq 'FOOD'}">
							<title>C.G.LOHAS</title>
							<h1 style="text-align: center; font-size: 40px; color: #fff;">
								<strong>Replace food picture</strong>
							</h1>
							<input type="hidden" name="from" value="FOOD">
							<input type="hidden" name="picID" value="${param.imgID}">
							<jsp:setProperty property="imageID" name="MangeHolder"
								value="${param.imgID}" />
							<c:set var="imgBean" value="${MangeHolder.singlePic}" />
							<c:if test="${empty imgBean || !imgBean.type eq 'FOOD'}">
								<c:redirect url="/_09_community/HolderList.jsp" />
							</c:if>
							<c:if test="${!empty imgBean}">
								<table class="table">
									<tr align="center">
										<td style="border: none;">
											<div>
												<img height='150px' width='150px'
													src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${imgBean.image_id}&type=holderFoodPic' />
											</div>
											<div>
												<label style="font-size: 22px; color: #fff;">Picture</label>
											</div>
										</td>
									</tr>
									<tr align="center">
										<td style="border: none;">
											<div>
												<div style=" margin-top:20px; margin-bottom:20px;">
													<<Input Type="file" size="40"
														style="font-size: 22px; color: #fff; width:400px;"
														class="fieldWidth btn btn-warning"  name="food"
														accept="image/*">
														 <font color="red" size="-1" style="background-color: #fff; font-size: 18px;">${MsgMap.errorPicSize}</font>
												</div>
												<c:choose>
													<c:when test="${!empty param.foodTitle }">
														<c:set var="info" value="${param.foodTitle}" />
													</c:when>
													<c:otherwise>
														<c:set var="info" value="${imgBean.imageInfo}" />
													</c:otherwise>
												</c:choose>
												<div style=" margin-top:20px; margin-bottom:20px;">
													<label style="font-size: 22px; color: #fff;">Image Title (10 characters limit)</label> <input type="text"  style="font-size: 22px;"
														name="foodTitle" value="${info}" >
														<br>
														<font color="red"
														size="-1" style="background-color: #fff; font-size: 18px; margin-top:10px; margin-bottom:10px;">${MsgMap.errorPicTitle}</font>
												</div>
											</div>
										</td>
									</tr>

								</table>
							</c:if>
						</c:when>

					</c:choose>
					<input type="submit" name="submit" id="submit" value="Submit"
						class="btn btn-warning" style="font-size: 22px;" />
				</form>

			</div>
			<div class="col-md-12" align="center">
				<Input type="button" name="delete" value="Back to HOST"
					class="btn btn-success"
					style="margin-top: 10px; font-size: 22px; text-align: center; margin-top: 20px; margin-bottom: 20px;"
					onClick="location.href='<c:url value='HolderDetail.jsp?holderID=${param.holderID}'/>'">


				<!-- 				<a -->
				<%-- 					href='<c:url value="HolderDetail.jsp?holderID=${param.holderID}"/>'>回我的開伙</a> --%>

			</div>

		</div>
	</div>

	<jsp:include page="/fragment/newfooter.jsp" /> </section>
	<script>
		$('.sm-parallax-window3').parallax({
			imageSrc : '${pageContext.request.contextPath}/image/s4.jpg'

		});
	</script>
</body>
</html>