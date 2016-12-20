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
	test="${empty param.holderID or empty LoginOK or LoginOK.userID != param.holderID}">
	<c:redirect url="/_09_community/HolderList.jsp" />
</c:if>
<script
	src="${pageContext.request.contextPath}/javascript/jquery-1.12.4.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/javascript/address.js"></script>
<title>C.G.LOHAS</title>
</head>

<jsp:include page="/fragment/header.jsp" />
<jsp:useBean id="MangeHolder"
	class="_09_community.model.HolderManagementDAO" />

<jsp:setProperty property="holderID" name="MangeHolder"
	value="${LoginOK.userID}" />
<c:if test="${empty MangeHolder.holder}">
	<c:redirect url="/_09_community/HolderList.jsp" />
</c:if>

<c:set var="HolderBean" value="${MangeHolder.holder}" />
<body class="member">

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
							<strong>Modify my HOST</strong>
						</h1>
					</div>
					<form method="post"
						action="<c:url value='/_09_community/UpdateHolderInfoServlet.do'/>">

						<div class="col-md-8" style="vertical-align: middle;">
							<h3
								style="text-shadow: 1px 1px 8px #000; color: #fff; text-align: center;">
								<strong>HOST Detail</strong>
							</h3>
							<table class="table" style="text-align: left;">
								<tr>
									<c:choose>
										<c:when test="${!empty param.test}">
											<c:set var="city" value="${param.test}" />
										</c:when>
										<c:otherwise>
											<c:set var="city" value="${HolderBean.city}" />
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${!empty param.test2}">
											<c:set var="area" value="${param.test2}" />
										</c:when>
										<c:otherwise>
											<c:set var="area" value="${HolderBean.area}" />
										</c:otherwise>
									</c:choose>
									<th nowrap="nowrap"
										style="border: none; font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Address</th>
									<td><input name="zipcode" id="zipcode" type="hidden"
										maxlength="4" size="4" /> <input name="test" id="test"
										type="hidden" value="${city}" /> <input name="test2"
										id="test2" type="hidden" value="${area}" /> <select
										name="zone1" id="zone1"></select> <select name="zone2"
										id="zone2"></select> <font color="red" size="-1">${MsgMap.errorArea}</font></td>
								</tr>
								<c:choose>
									<c:when test="${!empty param.address}">
										<c:set var="add" value="${param.address}" />
									</c:when>
									<c:otherwise>
										<c:set var="add" value="${HolderBean.location}" />
									</c:otherwise>
								</c:choose>
								<tr>
									<th
										style="border: none; font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">&nbsp;</th>
									<td><input name="address" id="address" type="text"
										required="required" size="60" value="${add}" /> <font
										color="red" size="-1">${MsgMap.errorAddress}</font><br></td>
								</tr>
								<tr>
									<th
										style="border: none; font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">&nbsp;</th>
									<td><font color="red" size="-1"
										style="background-color: #fff;">Location is necessary reference information, the address will not fully shown on the page</font></td>
								</tr>
								<tr>
									<c:choose>
										<c:when test="${!empty param.exp}">
											<c:choose>
												<c:when test="${param.exp eq '半年-1年'}">
													<c:set var="exp1" value="checked" />
												</c:when>
												<c:when test="${param.exp eq '1-2年'}">
													<c:set var="exp2" value="checked" />
												</c:when>
												<c:when test="${param.exp eq '2-3年'}">
													<c:set var="exp3" value="checked" />
												</c:when>
												<c:when test="${param.exp eq '4-5年'}">
													<c:set var="exp4" value="checked" />
												</c:when>
												<c:when test="${param.exp eq '5年以上'}">
													<c:set var="exp5" value="checked" />
												</c:when>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${HolderBean.cook_exp eq '半年-1年'}">
													<c:set var="exp1" value="checked" />
												</c:when>
												<c:when test="${HolderBean.cook_exp eq '1-2年'}">
													<c:set var="exp2" value="checked" />
												</c:when>
												<c:when test="${HolderBean.cook_exp eq '2-3年'}">
													<c:set var="exp3" value="checked" />
												</c:when>
												<c:when test="${HolderBean.cook_exp eq '4-5年'}">
													<c:set var="exp4" value="checked" />
												</c:when>
												<c:when test="${HolderBean.cook_exp eq '5年以上'}">
													<c:set var="exp5" value="checked" />
												</c:when>
											</c:choose>
										</c:otherwise>
									</c:choose>

									<th nowrap="nowrap"
										style="border: none; font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Cook experience</th>
									<td><strong
										style="font-size: 18px; text-shadow: 1px 1px 8px #000; color: #fff;">
											<input type="radio" name="exp" value="半年-1年" ${exp1} />
											6 months-1 year <input type="radio" name="exp" value="1-2年" ${exp2} />
											1-2 years <input type="radio" name="exp" value="2-3年" ${exp3} />
											2-3 years <input type="radio" name="exp" value="4-5年" ${exp4} />
											4-5 years <input type="radio" name="exp" value="5年以上" ${exp5} />
											above 5 years <font color="red" size="-1"
											style="background-color: #fff;">${MsgMap.errorExp}</font>
									</strong></td>
								</tr>
								<tr>
									<c:choose>
										<c:when test="${!empty paramValues.proType}">
											<c:forEach var="proType" items="${paramValues.proType}">
												<c:choose>
													<c:when test="${proType eq '中式正餐'}">
														<c:set var="pro1" value="checked" />
													</c:when>
													<c:when test="${proType eq '西式正餐'}">
														<c:set var="pro2" value="checked" />
													</c:when>
													<c:when test="${proType eq '甜點'}">
														<c:set var="pro3" value="checked" />
													</c:when>
													<c:when test="${proType eq '麵包'}">
														<c:set var="pro4" value="checked" />
													</c:when>
													<c:when test="${proType eq '湯品'}">
														<c:set var="pro5" value="checked" />
													</c:when>
													<c:when test="${proType eq '手作小食'}">
														<c:set var="pro6" value="checked" />
													</c:when>
													<c:when test="${proType eq '其他'}">
														<c:set var="pro7" value="checked" />
													</c:when>
												</c:choose>
											</c:forEach>
										</c:when>

										<c:otherwise>
											<c:forEach var="proType" items="${HolderBean.proTypeArr}">
												<c:choose>
													<c:when test="${proType eq '中式正餐'}">
														<c:set var="pro1" value="checked" />
													</c:when>
													<c:when test="${proType eq '西式正餐'}">
														<c:set var="pro2" value="checked" />
													</c:when>
													<c:when test="${proType eq '甜點'}">
														<c:set var="pro3" value="checked" />
													</c:when>
													<c:when test="${proType eq '麵包'}">
														<c:set var="pro4" value="checked" />
													</c:when>
													<c:when test="${proType eq '湯品'}">
														<c:set var="pro5" value="checked" />
													</c:when>
													<c:when test="${proType eq '手作小食'}">
														<c:set var="pro6" value="checked" />
													</c:when>
													<c:when test="${proType eq '其他'}">
														<c:set var="pro7" value="checked" />
													</c:when>
												</c:choose>

											</c:forEach>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${!empty param.pro_other}">
											<c:set var="other" value="${param.pro_other }" />
										</c:when>
										<c:otherwise>
											<c:set var="other" value="${HolderBean.pro_other}" />
										</c:otherwise>
									</c:choose>
									<th nowrap="nowrap"
										style="border: none; font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Expert in..(can be multiple)</th>
									<td><strong
										style="font-size: 18px; text-shadow: 1px 1px 8px #000; color: #fff;"><input
											type="checkbox" name="proType" value="中式正餐" ${pro1 } /> Chinese-style
											<input type="checkbox" name="proType" value="西式正餐" ${pro2 } />
											Western-style <input type="checkbox" name="proType" value="甜點"
											${pro3 } /> Dessert <input type="checkbox" name="proType"
											value="麵包" ${pro4 } /> Bread <input type="checkbox"
											name="proType" value="湯品" ${pro5 } /> Soup <input
											type="checkbox" name="proType" value="手作小食" ${pro6 } /> Hand-made snacks<br>
											<input type="checkbox" name="proType" value="其他" ${pro7 } />Other</strong>
										<label for="textfield"></label> <input name="pro_other"
										type="text" size="40" value="${other}" /> <strong
										style="background-color: #fff;"> <font color="red"
											size="-1">${MsgMap.errorPro_other}</font> <font color="red"
											size="-1">${MsgMap.errorProType}</font>
									</strong></td>

								</tr>

								<c:choose>
									<c:when test="${!empty param.foodIntro}">
										<c:set var="foodintro" value="${param.foodIntro}" />
									</c:when>
									<c:otherwise>
										<c:set var="foodintro" value="${HolderBean.foodIntroTextArea}" />
									</c:otherwise>
								</c:choose>

								<tr>
									<th nowrap="nowrap"
										style="border: none; font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Features of the cuisine intro</th>
									<td><textarea name="foodIntro" cols="66" rows="5"
											placeholder="限100字">${foodintro}</textarea> <font color="red"
										size="-1">${MsgMap.errorFoodIntro}</font></td>
								</tr>
								<c:choose>
									<c:when test="${!empty paramValues.weekday}">
										<c:forEach var="cookDay" items="${paramValues.weekday}">
											<c:choose>
												<c:when test="${cookDay eq '星期一'}">
													<c:set var="cookDay1" value="checked" />
												</c:when>
												<c:when test="${cookDay eq '星期二'}">
													<c:set var="cookDay2" value="checked" />
												</c:when>
												<c:when test="${cookDay eq '星期三'}">
													<c:set var="cookDay3" value="checked" />
												</c:when>
												<c:when test="${cookDay eq '星期四'}">
													<c:set var="cookDay4" value="checked" />
												</c:when>
												<c:when test="${cookDay eq '星期五'}">
													<c:set var="cookDay5" value="checked" />
												</c:when>
												<c:when test="${cookDay eq '星期六'}">
													<c:set var="cookDay6" value="checked" />
												</c:when>
												<c:when test="${cookDay eq '星期日'}">
													<c:set var="cookDay7" value="checked" />
												</c:when>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:forEach var="cookDay" items="${HolderBean.cookDayArr}">
											<c:choose>
												<c:when test="${cookDay eq '星期一'}">
													<c:set var="cookDay1" value="checked" />
												</c:when>
												<c:when test="${cookDay eq '星期二'}">
													<c:set var="cookDay2" value="checked" />
												</c:when>
												<c:when test="${cookDay eq '星期三'}">
													<c:set var="cookDay3" value="checked" />
												</c:when>
												<c:when test="${cookDay eq '星期四'}">
													<c:set var="cookDay4" value="checked" />
												</c:when>
												<c:when test="${cookDay eq '星期五'}">
													<c:set var="cookDay5" value="checked" />
												</c:when>
												<c:when test="${cookDay eq '星期六'}">
													<c:set var="cookDay6" value="checked" />
												</c:when>
												<c:when test="${cookDay eq '星期日'}">
													<c:set var="cookDay7" value="checked" />
												</c:when>
											</c:choose>

										</c:forEach>
									</c:otherwise>
								</c:choose>
								<tr>
									<th nowrap="nowrap"
										style="border: none; font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Available day(can be multiple)</th>
									<td><strong
										style="font-size: 18px; text-shadow: 1px 1px 8px #000; color: #fff;"><input
											type="checkbox" name="weekday" value="星期一" ${cookDay1} />
											Mon <input type="checkbox" name="weekday" value="星期二"
											${cookDay2} /> Tue <input type="checkbox" name="weekday"
											value="星期三" ${cookDay3} /> Wed <input type="checkbox"
											name="weekday" value="星期四" ${cookDay4} /> Thu <input
											type="checkbox" name="weekday" value="星期五" ${cookDay5} />
											Fri <input type="checkbox" name="weekday" value="星期六"
											${cookDay6} /> Sat <input type="checkbox" name="weekday"
											value="星期日" ${cookDay7} /> Sun </strong> <strong
										style="background-color: #fff;"><font color="red"
											size="-1">${MsgMap.errorWeekday}</font></strong></td>
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
										<c:forEach var="cookTime" items="${HolderBean.cookTimeArr}">
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
									<th nowrap="nowrap"
										style="border: none; font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Available time</th>
									<td><strong
										style="font-size: 18px; text-shadow: 1px 1px 8px #000; color: #fff;"><input
											type="checkbox" name="times" value="晚餐" ${cookTime1} /> Dinner <input
											type="checkbox" name="times" value="中餐" ${cookTime2} /> Lunch <input
											type="checkbox" name="times" value="下午茶" ${cookTime3} /> Afternoon tea
											<input type="checkbox" name="times" value="其他" ${cookTime4} />
											Other </strong> <strong style="background-color: #fff;"> <font
											color="red" size="-1">${MsgMap.errorTimes}</font>
									</strong></td>
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
											<c:when test="${HolderBean.limitno eq '1人'}">
												<c:set var="ppl1" value="checked" />
											</c:when>
											<c:when test="${HolderBean.limitno eq '2人'}">
												<c:set var="ppl2" value="checked" />
											</c:when>
											<c:when test="${HolderBean.limitno eq '3人'}">
												<c:set var="ppl3" value="checked" />
											</c:when>
											<c:when test="${HolderBean.limitno eq '4人'}">
												<c:set var="ppl4" value="checked" />
											</c:when>
											<c:when test="${HolderBean.limitno eq '5人'}">
												<c:set var="ppl5" value="checked" />
											</c:when>
											<c:when test="${HolderBean.limitno eq '6人以上'}">
												<c:set var="ppl6" value="checked" />
											</c:when>
										</c:choose>
									</c:otherwise>
								</c:choose>

								<tr>
									<th nowrap="nowrap"
										style="border: none; font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Maximum customers</th>
									<td><strong
										style="font-size: 18px; text-shadow: 1px 1px 8px #000; color: #fff;">
											<input type="radio" name="pplNums" id="nums_1" value="1人"
											${ppl1} /> 1 person <input type="radio" name="pplNums" id="nums_2"
											value="2人" ${ppl2} /> 2 people <input type="radio" name="pplNums"
											id="nums_3" value="3人" ${ppl3} /> 3 people <input type="radio"
											name="pplNums" id="nums_4" value="4人" ${ppl4} /> 4 people <input
											type="radio" name="pplNums" id="nums_5" value="5人" ${ppl5} />
											5 people <input type="radio" name="pplNums" id="nums_6"
											value="6人以上" ${ppl6} /> More than 6 people
									</strong> <strong style="background-color: #fff;"> <font
											color="red" size="-1">${MsgMap.errorPplNums}</font>
									</strong></td>

								</tr>
								<c:choose>
									<c:when test="${!empty param.vegan}">
										<c:choose>
											<c:when test="${param.vegan eq '提供素食'}">
												<c:set var="veg1" value="checked" />
											</c:when>
											<c:when test="${param.vegan eq '無提供素食'}">
												<c:set var="veg2" value="checked" />
											</c:when>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${HolderBean.vegetarian eq '提供素食'}">
												<c:set var="veg1" value="checked" />
											</c:when>
											<c:when test="${HolderBean.vegetarian eq '無提供素食'}">
												<c:set var="veg2" value="checked" />
											</c:when>
										</c:choose>
									</c:otherwise>
								</c:choose>
								<tr>
									<th nowrap="nowrap"
										style="border: none; font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Vegetarian</th>
									<td><strong
										style="font-size: 18px; text-shadow: 1px 1px 8px #000; color: #fff;"><input
											type="radio" name="vegan" value="提供素食" ${veg1} /> Available <input
											type="radio" name="vegan" value="無提供素食" ${veg2} /> Unavailable </strong> <strong
										style="background-color: #fff;"> <font color="red"
											size="-1">${MsgMap.errorVegan}</font>
									</strong></td>
								</tr>
								<c:choose>
									<c:when test="${!empty param.price}">
										<c:choose>
											<c:when test="${param.price eq '50元以下'}">
												<c:set var="price1" value="checked" />
											</c:when>
											<c:when test="${param.price eq '50-100元'}">
												<c:set var="price2" value="checked" />
											</c:when>
											<c:when test="${param.price eq '100-150元'}">
												<c:set var="price3" value="checked" />
											</c:when>
											<c:when test="${param.price eq '150-200元'}">
												<c:set var="price4" value="checked" />
											</c:when>
											<c:when test="${param.price eq '200元以上'}">
												<c:set var="price5" value="checked" />
											</c:when>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${HolderBean.price eq '50元以下'}">
												<c:set var="price1" value="checked" />
											</c:when>
											<c:when test="${HolderBean.price eq '50-100元'}">
												<c:set var="price2" value="checked" />
											</c:when>
											<c:when test="${HolderBean.price eq '100-150元'}">
												<c:set var="price3" value="checked" />
											</c:when>
											<c:when test="${HolderBean.price eq '150-200元'}">
												<c:set var="price4" value="checked" />
											</c:when>
											<c:when test="${HolderBean.price eq '200元以上'}">
												<c:set var="price5" value="checked" />
											</c:when>

										</c:choose>
									</c:otherwise>
								</c:choose>
								<tr>
									<th nowrap="nowrap"
										style="border: none; font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">Each price</th>
									<td><strong
										style="font-size: 18px; text-shadow: 1px 1px 8px #000; color: #fff;">
											<input type="radio" name="price" value="50元以下" ${price1} />
											Under 50 NTD <input type="radio" name="price" value="50-100元"
											${price2} /> 50-100 <input type="radio" name="price"
											value="100-150元" ${price3} /> 100-150 <input type="radio"
											name="price" value="150-200元" ${price4} /> 150-200 <input
											type="radio" name="price" value="200元以上" ${price5} /> Above 200 NTD
									</strong> <strong style="background-color: #fff;"> <font
											color="red" size="-1">${MsgMap.errorPrice}</font>
									</strong></td>

								</tr>
								<c:choose>
									<c:when test="${!empty param.status}">
										<c:choose>
											<c:when test="${param.status eq '歡迎搭伙'}">
												<c:set var="open1" value="checked" />
											</c:when>
											<c:when test="${param.status eq '目前額滿'}">
												<c:set var="open2" value="checked" />
											</c:when>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${HolderBean.open_condition eq '歡迎搭伙'}">
												<c:set var="open1" value="checked" />
											</c:when>
											<c:when test="${HolderBean.open_condition eq '目前額滿'}">
												<c:set var="open2" value="checked" />
											</c:when>
										</c:choose>
									</c:otherwise>
								</c:choose>
								<tr>
									<th nowrap="nowrap"
										style="border: none; font-size: 22px; text-shadow: 1px 1px 8px #000; color: #fff;">HOST Status</th>
									<td><strong
										style="font-size: 18px; text-shadow: 1px 1px 8px #000; color: #fff;">
											<input type="radio" name="status" value="歡迎搭伙" ${open1} />
											Available <input type="radio" name="status" value="目前額滿" ${open2} />
											Unavailable
									</strong> <strong style="background-color: #fff;"> <font
											color="red" size="-1">${MsgMap.errorStatus}</font>
									</strong></td>
								</tr>
							</table>
						</div>
						<c:choose>
							<c:when test="${!empty param.selfIntro}">
								<c:set var="selfIntro" value="${param.selfIntro}" />
							</c:when>
							<c:otherwise>
								<c:set var="selfIntro" value="${HolderBean.selfInfoTextArea}" />
							</c:otherwise>
						</c:choose>

						<div class="col-md-4"
							style="vertical-align: middle; text-align: center;">
							<h3
								style="text-shadow: 1px 1px 8px #000; color: #fff; text-align: center;">
								<strong>我的介紹</strong>
							</h3>

							<p>
								<textarea name="selfIntro" cols="50" rows="8"
									placeholder="限100字">${selfIntro}</textarea>
								<font color="red" size="-1">${MsgMap.errorSelfIntro }</font>
							</p>
						</div>
						<div class="col-md-12"
							style="vertical-align: middle; text-align: center;">
							<p class="btn">
								<input type="hidden" name="UpdateInfo" value="UpdateInfo" /> <input
									type="submit" name="submit" id="submit" class="btn btn-warning"
									style="font-size: 22px; text-align: center;" value="Submit" />
								<button onclick="back()" class="btn btn-warning" style="font-size: 22px; margin-left:10px;" >Back to my HOST</button>
							</p>
						</div>
					</form>
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