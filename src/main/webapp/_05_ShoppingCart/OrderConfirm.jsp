<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 取得今天的日期，今天的日期應當在最後確認時才取得 -->
<jsp:useBean id="today" class="java.util.Date" scope="session" />
<title>Shopping list confirm</title>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>
<body>
	<c:set var="funcName" value="CHE" scope="session" />
	<jsp:include page="/fragment/header.jsp" />
	<div class="sm-parallax-window1">
		<div class="container" style="font-family: '微軟正黑體'">
			<div class="row">

				<CENTER>

					<FORM action="<c:url value='ProcessOrder.do' />" method="POST">
						<table class="table table-hover col-md-12 sm-text"
							style="background: #fff; font-size: 20px; margin-top: 20px;">
							<tr>
								<td>請確認下列訊息</td>
							</tr>
						</table>
						<table class="table table-hover col-md-12 sm-text"
							style="background: #fff; font-size: 20px;">


							<TR>
								<TD style="text-align: center;">Member ID：${LoginOK.userID}</TD>
								<TD style="text-align: center;">Name：${LoginOK.name}</TD>
								<TD style="text-align: center;">Mobile：${LoginOK.cellphone}</TD>
								<TD style="text-align: center;">Order Date：<fmt:formatDate
										value="${today}" pattern="yyyy-MM-dd" /></TD>
							</TR>
						</table>
						<table class="table table-hover col-md-12 sm-text"
							style="background: #fff; font-size: 20px;">

							<TR>
								<TD colspan='4' style="text-align: left;">Address： <input
									name="zipcode" id="zipcode" type="hidden" maxlength="4"
									size="4" /> <input id="test" type="hidden"
									value="${param.zone1}" /> <input id="test2" type="hidden"
									value="${param.zone2}" /> <select name="zone1" id="zone1"></select>
									<select name="zone2" id="zone2"></select><input
									style="background: #ECFFCD;" size="78" name="address"
									id="address" type="text" size="60" value="${param.address}" /><br>
									<font color="red" size="-1">${MsgMap.errorArea}
										${MsgMap.errorAddress}</font>

								</TD>
							</TR>
							<TR>
								<TD colspan='4' style="text-align: left;">Remarks：<textarea
										style="background: #ECFFCD; resize: none;" name="Description"
										cols="100" rows="1"> ${param.Description}</textarea> <font
									color="red" size="-1">${MsgMap.errorDesc} </font>
								</TD>
							</TR>
						</table>


						<table class="table table-hover col-md-12 sm-text"
							style="background: #fff; font-size: 20px;">

							<TR>
								<TD style="text-align: left; font-size: 12pt;" width="350">Ingredient</TD>
								<TD style="text-align: right; font-size: 12pt;" width="80">Price</TD>
								<TD style="text-align: right; font-size: 12pt;" width="60">Quantity</TD>
								<TD style="text-align: right; font-size: 12pt;" width="110">Total</TD>
							</TR>

							<c:forEach varStatus="vs" var="anEntry"
								items="${ShoppingCart.content}">
								<TR height='16'>
									<jsp:useBean id="itemname" class="_04_listItems.model.item_DAO" />
									<jsp:setProperty property="selectID" name="itemname"
										value="${anEntry.value.itemid}" />

									<TD style="text-align: left; font-size: 11pt;">${itemname.select.name}</TD>
									<TD style="text-align: right; font-size: 11pt;"><fmt:formatNumber
											value="${anEntry.value.unitPrice * anEntry.value.discount }"
											pattern="#,###" />NTD</TD>
									<TD style="text-align: right; font-size: 11pt;">
										${anEntry.value.qty}</TD>
									<TD style="text-align: right; font-size: 11pt;"><fmt:formatNumber
											value="${anEntry.value.unitPrice * anEntry.value.discount * anEntry.value.qty}"
											pattern="#,###,###" />NTD</TD>
								</TR>
							</c:forEach>
						</TABLE>
						<table class="table table-hover col-md-12 sm-text"
							style="background: #fff; font-size: 20px;">
							<TR height='16'>
								<TD style="text-align: left;">Total price：</TD>
								<TD style="text-align: right; font-size: 11pt;" colspan='4'><fmt:formatNumber
										value="${ShoppingCart.subtotal}" pattern="#,###,### NTD" /></TD>
								<!-- <TD style="text-align:right;font-size: 11pt;" ><fmt:formatNumber value="${ShoppingCart.subtotal}" pattern="#,###,###" />元</TD> -->
							</TR>
							<TR>
								<TD style="text-align: left;">Direct Discount：</TD>
								<fmt:formatNumber var="VAT" type="number"
									value="${(ShoppingCart.subtotal - ShoppingCart.subtotal%1000)/1000}"
									maxFractionDigits="0" pattern="#" />
								<TD colspan='4' style="text-align: right; font-size: 11pt;">
									<fmt:formatNumber value="${VAT*100}" pattern="#,###,###" />NTD
								</TD>

							</TR>
							<TR>
								<TD style="text-align: left;">Subtotal：</TD>
								<TD colspan='4' style="text-align: right; font-size: 11pt;"><fmt:formatNumber
										value="${ShoppingCart.subtotal - VAT*100 }"
										pattern="#,###,### NTD" /></TD>
								<!-- <TD style="text-align:right;font-size: 11pt;color:#AA0200;" ><fmt:formatNumber value="${ShoppingCart.subtotal + VAT }" pattern="#,###,###" />元</TD>  -->

							</TR>
						</TABLE>
						<TABLE class="table table-hover col-md-6 sm-text"
							style="background: #fff; font-size: 20px; margin-top: 20px;">
							<TR>
								<TD style="text-align: left;" colspan='4'>Pay Option：</TD>
							</TR>
							
							<c:if test="${!empty param.payOption}">
								<c:choose>
									<c:when test="${param.payOption eq '貨到付款'}">
										<c:set var="arrivePay" value="checked" />
									</c:when>
									<c:otherwise>
										<c:set var="creditCard" value="checked" />
									</c:otherwise>
								</c:choose>
							</c:if>
							<TR>
								<TD style="text-align: left;" colspan='2'><input
									type="radio" name="payOption" value="貨到付款"
									onclick="hideCardBox()" ${arrivePay} />Cash on delivery
									<input
									id="cardchecked" type="radio" name="payOption" value="信用卡"
									onclick="showCardBox()" ${creditCard} />Credit card<img id="Payimage" style="height:40px; display:none;" alt="" src="${pageContext.request.contextPath}/image/images.jpg ">
									 <font
									color="red" size="-1">${MsgMap.errorPayOption}</font>
									</TD>
								<TD style="text-align: right;" colspan='2'></TD>
							</TR>
							
							
							<TR id="PayName" style="display: none;">
								<TD style="text-align: left; padding-left:119px;" colspan='2'>
								
								Cardholder's Name：<input type='text' name='cardOwnerName'
									size='20' value='${param.CardOwnerName}'> <font
									color="red" size="-1">${MsgMap.errorCardOwnerName}</font>
									<br>
								Credit card number：<input type='text' name='cardno' size='20'
									value='${param.Cardno}'> <font color="red" size="-1">${MsgMap.errorCardno}</font>
									<br>
								Expire Date:<select name="cardMonth">
										<option value="">Please Select</option>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
								</select>/ <select name="cardYear">
										<option value="">Please Select</option>
										<option value="2016">2016</option>
										<option value="2017">2017</option>
										<option value="2018">2018</option>
										<option value="2019">2019</option>
										<option value="2020">2020</option>
										<option value="2021">2021</option>
										<option value="2022">2022</option>
										<option value="2023">2023</option>
										<option value="2024">2024</option>
										<option value="2025">2025</option>
										<option value="2026">2026</option>
										<option value="2027">2027</option>
								</select> <font color="red" size="-1">${MsgMap.errorCardDate}</font>
								<br>
								CVV number：<input type='text' name='cardThreedNum'
									size='5' value='${param.cardThreedNum}'> <font
									color="red" size="-1">${MsgMap.errorCardThreedNum}</font>					
								</TD>
								<td>
								<img alt="" style="height:150px;" src="${pageContext.request.contextPath}/image/U20.jpg ">
								<img alt="" src="">
								
								</td>
<!-- 								<TD colspan='2'><input type='text' name='cardOwnerName' -->
<%-- 									size='20' value='${param.CardOwnerName}'> <font --%>
<%-- 									color="red" size="-1">${MsgMap.errorCardOwnerName}</font></TD> --%>
							</TR>
							<TR id="PayCard" style="display: none;">
<!-- 								<TD style="text-align: right;" colspan='2'>信用卡卡號：</TD> -->
<!-- 								<TD colspan='2'><input type='text' name='cardno' size='20' -->
<%-- 									value='${param.Cardno}'> <font color="red" size="-1">${MsgMap.errorCardno}</font></TD> --%>
							</TR>
							<TR id="CardDay" style="display: none;">
<!-- 								<TD colspan='2'>信用卡有效截止日期:</TD> -->
<!-- 								<TD colspan='2'><select name="cardMonth"> -->
<!-- 										<option value="">請選擇</option> -->
<!-- 										<option value="1">1</option> -->
<!-- 										<option value="2">2</option> -->
<!-- 										<option value="3">3</option> -->
<!-- 										<option value="4">4</option> -->
<!-- 										<option value="5">5</option> -->
<!-- 										<option value="6">6</option> -->
<!-- 										<option value="7">7</option> -->
<!-- 										<option value="8">8</option> -->
<!-- 										<option value="9">9</option> -->
<!-- 										<option value="10">10</option> -->
<!-- 										<option value="11">11</option> -->
<!-- 										<option value="12">12</option> -->
<!-- 								</select>月/ <select name="cardYear"> -->
<!-- 										<option value="">請選擇</option> -->
<!-- 										<option value="2016">2016</option> -->
<!-- 										<option value="2017">2017</option> -->
<!-- 										<option value="2018">2018</option> -->
<!-- 										<option value="2019">2019</option> -->
<!-- 										<option value="2020">2020</option> -->
<!-- 										<option value="2021">2021</option> -->
<!-- 										<option value="2022">2022</option> -->
<!-- 										<option value="2023">2023</option> -->
<!-- 										<option value="2024">2024</option> -->
<!-- 										<option value="2025">2025</option> -->
<!-- 										<option value="2026">2026</option> -->
<!-- 										<option value="2027">2027</option> -->
<%-- 								</select>年 <font color="red" size="-1">${MsgMap.errorCardDate}</font></TD> --%>
							</TR>
<!-- 							<TR id="threedNum" style="display: none;"> -->
<!-- 								<TD style="text-align: right;" colspan='2'>信用卡背面最後三碼：</TD> -->
<!-- 								<TD colspan='2'><input type='text' name='cardThreedNum' -->
<%-- 									size='5' value='${param.cardThreedNum}'> <font --%>
<%-- 									color="red" size="-1">${MsgMap.errorCardThreedNum}</font></TD> --%>
							</TR>
						</TABLE>

						
						<INPUT TYPE="hidden" name="finalDecision" value=""> <INPUT
							TYPE="SUBMIT" name="OrderBtn" value="Submit"
							class="btn btn-warning" style="font-size:22px;" onclick="return reconfirmOrder();">
						<INPUT TYPE="SUBMIT" name="CancelBtn" value="Cancel order"
							class="btn btn-warning" style="font-size:22px;" onclick="return cancelOrder();">
					</FORM>
				</CENTER>

				<c:if test="${!empty stockError}">
					<jsp:useBean id="item" class="_04_listItems.model.item_DAO" />
					<jsp:setProperty property="selectID" name="item"
						value="${stockError}" />
	${item.select.name} Inventory shortage <br>
				</c:if>
				<br> ${Error} <br>

			</div>
		</div>
		<jsp:include page="/fragment/newfooter.jsp" />

	</div>
	<script>
		$('.sm-parallax-window1')
				.parallax(
						{
							imageSrc : '${pageContext.request.contextPath}/image/Cool-Green-Natural-Desktop-HD-Wallpaper.jpg'
						});
	</script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/javascript/address.js"></script>
	<script type="text/javascript">
		function cancelOrder() {
			if (confirm("Do you want to cancel this order? ")) {
				// 接收此資料的Servlet會使用 finalDecision 參數的值
				document.forms[0].finalDecision.value = "CANCEL";
				return true;
			} else {
				return false;
			}
		}
		function reconfirmOrder() {
			if (confirm("Do you want to submit this order? ")) {
				// 接收此資料的Servlet會使用 finalDecision 參數的值
				document.forms[0].finalDecision.value = "ORDER";
				return true;
			} else {
				return false;
			}
		}
		function showCardBox() {
			document.getElementById('Payimage').style.cssText='display: inline; height:40px;';
			document.getElementById('PayCard').style.cssText = 'display: block;';
			document.getElementById('PayName').style.cssText = 'display: block;';
			document.getElementById('CardDay').style.cssText = 'display: block;';
			document.getElementById('threedNum').style.cssText = 'display: block;';

		}
		function hideCardBox() {
			document.getElementById('Payimage').style.cssText='display: none;';
			document.getElementById('PayCard').style.cssText = 'display: none;';
			document.getElementById('PayName').style.cssText = 'display: none;';
			document.getElementById('CardDay').style.cssText = 'display: none;';
			document.getElementById('threedNum').style.cssText = 'display: none;';
		}
		
		function dofirst(){
			if(document.getElementById('cardchecked').checked){
				showCardBox();
			}else{
				hideCardBox();
			}
		}
		
		window.addEventListener('load', dofirst, false);
	</script>
</body>
</html>