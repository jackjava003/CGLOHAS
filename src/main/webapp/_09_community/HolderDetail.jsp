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
<c:if test="${empty param.holderID}">
	<c:redirect url="/_09_community/HolderList.jsp" />
</c:if>
<script type="text/javascript">
// 	function showDetail() {
// 		document.getElementById('intro').style.cssText = 'display: block;';
// 		document.getElementById('envPic').style.cssText = 'display: none;';
// 		document.getElementById('foodPic').style.cssText = 'display: none;';
// 		document.getElementById('JoinMessage').style.cssText = 'display: none;';
// 		document.getElementById('MessageArea').style.cssText = 'display: none;';

	
	function confirmDelete(n , x) {
		if (confirm("Delete this message ? ") ) {
			document.forms[0].action="<c:url value='DeleteJoiner.do?joinerID=" + n +"&holderID="+x+"' />" ;
			document.forms[0].method="POST";
			document.forms[0].submit();
		} else {
		
		}
	}
	
	function confirmMessageDelete(n ,z, x) {
		if (confirm("Delete this message ? ") ) {
			document.forms[0].action="<c:url value='DeleteMessage.do?userID=" + n +"&from=user&holderID="+z+"&messageID="+x+"' />" ;
			document.forms[0].method="POST";
			document.forms[0].submit();
		} else {
		
		}
	}
	
	function confirmReplyDelete(n ,z, x) {
		if (confirm("Delete this reply message ? ") ) {
			document.forms[0].action="<c:url value='DeleteMessage.do?replierID=" + n +"&from=replier&holderID="+z+"&messageID="+x+"' />" ;
			document.forms[0].method="POST";
			document.forms[0].submit();
		} else {
		
		}
	}
	
	 var CaptchaCallback = function() {
	        grecaptcha.render('RecaptchaField2', {'sitekey' : '6LcLfQcUAAAAACCrN4x3uNDWUi-zoiJLyco9BnfN'});
	        grecaptcha.render('RecaptchaField1', {'sitekey' : '6LcLfQcUAAAAACCrN4x3uNDWUi-zoiJLyco9BnfN'});
	    };
</script>

<title>C.G.LOHAS</title>
</head>
<body>
	<form></form>
	<jsp:include page="/fragment/header.jsp" />
	<jsp:useBean id="MangeHolder"
		class="_09_community.model.HolderManagementDAO" />

	<jsp:setProperty property="holderID" name="MangeHolder"
		value="${param.holderID}" />
	<c:if test="${empty MangeHolder.holder}">
		<c:redirect url="/_09_community/HolderList.jsp" />
	</c:if>



	<section class="sm-parallax-window3"
		style=" background-color:rgba(0,0,0,0.4);padding:0 0; margin:0 0;">
	<div class="container"
		style="padding-top: 50px; margin-bottom: 350px; font-family: 微軟正黑體;">
		<div class="row">
			<div align="center" style="padding-bottom: 50px;">
				<c:if test="${param.message eq 'existed'}">
					<font
						style="font-size: 40px; text-shadow: 1px 1px 8px #000; color: #fff;"><strong>HOST already exist, Please directly modify</strong></font>
				</c:if>
			</div>
			<jsp:useBean id="holderName" class="_01_register.model.register_DAO" />
			<jsp:setProperty property="selectID" name="holderName"
				value="${param.holderID}" />
			<div class="col-md-4" style="text-align: center;">
				<div align="center">
					<div style="float: bottom; margin-bottom: 50px;">
						<div>
							<img height='300px' width='300px'
								src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${param.holderID}&type=userImg' /><br>
							<%-- 							${holderName.select.name} --%>
						</div>
					</div>
				</div>
				<div
					style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff; border-radius: 10px;">
					<dl>
						<dt></dt>
						<dd>
							<img height='30px' width='30px' style="padding-bottom: 5px;"
								src='${pageContext.request.contextPath}/image/png/like.png' />Like
							: <strong>${holderName.select.like_count}</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<img height='30px' width='30px'
								src='${pageContext.request.contextPath}/image/png/dislike.png' />Dislike
							: <strong>${holderName.select.dislike_count}</strong>
						</dd>
						<dd>
							<c:if test="${LoginOK.verified eq 'F' and LoginOK.userID != param.holderID}">
								<strong style="text-shadow: none; background-color: #fff;"><font
									color="red">After verify your account you can evaluate</font></strong>
							</c:if>
							<c:if test="${!empty LoginOK and LoginOK.verified eq 'T'}">
								<jsp:useBean id="postLikeDAO"
									class="_10_postLike.model.PostLikeDAO" />
								<jsp:setProperty property="holderID" name="postLikeDAO"
									value="${param.holderID}" />
								<jsp:setProperty property="posterID" name="postLikeDAO"
									value="${LoginOK.userID}" />
								<c:choose>
									<c:when
										test="${(LoginOK.userID != MangeHolder.holder.userID) and postLikeDAO.checkLike == 0}">
										<form method="POST"
											action="<c:url value='/_10_postLike/InsertPostLike.do'/>">
											<input type="hidden" name="holderID"
												value="${param.holderID}"> <input type="radio"
												name="recommend" value="LIKE" />Like <input type="radio"
												name="recommend" value="DISLIKE" />Dislike <font color="black"
												style="background-color: #fff;" size="-1">${errorLike}</font>
											<input type="submit" name="button" class="btn btn-warning"
												id="button" style="font-size: 20px;" value="Submit" />
										</form>
									</c:when>
									<c:when
										test="${(LoginOK.userID != MangeHolder.holder.userID) and postLikeDAO.checkLike > 0}">
										<input type="button" name="button" class="btn btn-warning"
											onclick="location.href='<c:url value="/_10_postLike/DeletePostLike.do?holderID=${param.holderID}"/>'"
											value="Cancel my evaluation" style="font-size: 20px;" />
									</c:when>
								</c:choose>
							</c:if>

						</dd>
						<dd>Area： ${MangeHolder.holder.city} /
							${MangeHolder.holder.area}</dd>
						<dd>Price： ${MangeHolder.holder.engPrice}</dd>
						<dd>
							Status：<strong>${MangeHolder.holder.engOpen_condition}</strong>
						</dd>
						<dd>Address：
							${MangeHolder.holder.city}${MangeHolder.holder.area}${MangeHolder.holder.hideAddress}
						</dd>
					</dl>
				</div>
			</div>



			<!-- 				<div align="center"> -->
			<!-- 					<div style="display: inline-block;"> -->
			<!-- 						<button onclick="showDetail();" class="btn btn-success">我的介紹</button> -->
			<!-- 					</div> -->
			<!-- 					<div style="display: inline-block;"> -->
			<!-- 						<button onclick="showEnvPic();" class="btn btn-success">廚房環境</button> -->
			<!-- 					</div> -->
			<!-- 					<div style="display: inline-block;"> -->
			<!-- 						<button onclick="showFoodPic();" class="btn btn-success">餐點照片</button> -->
			<!-- 					</div> -->
			<!-- 					<div class="message" style="display: inline-block;"> -->
			<!-- 						<button onclick="showJoinMessage();" class="btn btn-success">留言搭伙</button> -->
			<!-- 					</div> -->
			<!-- 					<div class="message" style="display: inline-block;"> -->
			<!-- 						<button onclick="showMessage();" class="btn btn-success">問與答</button> -->
			<!-- 					</div> -->
			<!-- 				</div> -->


			<div class="col-md-8">

				<div role="tabpanel">

					<!-- Nav tabs -->
					<ul class="nav nav-tabs" role="tablist"
						style="font-size: 22px; color: orange;">
						<li role="presentation" class="active"><a
							style="font-size: 22px; color: orange;" href="#home"
							aria-controls="home" role="tab" data-toggle="tab"><strong>Self intro</strong></a></li>
						<li role="presentation"><a href="#profile"
							style="font-size: 22px; color: orange;" aria-controls="profile"
							role="tab" data-toggle="tab"><strong>Environment</strong></a></li>
						<li role="presentation"><a href="#messages"
							style="font-size: 22px; color: orange;" aria-controls="messages"
							role="tab" data-toggle="tab"><strong>Food</strong></a></li>
						<li role="presentation"><a href="#settings"
							style="font-size: 22px; color: orange;" aria-controls="settings"
							role="tab" data-toggle="tab"><strong>Join this HOST</strong></a></li>
						<li role="presentation"><a href="#bbb"
							style="font-size: 22px; color: orange;" aria-controls="settings1"
							role="tab" data-toggle="tab"><strong>Ask message</strong></a></li>
					</ul>

					<!-- Tab panes -->
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active" id="home">
							<div id="intro"
								style="margin-top: 10px; font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff; display: block;">
								<table class="table  holder-boder1" align="center">
									<tr>
										<th style="border: none; width:150px;" align="left" >Cook experience</th>
										<td style="border: none;">${MangeHolder.holder.engCook_exp}</td>
									</tr>
									<tr>
										<th style="border: none;" align="left">Expert in..</th>
										<td >${MangeHolder.holder.proTypeString}</td>
									</tr>
									<c:if test="${not empty MangeHolder.holder.pro_other}">
										<tr style="border: none;" align="left">
											<th style="border: none;">Expert in other</th>
											<td >${MangeHolder.holder.pro_other}</td>
										</tr>
									</c:if>
									<tr style="border: none;">
										<th style="border: none;" align="left">Features of the cuisine intro</th>
										<td>${MangeHolder.holder.foodIntro}</td>
									</tr>
									<tr>
										<th style="border: none;" align="left">Available day</th>
										<td >${MangeHolder.holder.cookDayString}</td>
									</tr>
									<tr>
										<th style="border: none;" align="left">Available time</th>
										<td >${MangeHolder.holder.cookTimeString}</td>
									</tr>
									<tr>
										<th style="border: none;" align="left">Maximum customers</th>
										<td >${MangeHolder.holder.engLimitno}</td>
									</tr>
									<tr>
										<th style="border: none;" align="left">Vegetarian</th>
										<td >${MangeHolder.holder.engVegetarian}</td>
									</tr>
									<tr>
										<th style="border: none;" align="left">Each price</th>
										<td >${MangeHolder.holder.engPrice}</td>
									</tr>
									<tr>
										<th style="border: none;" align="left">Self Intro</th>
										<td >${MangeHolder.holder.selfInfo}</td>
									</tr>
								</table>
							</div>
						</div>
						<div role="tabpanel" class="tab-pane" id="profile"
							style="margin-top: 20px; font-size: 22px;">
							<div id="envPic" align="center">
								<c:forEach var="envId" items="${MangeHolder.envImageId}">
									<div>
										<c:forEach var="Object" items="${envId}" varStatus="varSt">
											<c:if test="${varSt.count == 1 }">
												<img height='200px' width='200px'
													src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${Object}&type=holderEnvPic' />
											</c:if>
											<c:if test="${varSt.count == 2 }">
												<div
													style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">${Object}</div>
											</c:if>
										</c:forEach>
									</div>
								</c:forEach>
								<c:if test="${LoginOK.userID eq MangeHolder.holder.userID }">
									<input type="button" class="btn btn-warning"
										style="font-size: 20px;" value="Edit environment photo"
										onclick="location.href='<c:url value="ShowEnvPic.jsp?holderID=${MangeHolder.holder.userID}&from=ENV"/>'">
								</c:if>
							</div>
						</div>
						<div role="tabpanel" class="tab-pane" id="messages"
							style="margin-top: 20px; font-size: 20px;">
							<div id="foodPic" align="center">
								<c:forEach var="foodId" items="${MangeHolder.foodImageId}">
									<div>
										<c:forEach var="foodObject" items="${foodId}"
											varStatus="varStFood">
											<c:if test="${varStFood.count == 1 }">
												<img height='200px' width='200px'
													src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${foodObject}&type=holderFoodPic' />
											</c:if>
											<c:if test="${varStFood.count == 2 }">
												<div
													style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">${foodObject}</div>
											</c:if>
										</c:forEach>
									</div>
								</c:forEach>
								<c:if test="${LoginOK.userID eq MangeHolder.holder.userID }">

									<input type="button" class="btn btn-warning"
										style="font-size: 20px;" value="Edit food photo"
										onclick="location.href='<c:url value="ShowEnvPic.jsp?holderID=${MangeHolder.holder.userID}&from=FOOD"/>'">

								</c:if>
							</div>
						</div>
						<div role="tabpanel" class="tab-pane" id="settings"
							style="margin-top: 10px; font-size: 20px;">
							<div id="JoinMessage" align="center">
								<c:choose>
									<c:when test="${empty LoginOK}">
										<p>
											請<a div
												style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;"
												href="<c:url value='/_02_login/login.jsp?' />"> Login </a>or<a
												div
												style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;"
												href="<c:url value='/_00_register/register.jsp' />"> Register
											</a>
										</p>
									</c:when>
									<c:when test="${LoginOK.verified eq 'F' and LoginOK.userID != param.holderID}">
										<p style="margin-top: 30px; font-size: 22px;">
											<strong style="color: #fff; text-shadow: 1px 1px 8px #000;">Please check your verification letter in your mailbox or </strong>
											<strong style="background-color: #fff;"> <a
												href="<c:url value='/_00_register/reVerified.jsp' />">
													Reverify </a></strong>
										</p>
									</c:when>
									<c:when test="${LoginOK.userID eq MangeHolder.holder.userID }">
										<c:choose>
											<c:when test="${empty param.JoinMessagePageNo}">
												<c:set var="JoinMessagePageNo" value="1" />
											</c:when>
											<c:otherwise>
												<c:set var="JoinMessagePageNo"
													value="${param.JoinMessagePageNo}" />
											</c:otherwise>
										</c:choose>
										<jsp:useBean id="PageMessageBean"
											class="_09_community.model.JoinDAO" />
										<jsp:setProperty name="PageMessageBean" property="holderID"
											value="${param.holderID}" />
										<c:set var="totalPages" value="${PageMessageBean.totalPages}" />
										<jsp:setProperty property="pageNo" name="PageMessageBean"
											value="${JoinMessagePageNo}" />
										<jsp:setProperty name="PageMessageBean" property="holderID"
											value="${MangeHolder.holder.userID}" />
										<c:choose>
											<c:when test="${empty PageMessageBean.pageJoiner}">

												<div align="center"
													style="margin-top: 30px; font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff; display: block;">
													<strong>Currently no user join this HOST</strong>
												</div>
											</c:when>
											<c:otherwise>
												<c:forEach var="PageJoin"
													items="${PageMessageBean.pageJoiner}">
													<table class="table">
														<tr>
															<td style="border: none;" width='150'>
																<div style="float: left;">
																	<div style="margin-left: 30px; margin-right: 30px;">
																		<img height='150' width='150'
																			src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${PageJoin.joinerID}&type=userImg' />
																	</div>
																</div>
															</td>
															<td style="border: none;">
																<div align="left">
																	<jsp:useBean id="userName"
																		class="_01_register.model.register_DAO" />
																	<jsp:setProperty property="selectID" name="userName"
																		value="${PageJoin.joinerID}" />
																	<div
																		style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">
																		<dl
																			style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">
																			<dd
																				style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Name：${userName.select.name}</dd>
																			<dd>Expected date：${PageJoin.startDate}</dd>
																			<dd>Favorite food：${PageJoin.favourTypeString}</dd>
																			<dd>Expected day： ${PageJoin.avlDayString}</dd>
																			<dd>Expected time：${PageJoin.avlTimeString}</dd>
																			<dd>Number of people：${PageJoin.engPplNeed}</dd>
																			<dd>Vegetarian：${PageJoin.engVegetarian}</dd>
																			<dd>Contact detail：${PageJoin.contact}</dd>
																			<dd>Other need：${PageJoin.other_need}</dd>
																			<dd>Application date：${PageJoin.createRealTime}</dd>
																		</dl>
																		
																	</div>
																</div>
															</td>
														</tr>
													</table>

												</c:forEach>


												<table class="table table-hover" align="center">
													<tr>
														<td width='76'><c:if test="${JoinMessagePageNo > 1}">

																<a
																	href="<c:url value='HolderDetail.jsp?JoinMessagePageNo=1&JoinMessage=1&holderID=${MangeHolder.holder.userID}'/>">First Page</a>&nbsp;&nbsp;&nbsp;
									
									</c:if></td>
														<td width='76'><c:if test="${JoinMessagePageNo > 1}">

																<a
																	href="<c:url value='HolderDetail.jsp?JoinMessagePageNo=${JoinMessagePageNo-1}&JoinMessage=1&holderID=${MangeHolder.holder.userID}' />">Previous page</a>&nbsp;&nbsp;&nbsp;
									
									</c:if></td>
														<td width='76'><c:if
																test="${JoinMessagePageNo != totalPages}">
																<a
																	href="<c:url value='HolderDetail.jsp?JoinMessagePageNo=${JoinMessagePageNo+1}&JoinMessage=1&holderID=${MangeHolder.holder.userID}' />">Next page</a>&nbsp;&nbsp;&nbsp;
									</c:if></td>
														<td width='76'><c:if
																test="${JoinMessagePageNo != totalPages}">
																<a
																	href="<c:url value='HolderDetail.jsp?JoinMessagePageNo=${totalPages}&JoinMessage=1&holderID=${MangeHolder.holder.userID}' />">Last page</a>&nbsp;&nbsp;&nbsp;
									</c:if></td>
														<td width='176' align="center" style="color: #000;">${JoinMessagePageNo} page / Total ${totalPages} pages</td>
													</tr>
												</table>
												<br>
												<br>
												<br>

												<div align="center">
													<a href="<c:url value='../index.jsp' />"><input
														type="button" class="btn btn-warning" value="Home page"
														style="font-size: 22px;"></a>
												</div>
											</c:otherwise>
										</c:choose>


									</c:when>
									<c:otherwise>
										<jsp:useBean id="JoinBean" class="_09_community.model.JoinDAO" />
										<jsp:setProperty name="JoinBean" property="joinerID"
											value="${LoginOK.userID}" />
										<jsp:setProperty name="JoinBean" property="holderID"
											value="${MangeHolder.holder.userID}" />
										<c:set var="Join" value="${JoinBean.joinerMessage}" />
										<c:choose>
											<c:when test="${!empty Join.contact}">
												<jsp:useBean id="user"
													class="_01_register.model.register_DAO" />
												<jsp:setProperty property="selectID" name="user"
													value="${Join.joinerID}" />
												<table class="table " style="color: #fff; font-size:22px;'">
													<tr>
														<th style="border: none;">Name</th>
														<td style="border: none;">${user.select.name}</td>
													</tr>
													<tr>
														<th style="border: none;">Expected date</th>
														<td >${Join.startDate}</td>
													</tr>
													<tr>
														<th style="border: none;">Favorite food</th>
														<td >${Join.favourTypeString}</td>
													</tr>
													<tr>
														<th style="border: none;">Expected day</th>
														<td >${Join.avlDayString}</td>
													</tr>
													<tr>
														<th style="border: none;">Expected time</th>
														<td >${Join.avlTimeString}</td>
													</tr>
													<tr>
														<th style="border: none;">Number of people</th>
														<td >${Join.engPplNeed}</font></td>
													</tr>
													<tr>
														<th style="border: none;">Vegetarian</th>
														<td >${Join.engVegetarian}</td>
													</tr>
													<tr>
														<th style="border: none;">Contact detail</th>
														<td >${Join.contact}</td>
													</tr>
													<tr>
														<th style="border: none;">Other need</th>
														<td >${Join.other_need}</td>
													</tr>
												</table>
												<div style="margin-top: 10px;">
													<Input type="button" name="delete" value="Modify"
														class="btn btn-warning"
														style="font-size: 22px; margin-right: 10px;"
														onClick="location.href='<c:url value="UpdateForJoiner.jsp?holderID=${Join.holderID}&joinerID=${Join.joinerID}"/>'">

													<Input type="button" name="delete" value="Delete"
														class="btn btn-warning" style="font-size: 22px;"
														onClick="confirmDelete(${Join.joinerID},${Join.holderID})">
												</div>


											</c:when>
											<c:otherwise>

												<form method="post"
													action="<c:url value='/_09_community/JoinMessageServlet.do'/>">
													<table class="table ">
														<tr
															style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">
															<td style="border: none;"><div
																	style="border: none; width: 150px;">
																	<strong>Name</strong>
																</div></td>
															<td style="border: none;">${LoginOK.name}</td>
															<td style="border: none;"><input type="hidden"
																name="holderID" value="${param.holderID}"></td>
														</tr>
														<tr>
															<td style="border: none; width: 200px; display: inline;"><strong
																style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Expected<br>&nbsp;date
															</strong></td>
															<td><input type="date" name="startDate" size="10"
																value="${param.startDate}"> <font color="red"
																size="-1">${MsgMap.errorStartDate}</font></td>
														</tr>
														<c:if test="${!empty paramValues.favourType}">
															<c:forEach var="favType"
																items="${paramValues.favourType}">
																<c:choose>
																	<c:when test="${favType eq '中式正餐'}">
																		<c:set var="fav1" value="checked" />
																	</c:when>
																	<c:when test="${favType eq '西式正餐'}">
																		<c:set var="fav2" value="checked" />
																	</c:when>
																	<c:when test="${favType eq '甜點'}">
																		<c:set var="fav3" value="checked" />
																	</c:when>
																	<c:when test="${favType eq '麵包'}">
																		<c:set var="fav4" value="checked" />
																	</c:when>
																	<c:when test="${favType eq '湯品'}">
																		<c:set var="fav5" value="checked" />
																	</c:when>
																	<c:when test="${favType eq '手作小食'}">
																		<c:set var="fav6" value="checked" />
																	</c:when>
																	<c:when test="${favType eq '其他'}">
																		<c:set var="fav7" value="checked" />
																	</c:when>
																</c:choose>
															</c:forEach>
														</c:if>
														<tr>
															<td style="border: none; width: 200px; display: inline;"><strong
																style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Favorite food<br>&nbsp;(Multiple)
															</strong></td>
															<td style="">
																<div
																	style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">

																	<input type="checkbox" name="favourType" value="中式正餐"
																		${fav1 } /> Chinese-style <input type="checkbox"
																		name="favourType" value="西式正餐" ${fav2 } /> Western-style <input
																		type="checkbox" name="favourType" value="甜點" ${fav3 } />
																	Dessert <input type="checkbox" name="favourType" value="麵包"
																		${fav4 } /> Bread <input type="checkbox"
																		name="favourType" value="湯品" ${fav5 } /> Soup <input
																		type="checkbox" name="favourType" value="手作小食"
																		${fav6 } /> Hand-made snacks<br /> <input type="checkbox"
																		name="favourType" value="其他" ${fav7 } />Other <label
																		for="textfield"></label>
																</div> <input name="favour_other" type="text" size="40"
																value="${param.favour_other}" /><font color="red"
																size="-1">${MsgMap.errorFavour_other}</font> <font
																color="red" size="-1">${MsgMap.errorFavourType}</font>
															</td>

														</tr>
														<c:if test="${!empty paramValues.weekday}">
															<c:forEach var="cookDay" items="${paramValues.weekday}">
																<c:choose>
																	<c:when test="${cookDay eq '星期一'}">
																		<c:set var="day1" value="checked" />
																	</c:when>
																	<c:when test="${cookDay eq '星期二'}">
																		<c:set var="day2" value="checked" />
																	</c:when>
																	<c:when test="${cookDay eq '星期三'}">
																		<c:set var="day3" value="checked" />
																	</c:when>
																	<c:when test="${cookDay eq '星期四'}">
																		<c:set var="day4" value="checked" />
																	</c:when>
																	<c:when test="${cookDay eq '星期五'}">
																		<c:set var="day5" value="checked" />
																	</c:when>
																	<c:when test="${cookDay eq '星期六'}">
																		<c:set var="day6" value="checked" />
																	</c:when>
																	<c:when test="${cookDay eq '星期日'}">
																		<c:set var="day7" value="checked" />
																	</c:when>
																</c:choose>
															</c:forEach>
														</c:if>

														<tr>
															<td td
																style="border: none; width: 200px; display: inline;"><strong
																style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Expected day<br>&nbsp;(Multiple)
															</strong></td>
															<td
																style="font-size: border: none; 22 px; text-shadow: 1px 1px 8px #000; color: #fff;"><input
																type="checkbox" name="weekday" value="星期一" ${day1} />
																Mon <input type="checkbox" name="weekday" value="星期二"
																${day2} /> Tue <input type="checkbox" name="weekday"
																value="星期三" ${day3} /> Wed <input type="checkbox"
																name="weekday" value="星期四" ${day4} /> Thu <input
																type="checkbox" name="weekday" value="星期五" ${day5} />
																Fri <input type="checkbox" name="weekday" value="星期六"
																${day6} /> Sat <input type="checkbox" name="weekday"
																value="星期日" ${day7} /> Sun <font color="red" size="-1">${MsgMap.errorWeekday}</font></td>

														</tr>
														<c:if test="${!empty paramValues.times}">
															<c:forEach var="cookTime" items="${paramValues.times}">
																<c:choose>
																	<c:when test="${cookTime eq '晚餐'}">
																		<c:set var="cookTime1" value="checked" />
																	</c:when>
																	<c:when test="${cookTime eq '中餐'}">
																		<c:set var="cookTime2" value="checked" />
																	</c:when>
																	<c:when test="${cookTime eq '下午茶'}">
																		<c:set var="cookTime3" value="checked" />
																	</c:when>
																	<c:when test="${cookTime eq '其他'}">
																		<c:set var="cookTime4" value="checked" />
																	</c:when>

																</c:choose>
															</c:forEach>
														</c:if>
														<tr>
															<td style="border: none; width: 200px; display: inline;"><strong
																style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Expected time</strong></td>
															<td
																style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;"><input
																type="checkbox" name="times" value="晚餐" ${cookTime1} />
																Dinner <input type="checkbox" name="times" value="中餐"
																${cookTime2} /> Lunch <input type="checkbox" name="times"
																value="下午茶" ${cookTime3} /> Afternoon tea <input type="checkbox"
																name="times" value="其他" ${cookTime4} /> Other <font
																color="red" size="-1">${MsgMap.errorTimes}</font></td>

														</tr>
														<c:if test="${!empty param.pplNums}">
															<c:choose>
																<c:when test="${param.pplNums eq '1人'}">
																	<c:set var="ppl1" value="checked" />
																</c:when>
																<c:when test="${param.pplNums eq '2人'}">
																	<c:set var="ppl2" value="checked" />
																</c:when>
																<c:when test="${param.pplNums eq '3人'}">
																	<c:set var="ppl3" value="checked" />
																</c:when>
																<c:when test="${param.pplNums eq '4人'}">
																	<c:set var="ppl4" value="checked" />
																</c:when>
																<c:when test="${param.pplNums eq '5人'}">
																	<c:set var="ppl5" value="checked" />
																</c:when>
																<c:when test="${param.pplNums eq '6人以上'}">
																	<c:set var="ppl6" value="checked" />
																</c:when>
															</c:choose>
														</c:if>
														<tr>
															<td style="border: none; width: 200px; display: inline;"><strong
																style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Number of people</strong></td>
															<td
																style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;"><input
																type="radio" name="pplNums" id="nums_1" value="1人"
																${ppl1} /> 1 person <input type="radio" name="pplNums"
																id="nums_2" value="2人" ${ppl2} /> 2 people <input
																type="radio" name="pplNums" id="nums_3" value="3人"
																${ppl3} /> 3 people <input type="radio" name="pplNums"
																id="nums_4" value="4人" ${ppl4} /> 4 people <input
																type="radio" name="pplNums" id="nums_5" value="5人"
																${ppl5} /> 5 people <input type="radio" name="pplNums"
																id="nums_6" value="6人以上" ${ppl6} /> More than 6 people <font
																color="red" size="-1">${MsgMap.errorPplNums}</font></td>

														</tr>
														<c:if test="${!empty param.vegan}">
															<c:choose>
																<c:when test="${param.vegan eq '是'}">
																	<c:set var="veg1" value="checked" />
																</c:when>
																<c:when test="${param.vegan eq '否'}">
																	<c:set var="veg2" value="checked" />
																</c:when>
															</c:choose>
														</c:if>
														<tr>
															<td style="border: none; width: 200px; display: inline;"><strong
																style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Vegetarian</strong></td>
															<td
																style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;"><input
																type="radio" name="vegan" value="是" ${veg1} /> Vegetarian <input
																type="radio" name="vegan" value="否" ${veg2} /> non-Vegetarian<font
																color="red" size="-1">${MsgMap.errorVegan}</font></td>

														</tr>
														<tr>
															<td style="border: none; width: 200px; display: inline;"><strong
																style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Contact detail</strong></td>
															<td style=""><input type="text" name="contact"
																style="width: 65%" maxlength="300"
																value="${param.contact}"><font color="red"
																size="-1">${MsgMap.errorContact}</font> <br />
																<div
																	style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Such as：Line：jack@line.com
																	/ Mobile：0928317335</div></td>

														</tr>
														<tr>
															<td style="border: none; width: 200px; display: inline;"><strong
																style="font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Other need<br>&nbsp;(50 characters limit)
															</strong></td>
															<td><textarea name="message" cols="65" rows="2"
																	maxlength='50'> ${param.message}</textarea> <font
																color="red" size="-1">${MsgMap.errorMessage}</font></td>

														</tr>
														<tr>
															<td style="border: none; width: 200px; display: inline;"></td>
															<td><div id="RecaptchaField1"
																	class="RecaptchaField1"></div> <font color="red"
																size="-1">${MsgMap.errorRecaptcha}</font></td>

														</tr>
													</table>
													<input type="submit" value="Submit" name="送出搭伙資訊"
														class="btn btn-warning" style="font-size: 22px;" />
												</form>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div role="tabpanel" class="tab-pane" id="bbb">
							<div id="MessageArea" align="center">
								<jsp:useBean id="messageDAO"
									class="_09_community.model.MessageDAO" />
								<jsp:setProperty property="holderID" name="messageDAO"
									value="${param.holderID}" />
								<c:choose>
									<c:when test="${empty param.MessagePageNo}">
										<c:set var="MessagePageNo" value="1" />
									</c:when>
									<c:otherwise>
										<c:set var="MessagePageNo" value="${param.MessagePageNo}" />
									</c:otherwise>
								</c:choose>
								<c:set var="totalMessagePages" value="${messageDAO.totalPages}" />
								<jsp:setProperty property="pageNo" name="messageDAO"
									value="${MessagePageNo}" />
								<div align="center">
									<table class="table">
										<c:forEach var="PageMessage" items="${messageDAO.pageMessage}">
											<c:choose>
												<c:when
													test="${(PageMessage.hidden eq 'T') and (empty LoginOK or (LoginOK.userID != PageMessage.userID and LoginOK.userID != PageMessage.holderID)) }">
													<tr>
														<td>
															<div align="center"
																style="margin-top: 20px; font-size: 22px; text-shadow: 1px 1px 10px #000; color: #fff;">Private Message</div>
														</td>
													</tr>
												</c:when>

												<c:otherwise>
													<jsp:useBean id="messageUserName"
														class="_01_register.model.register_DAO" />
													<jsp:setProperty property="selectID" name="messageUserName"
														value="${PageMessage.userID}" />
													<tr>
														<td>
																<div style="margin-top: 20px;">
																<div style="float: left;">
																	<div
																		style="margin-right: 60px; text-align: center; margin-left: 60px; font-size: 22px; text-shadow: 1px 1px 10px #000; color: #fff;">
																		<img height='50' width='50'
																			style="font-size: 22px; text-shadow: 1px 1px 10px #000; color: #fff;"
																			src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${PageMessage.userID}&type=userImg' /><br>
																		${messageUserName.select.name }
																	</div>
																</div>
																<div
																	style="font-size: 22px; text-shadow: 1px 1px 10px #000; color: #fff;">
																	${PageMessage.message} 
																	<br>
								
																	${PageMessage.createRealTime}
																</div>
																</div>
																<c:if test="${PageMessage.userID == LoginOK.userID }">
																	<Input type="button" name="delete" value="Delete"
																		class="btn btn-warning" style="margin-top: 10px; font-size: 22px;""
																		onClick="confirmMessageDelete(${PageMessage.userID},${param.holderID},${PageMessage.messageID})">
																</c:if>



																<c:if test="${!empty PageMessage.replyMessage}">
																	<jsp:setProperty property="selectID"
																		name="messageUserName" value="${PageMessage.holderID}" />
																	<div style="margin-top: 40px; margin-bottom: 20px;">
																		<div style="float: left;">
																			<div
																				style="margin-right: 60px; text-align: center; margin-left: 60px; font-size: 22px; text-shadow: 1px 1px 10px #000; color: #fff;">
																				<img height='50' width='50' style="font-size: 22px; text-shadow: 1px 1px 10px #000; color: #fff;"
																					src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${PageMessage.holderID}&type=userImg' /><br>
																				${messageUserName.select.name }
																			</div>
																		</div>
																		<div
																			style=" padding-left: 40px; margin-top: 20px; font-size: 22px; text-shadow: 1px 1px 10px #000; color: #fff;">
																			${PageMessage.replyMessage} 
																			<br>
																			${PageMessage.replyRealTime}
																		</div>
																	</div>
						
														
																	<div align="center">
																		<c:if
																			test="${PageMessage.holderID == LoginOK.userID }">
																			<Input type="button" name="delete" value="Delete"
																				class="btn btn-warning"
																				style="font-size: 22px; margin-top: 40px; margin-bottom: 40px;"
																				onClick="confirmReplyDelete(${PageMessage.holderID},${param.holderID},${PageMessage.messageID})">
																		</c:if>
																	</div>
																	<div style="clear: both;"></div>
																</c:if>


																<c:if
																	test="${!empty LoginOK and empty PageMessage.replyMessage and LoginOK.userID == PageMessage.holderID }">
																	<form method="post"
																		action="<c:url value='/_09_community/MessageServlet.do'/>">
																		<input name="holderID" type="hidden"
																			value="${param.holderID}"> <input
																			name="messageID" type="hidden"
																			value="${PageMessage.messageID}"> <input
																			name="userID" type="hidden"
																			value="${PageMessage.userID}" /> <input
																			name="messageFrom" type="hidden" value="replyMessage">
																		<div align="center">
																		<textarea name="replyMessage" cols="" rows="2"
																			style="width: 700px; margin-top:10px;" maxlength='200'   >${param.replyMessage}</textarea>
																	
																			<input type="submit" name="button" id="button"
																				class="btn btn-warning"
																				style="font-size: 22px; align:center; margin-top:10px; margin-bottom:10px;"
																				value="Submit" />
																				<br>
																				<font color="red" size="-1" style="background-color:#fff;font-size: 22px; align:center;  margin-bottom:10px;">${MsgMap.errorreplyMessage}</font>
																		</div>
																		
																	</form>
																</c:if>
														</td>
													</tr>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</table>
								</div>


								<c:choose>
									<c:when test="${!empty messageDAO.pageMessage}">
										<table class="table table-hover"
											style="color: #fff; font-size: 22px;" align="center">
											<tr>
												<td width='76'><c:if test="${MessagePageNo > 1}">

														<a
															href="<c:url value='HolderDetail.jsp?MessagePageNo=1&Message=1&holderID=${MangeHolder.holder.userID}'/>">First Page</a>&nbsp;&nbsp;&nbsp;
										
									</c:if></td>
												<td width='76'><c:if test="${MessagePageNo > 1}">

														<a
															href="<c:url value='HolderDetail.jsp?MessagePageNo=${MessagePageNo-1}&Message=1&holderID=${MangeHolder.holder.userID}' />">Previous page</a>&nbsp;&nbsp;&nbsp;
									
									</c:if></td>
												<td width='76'><c:if
														test="${MessagePageNo != totalMessagePages}">

														<a
															href="<c:url value='HolderDetail.jsp?MessagePageNo=${MessagePageNo+1}&Message=1&holderID=${MangeHolder.holder.userID}' />">Next page</a>&nbsp;&nbsp;&nbsp;
									
									</c:if></td>
												<td width='76'><c:if
														test="${MessagePageNo != totalMessagePages}">

														<a
															href="<c:url value='HolderDetail.jsp?JoinMessagePageNo=${totalPages}&Message=1&holderID=${MangeHolder.holder.userID}' />">Last page</a>&nbsp;&nbsp;&nbsp;
										
									</c:if></td>
												<td width='176' align="center" style="color: #000;">${MessagePageNo} page / Total ${totalMessagePages} pages</td>
											</tr>
										</table>
									</c:when>
									<c:otherwise>

										<div>
											<strong
												style="margin-top: 30px; font-size: 22px; color: #fff; text-shadow: 1px 1px 10px #000; display: block; overflow: hidden;">Currently no user leave message</strong>
										</div>
									</c:otherwise>
								</c:choose>

								<dl>
									<c:choose>
										<c:when
											test="${!empty LoginOK and LoginOK.userID != param.holderID and LoginOK.verified eq 'T' }">
											<form method="post"
												action="<c:url value='/_09_community/MessageServlet.do'/>">
												<input name="holderID" type="hidden"
													value="${param.holderID}"> <input
													name="messageFrom" type="hidden" value="normalUser">

												<dt
													style="font-size: 22px; color: #fff; text-shadow: 1px 1px 10px #000;">Leave Message
													(200 characters limit)</dt>
												<dd>
													<textarea name="message" cols="" rows="5"
														style="width: 500px;" maxlength='200'>${param.message}</textarea>
													<font color="red" size="-1">${MsgMap.errorMessage}</font>
												</dd>
												<dd
													style="font-size: 22px; color: #fff; text-shadow: 1px 1px 10px #000;">

													<input type="checkbox" name="visibility" value="T"
														<c:if test='${!empty param.visibility}'>
                			  checked
             				  </c:if>>
													Private message
												</dd>
												<dd>
													<div id="RecaptchaField2"></div>
													<div id="test"></div>
													<font color="red" size="-1">${MsgMap.errorRecaptcha2}</font>
												</dd>
												<dd>
													<input type="submit" name="button" id="button" value="Submit"
														class="btn btn-warning"
														style="font-size: 22px; color: #fff;" />
												</dd>
											</form>
										</c:when>
										<c:when test="${empty LoginOK}"></c:when>
										<c:when test="${LoginOK.verified eq 'F' and LoginOK.userID != param.holderID}">
											<strong
												style="color: #fff; text-shadow: 1px 1px 8px #000; font-size: 22px">Please check your verification letter in your mailbox or </strong>
											<strong style="background-color: #fff; font-size: 22px;">
												<a href="<c:url value='/_00_register/reVerified.jsp' />">
													Reverify </a>
											</strong>
										</c:when>
									</c:choose>
								</dl>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-12"
				style="margin-top: 50px; text-align: center; font-size: 20px;">
				<c:if test="${LoginOK.userID == MangeHolder.holder.userID }">
					<input type="button" value="Modify activity" name="編輯我的搭伙"
						class="btn btn-success"
						style="margin-right: 10px; font-size: 20px;"
						onclick="location.href='<c:url value="UpdateForHolder.jsp?holderID=${MangeHolder.holder.userID}"/>'">
					<input type="button" value="Delete activity" name="刪除我的搭伙"
						class="btn btn-success" style="font-size: 20px;"
						onclick="location.href='<c:url value="DeleteHolderServlet.do?holderID=${MangeHolder.holder.userID}"/>'">
				</c:if>


			</div>
		</div>
	</div>
	<jsp:include page="/fragment/newfooter.jsp" /> </section>
	<c:if test="${param.from eq 'del'}">
		<script>
			alert("Delete success");
		</script>
	</c:if>

	<!-- 	必須全部load完才可執行此敘述 -->
	<c:if test="${JoinMessage == 1 or param.JoinMessage == 1}">
		<script>
		$('.nav-tabs a[href="#settings"]').tab('show');
		</script>
	</c:if>
	<c:if test="${Message == 1 or param.Message == 1}">
		<script>
		$('.nav-tabs a[href="#bbb"]').tab('show');
		</script>
	</c:if>

	<script
		src="https://www.google.com/recaptcha/api.js?onload=CaptchaCallback&render=explicit"
		async defer></script>
	<script>
		$('.sm-parallax-window3')
				.parallax(
						{
							imageSrc : '${pageContext.request.contextPath}/image/s4.jpg'
							
						});
	</script>
</body>
</html>