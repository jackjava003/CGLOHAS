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
	test="${empty param.holderID or empty LoginOK or LoginOK.userID != param.joinerID}">
	<c:redirect url="/_09_community/HolderList.jsp" />
</c:if>
<script
	src="${pageContext.request.contextPath}/javascript/jquery-1.12.4.min.js"></script>
<title>C.G.LOHAS</title>
</head>
<jsp:useBean id="JoinBean" class="_09_community.model.JoinDAO" />
<jsp:setProperty name="JoinBean" property="joinerID"
	value="${LoginOK.userID}" />
<jsp:setProperty name="JoinBean" property="holderID"
	value="${param.holderID}" />

<c:if test="${empty JoinBean.joinerMessage.contact}">
	<c:redirect url="/_09_community/HolderList.jsp" />
</c:if>

<c:set var="JoinerBean" value="${JoinBean.joinerMessage}" />
<script src='https://www.google.com/recaptcha/api.js'></script>
<body>
	<jsp:include page="/fragment/header.jsp" />
	<section class="sm-parallax-window3"
		style=" background-color:rgba(0,0,0,0.4);padding:0 0; margin:0 0;">
	<div class="container"
		style="padding-top: 50px; margin-bottom: 350px; font-family: 微軟正黑體;">
		<div class="row">
			<div id="wrapper" class="applyIHave">
				<div class="content">
					<div class="col-md-12" style="text-align: center;">
						<h1
							style="font-size: 40px; text-shadow: 1px 1px 8px #000; color: #fff;">
							<strong>Modify join detail</strong>
						</h1>
					</div>
						<div class="col-md-12" style="text-align: center; text-shadow: 1px 1px 8px #000; color: #fff;">
						<h3><strong>Join detail</strong></h3>
						</div>
						
						
						<div class="col-me-12">
					<form method="post"
						action="<c:url value='/_09_community/JoinMessageServlet.do'/>">

							
							<table class="table table-hover" width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:20px; border-radius:10px; box-shadow: 10px 10px 5px #888888; background-color:rgba(255,255,255,0.9)">
								<tr>
									<td style="border:none;"><input type="hidden" name="holderID"
										value="${JoinerBean.holderID}"></td>
								</tr>
								<tr>
									<c:choose>
										<c:when test="${!empty param.startDate}">
											<c:set var="date" value="${param.startDate}" />
										</c:when>
										<c:otherwise>
											<c:set var="date" value="${JoinerBean.startDate}" />
										</c:otherwise>
									</c:choose>

									<th style="border:none;">Expected date</th>
									<td><input type="date" name="startDate" size="10"
										value="${date}"> <font color="red" size="-1">${MsgMap.errorStartDate}</font></td>
								</tr>
								<tr>
									<c:choose>
										<c:when test="${!empty paramValues.favourType}">
											<c:forEach var="favType" items="${paramValues.favourType}">
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
										</c:when>

										<c:otherwise>
											<c:forEach var="favType" items="${JoinerBean.favourTypeArr}">
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
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${!empty param.favour_other}">
											<c:set var="other" value="${param.favour_other }" />
										</c:when>
										<c:otherwise>
											<c:set var="other" value="${JoinerBean.favour_other}" />
										</c:otherwise>
									</c:choose>
									<th style="border:none;">Favorite food(Multiple)</th>
									<td><input type="checkbox" name="favourType" value="中式正餐"
										${fav1 } /> Chinese-style <input type="checkbox" name="favourType"
										value="西式正餐" ${fav2 } /> Western-style <input type="checkbox"
										name="favourType" value="甜點" ${fav3 } /> Dessert <input
										type="checkbox" name="favourType" value="麵包" ${fav4 } /> Bread <input
										type="checkbox" name="favourType" value="湯品" ${fav5 } /> Soup <input
										type="checkbox" name="favourType" value="手作小食" ${fav6 } />
										Hand-made snacks<br /> <input type="checkbox" name="favourType"
										value="其他" ${fav7 } />Other <label for="textfield"></label> <input
										name="favour_other" type="text" size="40" value="${other}" /><font
										color="red" size="-1">${MsgMap.errorFavour_other}</font> <font
										color="red" size="-1">${MsgMap.errorFavourType}</font></td>
								</tr>

								<c:choose>
									<c:when test="${!empty paramValues.weekday}">
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
									</c:when>
									<c:otherwise>
										<c:forEach var="cookDay" items="${JoinerBean.avlDayArr}">
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
									</c:otherwise>
								</c:choose>
								<tr>
									<th nowrap="nowrap" style="border:none;">Expected day(Multiple)</th>
									<td><input type="checkbox" name="weekday" value="星期一"
										${day1} /> Mon <input type="checkbox" name="weekday"
										value="星期二" ${day2} /> Tue <input type="checkbox"
										name="weekday" value="星期三" ${day3} /> Wed <input
										type="checkbox" name="weekday" value="星期四" ${day4} /> Thu <input
										type="checkbox" name="weekday" value="星期五" ${day5} /> Fri <input
										type="checkbox" name="weekday" value="星期六" ${day6} /> Sat <input
										type="checkbox" name="weekday" value="星期日" ${day7} /> Sun <font
										color="red" size="-1">${MsgMap.errorWeekday}</font></td>
								</tr>
								<c:choose>
									<c:when test="${!empty paramValues.times}">
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
									</c:when>
									<c:otherwise>
										<c:forEach var="cookTime" items="${JoinerBean.avlTimeArr}">
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
									</c:otherwise>
								</c:choose>
								<tr>
									<th nowrap="nowrap" style="border:none;">Expected time</th>
									<td><input type="checkbox" name="times" value="晚餐"
										${cookTime1} /> Dinner <input type="checkbox" name="times"
										value="中餐" ${cookTime2} /> Lunch <input type="checkbox"
										name="times" value="下午茶" ${cookTime3} /> Afternoon tea <input
										type="checkbox" name="times" value="其他" ${cookTime4} /> Other <font
										color="red" size="-1">${MsgMap.errorTimes}</font></td>
								</tr>
								<c:choose>
									<c:when test="${!empty param.pplNums}">
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
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${JoinerBean.pplNeed eq '1人'}">
												<c:set var="ppl1" value="checked" />
											</c:when>
											<c:when test="${JoinerBean.pplNeed  eq '2人'}">
												<c:set var="ppl2" value="checked" />
											</c:when>
											<c:when test="${JoinerBean.pplNeed  eq '3人'}">
												<c:set var="ppl3" value="checked" />
											</c:when>
											<c:when test="${JoinerBean.pplNeed  eq '4人'}">
												<c:set var="ppl4" value="checked" />
											</c:when>
											<c:when test="${JoinerBean.pplNeed  eq '5人'}">
												<c:set var="ppl5" value="checked" />
											</c:when>
											<c:when test="${JoinerBean.pplNeed  eq '6人以上'}">
												<c:set var="ppl6" value="checked" />
											</c:when>
										</c:choose>
									</c:otherwise>
								</c:choose>

								<tr>
									<th nowrap="nowrap" style="border:none;">Number of people</th>
									<td><input type="radio" name="pplNums" id="nums_1"
										value="1人" ${ppl1} /> 1 person <input type="radio" name="pplNums"
										id="nums_2" value="2人" ${ppl2} /> 2 people <input type="radio"
										name="pplNums" id="nums_3" value="3人" ${ppl3} /> 3 people <input
										type="radio" name="pplNums" id="nums_4" value="4人" ${ppl4} />
										4 people <input type="radio" name="pplNums" id="nums_5" value="5人"
										${ppl5} /> 5 people <input type="radio" name="pplNums" id="nums_6"
										value="6人以上" ${ppl6} /> More than 6 people <font color="red" size="-1">${MsgMap.errorPplNums}</font></td>
								</tr>
								<c:choose>
									<c:when test="${!empty param.vegan}">
										<c:choose>
											<c:when test="${param.vegan eq '是'}">
												<c:set var="veg1" value="checked" />
											</c:when>
											<c:when test="${param.vegan eq '否'}">
												<c:set var="veg2" value="checked" />
											</c:when>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${JoinerBean.vegetarian eq '是'}">
												<c:set var="veg1" value="checked" />
											</c:when>
											<c:when test="${JoinerBean.vegetarian eq '否'}">
												<c:set var="veg2" value="checked" />
											</c:when>
										</c:choose>
									</c:otherwise>
								</c:choose>
								<tr>
									<th style="border:none;">Vegetarian</th>
									<td><input type="radio" name="vegan" value="是" ${veg1} />
										Vegetarian <input type="radio" name="vegan" value="否" ${veg2} /> non-Vegetarian<font
										color="red" size="-1">${MsgMap.errorVegan}</font></td>
								</tr>

								<c:choose>
									<c:when test="${!empty param.contact}">
										<c:set var="tel" value="${param.contact}" />
									</c:when>
									<c:otherwise>
										<c:set var="tel" value="${JoinerBean.contact}" />
									</c:otherwise>
								</c:choose>

								<tr>
									<th style="border:none;">Contact detail</th>
									<td><input type="text" name="contact" style="width: 65%"
										maxlength="300" value="${tel}"><font color="red"
										size="-1">${MsgMap.errorContact}</font> <br />
										Such as：Line：jack@line.com / Mobile：0928317335</td>
								</tr>

								<c:choose>
									<c:when test="${!empty param.message}">
										<c:set var="mesag" value="${param.message}" />
									</c:when>
									<c:otherwise>
										<c:set var="mesag" value="${JoinerBean.otherNeedTextArea}" />
									</c:otherwise>
								</c:choose>

								<tr>
									<th style="border:none;">Other need (50 characters limit)</th>
									<td><textarea name="message" cols="65" rows="2"
											maxlength='50'> ${mesag}</textarea></td>
								</tr>
								<tr>
									<th style="border:none;"></th>
									<td><div class="g-recaptcha"
											data-sitekey="6LcLfQcUAAAAACCrN4x3uNDWUi-zoiJLyco9BnfN"></div>
										<font color="red" size="-1">${MsgMap.errorRecaptcha}</font></td>
								</tr>

							</table>
							
							<div class="col-md-12" style="text-align:center;">
							<p class="btn">
								<input type="hidden" name="UpdateJoiner" value="UpdateJoiner" />
								<input type="submit" name="submit" id="submit" value="Submit" class="btn btn-warning" style="font-size:22px" />
							</p>
							</div>
					</form>
					</div>
					</div>
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