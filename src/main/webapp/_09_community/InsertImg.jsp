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
	var file;
	var fileReader;

	function doFirst() {
		document.getElementById('myFile').onchange = fileChange;
		//document.....與畫面產生關聯  onchange 建立事件聆聽
		//change 是file的常用事件

	}

	function fileChange() {
		file = document.getElementById('myFile').files[0];
		//檔案讀取 以陣列模式讀取  此題只有一個檔案 所以取陣列0
		fileReader = new FileReader();
		//要使用FileReader()的屬性與方法 必須宣告為FileReader
		fileReader.readAsDataURL(file);
		//FileReader的方法readAsText()
		fileReader.onload = function() {
			var image = document.getElementById('image');
			image.src = fileReader.result;
			//將fileReader.result得到的資料 傳回image.src內
			image.style.width = '150px';
			image.style.height = '150px';

		}

	}

	window.addEventListener('load', doFirst, false);
</script>
<c:if
	test="${empty param.holderID or empty LoginOK or LoginOK.userID != param.holderID or empty param.from}">
	<c:redirect url="/_09_community/HolderList.jsp" />
</c:if>

</head>
<body>
	<jsp:include page="/fragment/header.jsp" />
	<section class="sm-parallax-window3"
		style=" background-color:rgba(0,0,0,0.4); padding:0 0; margin:0 0;">
	<div class="container" style="font-family: 微軟正黑體; text-align: center;">
		<div class="row">
			<jsp:useBean id="MangeHolder"
				class="_09_community.model.HolderManagementDAO" />

			<jsp:setProperty property="holderID" name="MangeHolder"
				value="${LoginOK.userID}" />
			<c:if test="${empty MangeHolder.holder}">
				<c:redirect url="/_09_community/HolderList.jsp" />
			</c:if>

			<form ENCTYPE="multipart/form-data" method="POST"
				action="<c:url value='/_09_community/InsertPic.do' />">
				<input type="hidden" name="from" value="${param.from}">
				<c:choose>
					<c:when test="${param.from eq 'ENV' }">
						<title>Upload Environment Photo</title>

						<div class="col-md-12">
							<h1 style="text-align: center; font-size: 40px; color: #fff;">
								<strong>Upload Environment Photo</strong>
							</h1>
						</div>
						<c:choose>
							<c:when test="${MangeHolder.envImgRecordCounts ge 5}">
								<c:redirect url="/_09_community/HolderList.jsp" />
							</c:when>
							<c:otherwise>
								<div class="col-md-12" style="text-align: center;"
									align="center">
									<table class="table"
										style="margin-top: 20px; margin-bottom: 20px; text-align: center; position: relative;">
										<tr align="center">
											<td style="border: none;">
												<div
													style="vertical-align: middle; margin-top: 20px; margin-bottom: 20px;">
													<img id="image" height='150px' width='150px'
														src='${pageContext.servletContext.contextPath}/image/defaultImg.jpg' />
												</div>
												<div>
													<label style="color: #fff; font-size: 22px;">Upload</label>
												</div>
												<div>
													<Input Type="file" size="40" id="myFile"
														style="text-align: center; font-size: 22px; width: 400px; margin-top: 20px; margin-bottom: 20px;"
														name="environment" class="btn btn-warning"
														accept="image/*"> <font color="red" size="-1"
														style="background-color: #fff; font-size: 18px;">${MsgMap.errorPicSize}</font>
												</div>
												<div>
													<label
														style="color: #fff; font-size: 22px; margin-top: 20px; margin-bottom: 20px;">Title(10 characters limit)</label>
													<input type="text" name="environmentTitle"
														value="${param.environmentTitle}"> <br>
													<font color="red" size="-1"
														style="background-color: #fff; font-size: 18px;">${MsgMap.errorPicTitle}</font>
												</div>
											</td>
										</tr>
									</table>
								</div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:when test="${param.from eq 'FOOD' }">
						<title>Upload Food Photo</title>
						<div class="col-md-12">
							<h1 style="text-align: center; font-size: 40px; color: #fff;">
								<strong>Upload Food Photo</strong>
							</h1>
						</div>
						<c:choose>
							<c:when test="${MangeHolder.foodImgRecordCounts ge 5}">
								<c:redirect url="/_09_community/HolderList.jsp" />
							</c:when>
							<c:otherwise>
								<div class="col-md-12" style="text-align: center;"
									align="center">
									<table class="table"
										style="margin-top: 20px; margin-bottom: 20px; text-align: center; position: relative;">
										<tr align="center">
											<td style="border: none;">
												<div
													style="vertical-align: middle; margin-top: 20px; margin-bottom: 20px;">
													<img id="image" height='150px' width='150px'
														src='${pageContext.servletContext.contextPath}/image/defaultImg.jpg' />
												</div>
												<div>
													<label style="color: #fff; font-size: 22px;">Upload</label>
												</div>
												<div>
													<Input Type="file" size="40" id="myFile"
														class="btn btn-warning"
														style="text-align: center; font-size: 22px; width: 400px; margin-top: 20px; margin-bottom: 20px;"
														name="food" accept="image/*"> <font color="red"
														size="-1" color="red" size="-1"
														style="background-color: #fff; font-size: 18px;">${MsgMap.errorPicSize}</font>
												</div>
												<div>
													<label
														style="color: #fff; font-size: 22px; margin-top: 20px; margin-bottom: 20px;">Title(10 characters limit):
													</label> <input type="text" name="foodTitle"
														value="${param.environmentTitle}"> <br> <font
														color="red" size="-1" color="red" size="-1"
														style="background-color: #fff; font-size: 18px;">${MsgMap.errorPicTitle}</font>
												</div>
											</td>
										</tr>
									</table>
								</div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<c:redirect url="/_09_community/HolderList.jsp" />
					</c:otherwise>
				</c:choose>
				<div class="col-md-12">
					<input type="submit" name="submit" class="btn btn-warning"
						style="margin-top: 10px; font-size: 22px; text-align: center; margin-top: 20px; margin-bottom: 20px;"
						id="submit" value="Submit" />
				</div>
			</form>
			<div class="col-md-12">
				<Input type="button" name="delete" value="Back to My HOST"
					class="btn btn-success"
					style="margin-top: 10px; font-size: 22px; text-align: center; margin-top: 20px; margin-bottom: 20px;"
					onClick="location.href='<c:url value='HolderDetail.jsp?holderID=${param.holderID}'/>'">
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