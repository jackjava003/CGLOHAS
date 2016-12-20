<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server 
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance 
	response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale" 
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
<script type="text/javascript">
function confirmDelete(n) {
	if (confirm("Do you want to delete this item ? ") ) {
		document.forms[0].action="<c:url value='UpdateItem.do?cmd=DEL&itemID=" + n +"' />" ;
		document.forms[0].method="POST";
		document.forms[0].submit();
	} else {
	
	}
}
function modify(key, qty, index) {
	var x = "newQty" + index;
	var newQty = document.getElementById(x).value;
	if  (newQty < 0 ) {
		window.alert ('The number can not be less than 0');
		return ; 
	}
	if  (newQty == 0 ) {
		window.alert ("Please click delete button to remove this item");
		document.getElementById(x).value = qty;
		return ; 
	}
	if  (newQty == qty ) {
		window.alert ("Same amount, no need to modify");
		return ; 
	}
	if (confirm("Change amount from " + qty + " to " + newQty + " ? ") ) {
		document.forms[0].action="<c:url value='UpdateItem.do?cmd=MOD&itemID=" + key + "&newQty=" + newQty +"' />" ;
		document.forms[0].method="POST";
		document.forms[0].submit();
	} else {
		document.getElementById(x).value = qty;
	}
}
function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57)){
      return false;
   }
   return true;
}
function Checkout(qty) {
	if (qty == 0)  {
		alert("No purchase of any items, do not need to checkout");
		return false;
	}
	if (confirm("Confirm the order details ? ") ) {
		return true;
	} else {
		return false;
	}
}
function Abort() {
	if (confirm("Delete this shopping list ? ") ) {
		return true;
	} else {
		return false;
	}
}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>購物清單</title>
</head>
<body>
	<c:set var="funcName" value="CHE" scope="session" />

	<jsp:include page="/fragment/header.jsp" />
	<div class="sm-parallax-window1">
		<%--
<c:choose>
   <c:when test="${ShoppingCart.itemNumber > 0}">
      <c:set var="cartContent" value="購物車內有${ShoppingCart.itemNumber}項商品"/>
   </c:when>
   <c:otherwise>
      <c:set var="cartContent" value="您尚未購買任何商品"/>        
   </c:otherwise>
</c:choose>
--%>

		<c:choose>
			<c:when test="${ShoppingCart.subtotal > 0}">
				<c:set var="subtotalMessage" value="Subtotal:${ShoppingCart.subtotal} 元" />
				<c:set var="subtotal" value="${ShoppingCart.subtotal}" />
			</c:when>
			<c:otherwise>
				<c:set var="subtotalMessage" value="Subtotal:  0 NTD" />
				<c:set var="subtotal" value="0" />
			</c:otherwise>
		</c:choose>

		<center>
			<div class="container">
				<div class="row" style="margin-top: 20px;">
					
					<table class="table table-hover col-md-12 sm-text"
						style="background: #fff; font-size: 20px;">


						<!--          購物車的標題          -->

						<!-- 						<TR height='40'> -->
						<!-- 							<TD width="270">&nbsp;</TD> -->
						<%-- 							<TD width="280" align='center'><FONT size='+2'>${AppName}</FONT></TD> --%>
						<!-- 							<TD width="270" align='right'></TD> -->
						<!-- 						</TR> -->
						<TR >

							<TD align='center'><FONT size='+2'>Shopping list</FONT></TD>

						</TR>
					</TABLE>



					<c:choose>
						<c:when test="${empty ShoppingCart}">
							<table class="table table-hover col-md-12 sm-text"
								style="background: #fff; font-size: 20px; ">
								<TR>
									<TD align="center" style="color: red;">You have not purchased any items <a
										href="<c:url value='/_04_listItems/DisplayPageProducts' />">Continue Shopping</a>
									</TD>
								</TR>
							</table>
							
							<table class="table table-hover col-md-12 sm-text"
						style="background: #fff; font-size: 20px; margin-bottom:400px;">



						<TR>
							<TD width="260" align='center'><A
								href="<c:url value='../_04_listItems/DisplayPageProducts?pageNo=${param.pageNo}&type=${param.type}' />">Continue Shopping</A>
							</TD>
							<TD width="260" align='center'><A
								href="<c:url value='/_05_ShoppingCart/checkout.do' />"
								onClick="return Checkout(${subtotal});">Confirm</A></TD>
							<TD width="260" align='center'><A
								href="<c:url value='/_04_listItems/abort.do' />"
								onClick="return Abort();">Delete list</A></TD>
						</TR>



					</TABLE>
						</c:when>
						<c:otherwise>
							<table class="table table-hover col-md-12 sm-text"
								style="background: #fff; font-size: 20px;">



								<TR style="text-align: right">
									<TD style="text-align: left"width="280">Ingredient</TD>
									<TD width="110"></TD>
									<TD width="110">Price</TD>
									<TD width="110">Quantity</TD>
									<TD width="110">Modify</TD>
									<TD width="110">Total</TD>

								</TR>

								<c:forEach varStatus="vs" var="anEntry"
									items="${ShoppingCart.content}">
									<TR style="text-align: right;">
									<jsp:useBean id="itemname" class="_04_listItems.model.item_DAO" />
									<jsp:setProperty property="selectID" name="itemname" value="${anEntry.value.itemid}"/>
									
										<TD style="text-align: left" width="280">${itemname.select.name}<c:if
												test="${itemID eq anEntry.value.itemid}">
												<span style="color: red;">${erroMessage}</span>
											</c:if>
										</TD>
										<TD style="width: 110;"></TD>
										<TD style="width: 110;"><fmt:formatNumber
												value="${anEntry.value.unitPrice * anEntry.value.discount }"
												pattern="#,###" />NTD</TD>


										<TD style="width: 110;"><Input id="newQty${vs.index}"
											style="width: 28px; text-align: right" name="newQty"
											type="text"
											value="<fmt:formatNumber value="${anEntry.value.qty}" />"
											name="qty" onkeypress="return isNumberKey(event)" /></TD>
										<TD style="width: 110;"><Input type="button"
											name="update" value="Modify" 
											onClick="modify(${anEntry.key}, ${anEntry.value.qty}, ${vs.index})">
											<Input type="button" name="delete" value="Delete" 
											onClick="confirmDelete(${anEntry.key})"></TD>
										<TD style="width: 110;"><fmt:formatNumber
												value="${anEntry.value.unitPrice * anEntry.value.discount * anEntry.value.qty}"
												pattern="#,###,###" />NTD</TD>

									</TR>
								</c:forEach>
							</TABLE>
								<table class="table table-hover col-md-12 sm-text"
								style="background: #fff; font-size: 20px;">
							<TR >
								<td align='right' width="1015">Price</td>
								<TD align='right'><fmt:formatNumber value="${subtotal}"
										pattern="#,###,###" />NTD</TD>
						
							</TR>
							<TR>
								<td align='right'>Direct Discount</td>
								<TD align='right'> <c:set var="VAT" value="${(subtotal-subtotal%1000)/1000}" /><fmt:formatNumber value="${VAT*100}"
										pattern="#,###,###" />NTD</TD>
					
				
							</TR>
							<TR>
								<td align='right'>Subtotal</td>
								<TD align='right'><fmt:formatNumber
										value="${subtotal - VAT*100 }" pattern="#,###,###" />NTD</TD>
								
							</TR>



							</TABLE>
							<table class="table table-hover col-md-12 sm-text"
						style="background: #fff; font-size: 20px;">



						<TR>
							<TD width="260" align='center'><A
								href="<c:url value='../_04_listItems/DisplayPageProducts?pageNo=${param.pageNo}&type=${param.type}' />">Continue Shopping</A>
							</TD>
							<TD width="260" align='center'><A
								href="<c:url value='/_05_ShoppingCart/checkout.do' />"
								onClick="return Checkout(${subtotal});">Confirm</A></TD>
							<TD width="260" align='center'><A
								href="<c:url value='/_04_listItems/abort.do' />"
								onClick="return Abort();">Delete list</A></TD>
						</TR>



					</TABLE>
							
							
						</c:otherwise>
					</c:choose>
					
				</div>
			</div>
		</center>



		<form>
			<input type="hidden" name="a" />
		</form>

		<jsp:include page="/fragment/newfooter.jsp"></jsp:include>
	</div>
	<script>
		$('.sm-parallax-window1')
				.parallax(
						{
							imageSrc : '${pageContext.request.contextPath}/image/Cool-Green-Natural-Desktop-HD-Wallpaper.jpg'
						});
	</script>
</body>
</html>