<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
<script type="text/javascript">
function confirmDelete(n) {
	if (confirm("Delete this recipe? ") ) {
		document.forms[0].action="<c:url value='DeleteFavourServlet.do?recipeID=" + n +"' />" ;
		document.forms[0].method="POST";
		document.forms[0].submit();
	} else {
	
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>C.G.LOHAS</title>
</head>
<jsp:include page="/fragment/header.jsp" />
<body>

	<section class="software sm-parallax-window" data-parallax="scroll" style="margin:0 0; padding:0 0;">
	<div class="container">
		<div class="row">
			<div class="col-md-12 sm-text">
				<h1 style="color:#fff; text-shadow:1px 1px 8px #000;"><strong>My Favorite</strong></h1>
			</div>
			<c:if test="${not empty LoginOK }">
				<table class="table table-hover col-md-12 sm-text" style="margin-bottom:300px;">
					<c:forEach var="favourController" items="${favour}">
						<TR>
							<TD colspan="2"><img height='200' width='200'
								src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${favourController.recipesID}&type=Recipes'></TD>
							<TD colspan="2"><jsp:useBean id="recipeNameController"
									class="_03_recipes.model.recipes_DAO" /> <jsp:setProperty
									property="selectID" name="recipeNameController"
									value="${favourController.recipesID}" />
								<div style="position:center; text-align: left; font-size: 28px;">
									<a
										href="<c:url value='/_08_favour/DisplayPageFavour.do?id=${favourController.recipesID}'/>">${recipeNameController.select.name}</a>
								</div></TD>
							<TD colspan="1"><Input type="button" name="delete"
								class="btn btn-success" value="Delete"
								onClick="confirmDelete(${favourController.recipesID})"></TD>
						</TR>
					</c:forEach>
					
					<c:if test="${empty totalPages}">
						<jsp:useBean id="total" class="_08_favour.model.ManageMyFavourDAO" />
						<c:set var="totalPages" target="total.totalPages"></c:set>
					</c:if>

					<tr style="font-size: 28px;">
						<td ><c:if test="${favourPageNo > 1}">
								<div id="pfirst">
									<a
										href="<c:url value='/_08_favour/DisplayPageFavour.do?favourPageNo=1&from=memberCenter'/>">First page</a>&nbsp;&nbsp;&nbsp;
								</div>
							</c:if></td>
						<td ><c:if test="${favourPageNo > 1}">
								<div id="pprev">
									<a
										href="<c:url value='/_08_favour/DisplayPageFavour.do?favourPageNo=${favourPageNo-1}&from=memberCenter' />">Previous page</a>&nbsp;&nbsp;&nbsp;
								</div>
							</c:if></td>
						<td ><c:if test="${favourPageNo != totalPages}">
								<div id="pnext">
									<a
										href="<c:url value='/_08_favour/DisplayPageFavour.do?favourPageNo=${favourPageNo+1}&from=memberCenter' />">Next page</a>&nbsp;&nbsp;&nbsp;
								</div>
							</c:if></td>
						<td ><c:if test="${favourPageNo != totalPages}">
								<div id="plast">
									<a
										href="<c:url value='/_08_favour/DisplayPageFavour.do?favourPageNo=${totalPages}&from=memberCenter' />">Last page</a>&nbsp;&nbsp;&nbsp;
								</div>
							</c:if></td>
						<td style="color:#fff; text-shadow:1px 1px 8px #000;" align="center">${favourPageNo} page / Total ${totalPages} pages</td>
					</tr>
		</table>
		
		<form>
			<input type="hidden" name="a" />
		</form>
		
		
		</c:if>

	</div>
	</div>




	<c:if test="${empty LoginOK }">
		<p style="color: white;">
			Please <a
				href="<c:url value='/_02_login/login.jsp?fromRecipesDetail=${param.id}' />">
				Login </a> or <a href="<c:url value='/_00_register/register.jsp' />"> register </a>
		</p>
	</c:if> <jsp:include page="/fragment/newfooter.jsp" /> </section>
	<script>
        $('.sm-parallax-window').parallax({imageSrc: '${pageContext.request.contextPath}/image/blue.jpg'});
    </script>
</body>
</html>