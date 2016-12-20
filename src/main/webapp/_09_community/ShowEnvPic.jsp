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
<script type="text/javascript">
	function confirmDelete(n,x) {
		if (confirm("Delete this photo ? ")) {
			document.forms[0].action = "<c:url value='DeleteImage.do?imageID="+ n + "&holderID="+x+"&from=ENV' />";
			document.forms[0].method = "POST";
			document.forms[0].submit();
		} else {

		}
	}
	function confirmDeletefood(n,x) {
		if (confirm("Delete this photo ? ")) {
			document.forms[0].action = "<c:url value='DeleteImage.do?imageID="+ n + "&holderID="+x+"&from=FOOD' />";
			document.forms[0].method = "POST";
			document.forms[0].submit();
		} else {

		}
	}
</script>
<c:if
	test="${empty param.holderID or empty LoginOK or LoginOK.userID != param.holderID or empty param.from}">
	<c:redirect url="/_09_community/HolderList.jsp" />
</c:if>
<title>Modify Photo</title>
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
			<div class="col-md-12">
				<c:choose>
					<c:when test="${param.from eq 'ENV' }">
						<h1 style="text-align: center; font-size: 40px; color: #fff;">
							<strong>Environment photo management</strong>
						</h1>
						<c:choose>
							<c:when test="${MangeHolder.envImgRecordCounts lt 5}">
								<div align="center">
									<Input type="button" name="delete" value="Upload new photo"
										class="btn btn-success"
										style="margin-top: 20px; margin-bottom: 20px; font-size: 22px;"
										onClick="location.href='<c:url value='/_09_community/InsertImg.jsp?holderID=${MangeHolder.holder.userID}&from=ENV' />'">
								</div>
							</c:when>
							<c:otherwise>
								<div align="center">
									<h4 style="color: #fff; margin-top: 20px; margin-bottom: 20px;">
										<strong>Reached upload limit (5 photo), please use modify feature</strong>
										<h4>
								</div>
							</c:otherwise>

						</c:choose>
						<div align="center">
							<table style="margin-top: 20px; margin-bottom: 20px;">
								<tr>
									<c:forEach var="envId" items="${MangeHolder.envImageId}"
										varStatus="OutVar">
										<div>
											<c:forEach var="Object" items="${envId}" varStatus="varSt">
												<td style="margin-left: 20px; margin-right: 20px;"><c:if
														test="${varSt.count == 1 }">
														<div>
															<c:set var="imgID" value="${Object}" />
															<img height='150px' width='150px'
																src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${imgID}&type=holderEnvPic' />
														</div>
													</c:if> <c:if test="${varSt.count == 2 }">
														<div>
															<h4 style="text-align: center; color: #fff;">Title:
																${Object}</h4>
														</div>
													</c:if>
											</c:forEach>
											<div style="text-align: center; color: #fff;">
												<c:if test="${OutVar.count != 1}">
													<div>
														<Input type="button" name="delete" value="Delete"
															class="btn btn-warning"
															onClick="confirmDelete(${imgID},${MangeHolder.holder.userID })">
													</div>
												</c:if>
												<Input type="button" name="delete" value="Modify"
													class="btn btn-success" style="margin-top: 10px;"
													onClick="location.href='<c:url value='/_09_community/ChangeImg.jsp?imgID=${imgID}&holderID=${MangeHolder.holder.userID}&from=ENV' />'">

											</div>
										</div>
										</td>
									</c:forEach>
								</tr>
							</table>
						</div>
					</c:when>
					<c:when test="${param.from eq 'FOOD' }">
						<h1 style="text-align: center; font-size: 40px; color: #fff;">
							<strong>Food photo management</strong>
						</h1>
						<c:choose>
							<c:when test="${MangeHolder.foodImgRecordCounts lt 5}">
								<div align="center">
									<Input type="button" name="delete" value="Upload new photo"
										class="btn btn-success"
										style="margin-top: 20px; margin-bottom: 20px; font-size: 22px;"
										onClick="location.href='<c:url value='/_09_community/InsertImg.jsp?holderID=${MangeHolder.holder.userID}&from=FOOD' />'">
								</div>
							</c:when>
							<c:otherwise>
								<div align="center">
									<h4 style="color: #fff; margin-top: 20px; margin-bottom: 20px;">
										<strong>Reached upload limit (5 photo), please use modify feature</strong>
										<h4>
								</div>
							</c:otherwise>

						</c:choose>
						<div align="center">
							<table style="margin-top: 20px; margin-bottom: 20px; text-align:center; position:relative;">
								<tr>
									<c:forEach var="envId" items="${MangeHolder.foodImageId}"
										varStatus="OutVar">
										<div style="margin-left: 10px; margin-right: 10px;">
											<c:forEach var="Object" items="${envId}" varStatus="varSt">
											<td style="margin-left: 20px; margin-right: 20px;">
												<c:if test="${varSt.count == 1 }">
														<div>
															<c:set var="imgID" value="${Object}" />
															<img height='150px' width='150px' 
																src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${imgID}&type=holderFoodPic' />
														</div>
												</c:if>
												<c:if test="${varSt.count == 2 }">
													<div>
														<h4 style="text-align: center; color: #fff;">Title:
															${Object}</h4>
													</div>
												</c:if>
											</c:forEach>
											<div>
												<c:if test="${OutVar.count != 1}">
													<div>
														<Input type="button" name="delete" value="Delete"
															class="btn btn-warning" style="margin-top: 10px;"
															onClick="confirmDeletefood(${imgID},${MangeHolder.holder.userID })">
													</div>
												</c:if>
												<Input type="button" name="delete" value="Modify"
													class="btn btn-success" style="margin-top: 10px;"
													onClick="location.href='<c:url value='/_09_community/ChangeImg.jsp?imgID=${imgID}&holderID=${MangeHolder.holder.userID}&from=FOOD' />'">
											</div>
										</div>
									</c:forEach>
								</tr>
							</table>
						</div>
					</c:when>
					<c:otherwise>
						<c:redirect url="/_09_community/HolderList.jsp" />
					</c:otherwise>
				</c:choose>
				<Input type="button" name="delete" value="Back to my HOST"
					class="btn btn-success"
					style="margin-top: 10px; font-size: 22px; text-align: center; margin-top: 20px; margin-bottom: 20px;"
					onClick="location.href='<c:url value='HolderDetail.jsp?holderID=${param.holderID}'/>'">


				<form action=""></form>
			</div>
		</div>
	</div>


	<jsp:include page="/fragment/newfooter.jsp" /> </section>
	<script>
		$('.sm-parallax-window3')
				.parallax(
						{
							imageSrc : '${pageContext.request.contextPath}/image/s4.jpg'
							
						});
	</script>

</body>
</html>