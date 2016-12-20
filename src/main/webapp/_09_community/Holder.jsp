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
<jsp:useBean id="MangeHolder"
	class="_09_community.model.HolderManagementDAO" />

<jsp:setProperty property="holderID" name="MangeHolder"
	value="${LoginOK.userID}" />
<c:if test="${!empty MangeHolder.holder}">
	<c:redirect
		url="/_09_community/HolderDetail.jsp?holderID=${LoginOK.userID}&message=existed" />
</c:if>
<c:if test="${LoginOK.verified eq 'F'}">
	<c:redirect url="/_00_register/reVerified.jsp?need=verified" />
</c:if>

<script
	src="${pageContext.request.contextPath}/javascript/jquery-1.12.4.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/javascript/address.js"></script>
<title>Become HOST</title>
<jsp:include page="/fragment/header.jsp" />


<style type="text/css">
.block {
	display: block;
}

form.cmxform label.error {
	display: none;
}
</style>
<meta name="keywords" content="Cooking,Lunch,Dinner" />
<meta name="description" content="C.G.LOHAS, letting you to share cooking and earn extra money" />
</head>

<body class="member" >
	${param.path}
	<section class="sm-parallax-window4" style="background-color:rgba(0,0,0,0.4);padding:0 0; margin:0 0;" >
	
	
	<div class="container" style="font-family:微軟正黑體;">
		<div class="row">
				<div id="wrapper" class="applyIHave">
					<div class="content">
						<div class="pageContent iHaveStep2">
							<form ENCTYPE="multipart/form-data" method="post"
								action="<c:url value='/_09_community/CreateHolderServlet.do'/>">
								<div class="col-md-12">
								<h1 style="text-align:center; font-size:40px; color:#fff;">
									<strong>Become HOST</strong>
								</h1>
								</div>			
								<div class="col-md-8">
								<h3 style="text-align:center; color:#fff;"><strong>HOST Detail</strong><strong style="font-size:18px;">(Required)</strong></h3>
								<table style="background-color:rgba(255,255,255,0.9); box-shadow: 10px 10px 5px #888888; border-radius:10px; " class="holder-table table table-hover" width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr style="border-radius:10px;" >
										<th style="border:none;" nowrap="nowrap">Address</th>
										<td style="border:none;"><input name="zipcode" id="zipcode" type="hidden"
											maxlength="4" size="4" /> <input id="test" type="hidden"
											value="${param.zone1}" /> <input id="test2" type="hidden"
											value="${param.zone2}" /> <select name="zone1" id="zone1"></select>
											<select name="zone2" id="zone2"></select> <font color="red"
											size="-1">${MsgMap.errorArea}</font></td>
									</tr>
									<tr>
										<th style="border:none;" style="border:none;">&nbsp;</th>
										<td style="border:none;"><input name="address" id="address" type="text"
											size="60" value="${param.address}" /> <font color="red"
											size="-1">${MsgMap.errorAddress}</font><br></td>
									</tr>
									<tr>
										<th style="border:none;">&nbsp;</th>
										<td style="border:none;"><font color="red" size="-1">Location is necessary reference information, the address will not fully shown on the page</font></td>
									</tr>
									<c:if test="${!empty param.exp}">
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
									</c:if>
									<tr>
										<th style="border:none;" nowrap="nowrap">Cook experience</th>
										<td style="border:none;"><input type="radio" name="exp" value="半年-1年" ${exp1} />
											6 months-1 year <input type="radio" name="exp" value="1-2年" ${exp2} />
											1-2 years<input type="radio" name="exp" value="2-3年" ${exp3} />
											2-3 years <input type="radio" name="exp" value="4-5年" ${exp4} />
											4-5 years <input type="radio" name="exp" value="5年以上" ${exp5} />
											above 5 years <font color="red" size="-1">${MsgMap.errorExp}</font></td>
									</tr>
									<c:if test="${!empty paramValues.proType}">
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
									</c:if>
									<tr>
										<th style="border:none;"nowrap="nowrap">Expert in..(can be multiple)</th>
										<td style="border:none;"><input type="checkbox" name="proType" value="中式正餐"
											${pro1} /> Chinese-style <input type="checkbox" name="proType"
											value="西式正餐" ${pro2} /> Western-style <input type="checkbox"
											name="proType" value="甜點" ${pro3} /> Dessert <input
											type="checkbox" name="proType" value="麵包" ${pro4} /> Bread <input
											type="checkbox" name="proType" value="湯品" ${pro5} /> Soup <input
											type="checkbox" name="proType" value="手作小食" ${pro6} /> Hand-made snacks<br />
											<input type="checkbox" name="proType" value="其他" ${pro7} />Other
											<label for="textfield"></label> <input name="pro_other"
											type="text" size="40" value="${param.pro_other}" /><font
											color="red" size="-1">${MsgMap.errorPro_other}</font> <font
											color="red" size="-1">${MsgMap.errorProType}</font></td>
									</tr>
									<tr>
										<th style="border:none;" nowrap="nowrap">Features of the cuisine intro</th>
										<td style="border:none;"><textarea name="foodIntro" cols="66" rows="5"
												placeholder="100 characters limit">${param.foodIntro}</textarea> <font
											color="red" size="-1">${MsgMap.errorFoodIntro}</font></td>
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
										<th style="border:none;" nowrap="nowrap">Available day(can be multiple)</th>
										<td style="border:none;"><input type="checkbox" name="weekday" value="星期一"
											${day1} /> Mon <input type="checkbox" name="weekday"
											value="星期二" ${day2} /> Tue <input type="checkbox"
											name="weekday" value="星期三" ${day3} /> Wed <input
											type="checkbox" name="weekday" value="星期四" ${day4} /> Thu <input
											type="checkbox" name="weekday" value="星期五" ${day5} /> Fri <input
											type="checkbox" name="weekday" value="星期六" ${day6} /> Sat <input
											type="checkbox" name="weekday" value="星期日" ${day7} /> Sun <font
											color="red" size="-1">${MsgMap.errorWeekday}</font></td>
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
										<th style="border:none;" nowrap="nowrap">Available time</th>
										<td style="border:none;"><input type="checkbox" name="times" value="晚餐"
											${cookTime1} /> Dinner <input type="checkbox" name="times"
											value="中餐" ${cookTime2} /> Lunch <input type="checkbox"
											name="times" value="下午茶" ${cookTime3} /> Afternoon tea <input
											type="checkbox" name="times" value="其他" ${cookTime4} /> Other <font
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
										<th style="border:none;" nowrap="nowrap">Maximum customers</th>
										<td style="border:none;"><input type="radio" name="pplNums" id="nums_1"
											value="1人" ${ppl1} /> 1 person <input type="radio" name="pplNums"
											id="nums_2" value="2人" ${ppl2} /> 2 people <input type="radio"
											name="pplNums" id="nums_3" value="3人" ${ppl3} /> 3 people <input
											type="radio" name="pplNums" id="nums_4" value="4人" ${ppl4} />
											4 people <input type="radio" name="pplNums" id="nums_5" value="5人"
											${ppl5} /> 5 people <input type="radio" name="pplNums" id="nums_6"
											value="6人以上" ${ppl6} /> More than 6 people <font color="red" size="-1">${MsgMap.errorPplNums}</font></td>
									</tr>
									<c:if test="${!empty param.vegan}">
										<c:choose>
											<c:when test="${param.vegan eq '提供素食'}">
												<c:set var="veg1" value="checked" />
											</c:when>
											<c:when test="${param.vegan eq '無提供素食'}">
												<c:set var="veg2" value="checked" />
											</c:when>
										</c:choose>
									</c:if>
									<tr>
										<th style="border:none;" nowrap="nowrap">Vegetarian</th>
										<td style="border:none;"><input type="radio" name="vegan" value="提供素食" ${veg1} />
											Available<input type="radio" name="vegan" value="無提供素食" ${veg2} />
											Unavailable <font color="red" size="-1">${MsgMap.errorVegan}</font></td>
									</tr>

									<c:if test="${!empty param.price}">
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
									</c:if>
									<tr>
										<th style="border:none;" nowrap="nowrap">Each price</th>
										<td style="border:none;"><input type="radio" name="price" value="50元以下"
											${price1 } /> Under 50 NTD <input type="radio" name="price"
											value="50-100元" ${price2 } /> 50-100 <input type="radio"
											name="price" value="100-150元" ${price3 } /> 100-150 <input
											type="radio" name="price" value="150-200元" ${price4 } />
											150-200 <input type="radio" name="price" value="200元以上"
											${price5 } /> Above 200 NTD <font color="red" size="-1">${MsgMap.errorPrice}</font></td>
									</tr>
									<c:if test="${!empty param.status}">
										<c:choose>
											<c:when test="${param.status eq '歡迎搭伙'}">
												<c:set var="open1" value="checked" />
											</c:when>
											<c:when test="${param.status eq '目前額滿'}">
												<c:set var="open2" value="checked" />
											</c:when>
										</c:choose>
									</c:if>
									<tr>
										<th style="border:none;" nowrap="nowrap">HOST Status</th>
										<td style="border:none;"><input type="radio" name="status" value="歡迎搭伙"
											${open1} /> Available <input type="radio" name="status"
											value="目前額滿" ${open2} /> Unavailable <font color="red" size="-1">${MsgMap.errorStatus}</font></td>
									</tr>
								</table>
								</div>
								<div class="col-md-4">
								<h3 style="text-align:center; color:#fff;"><strong>Kitchen Environment / Equipment Photo</strong><strong style="font-size:18px;">(Required)</strong></h3>
								<h5>
									<div style="color:#fff;">Please upload a photo, after we <strong style="color:red; background-color:#fff;">approve your detail</strong>, you can upload more</div>
								</h5>
								<table style="background-color:rgba(255,255,255,0.9); box-shadow: 10px 10px 5px #888888; border-radius:10px; " class="table table-hover" width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td valign="top" style="border:none;">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" style="margin: 0;">
												<tr>
													<th style="border:none;" nowrap="nowrap">Upload</th>
													<td style="border:none;"><input type="hidden" id="path" value="" /> <input
														type="file" name="environment" accept="image/*" />(File size limit 5MB)
														<font color="red" size="-1">${MsgMap.errorEnvironment}
															${MsgMap.errorEnvironmentSize}</font></td>
												</tr>
												<tr>
													<th style="border:none;" nowrap="nowrap">Title</th>
													<td style="border:none;"><input name="environmentTitle" type="text"
														maxlength="10" size="20" placeholder="10 characters limit"
														validate='required:true' value="${param.environmentTitle}" />
														<font color="red" size="-1">${MsgMap.errorEnvironmentTitle}</font></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>

								<h3 style="text-align:center; color:#fff;"><strong>Food Photo</strong><strong style="font-size:18px;">(Required)</strong></h3>
								<h5>
									<div style="color:#fff;">Please upload a photo, after we <strong style="color:red; background-color:#fff;">approve your detail</strong>, you can upload more</div>
								</h5>
								<table style="background-color:rgba(255,255,255,0.9); box-shadow: 10px 10px 5px #888888; border-radius:10px; " class="table table-hover" width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td valign="top" style="border:none;"><table width="100%" border="0"
												cellspacing="0" cellpadding="0" style="margin: 0;">
												<tr>
													<th style="border:none;" nowrap="nowrap">Upload</th>
													<td style="border:none;"><input type="file" name="food" accept="image/*" />
														(File size limit 5MB) <font color="red" size="-1">${MsgMap.errorFood}
															${MsgMap.errorFoodSize}</font></td>
												</tr>
												<tr>
													<th style="border:none;" nowrap="nowrap">Title</th>
													<td style="border:none;"><input name="foodTitle" type="text" maxlength="10"
														size="20" placeholder="10 characters limit" validate='required:true'
														value="${param.foodTitle }" /> <font color="red"
														size="-1">${MsgMap.errorFoodTitle }</font></td>
												</tr>
											</table></td>
									</tr>
								</table>

								<h3 style="text-align:center; color:#fff;"><strong>Self intro</strong><strong style="font-size:18px;">(Required)</strong></h3>
								<p>
									<textarea name="selfIntro" cols="47" rows="5" style="vertical-align:middle; box-shadow:10px 10px 5px #888888; border-radius:10px;"
										placeholder="100 characters limit">${param.selfIntro}</textarea>
									<font color="red" size="-1" style="background-color:#fff;">${MsgMap.errorSelfIntro }</font>
								</p>
								</div>
								<div class="col-md-12">
								<p style="text-align:center; font-size:20px;">
									<input style="text-align:center; font-size:20px;" class="btn btn-warning" type="submit" name="submit" id="submit" value="Submit" />
								</p>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	<jsp:include page="/fragment/newfooter.jsp" />
</section>

	<script>
		$('.sm-parallax-window4')
				.parallax(
						{
							imageSrc : '${pageContext.request.contextPath}/image/iStock.jpg'
						});
	</script>
</body>

</html>