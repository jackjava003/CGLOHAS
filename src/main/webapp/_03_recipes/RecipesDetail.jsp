<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>Recipe Detail</title>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>
<body>

	<jsp:include page="/fragment/recipeheader.jsp" />
	<jsp:useBean id="recipe_items" class="_03_recipes.model.recipes_DAO" />
	<jsp:setProperty property="selectID" name="recipe_items"
		value="${param.id}" />
	<div
		style="margin-top:100px;background-size:100%; background-position:center; background-attachment:fixed; background-repeat:no-repeat; height:800px; width:100%; background-image:url('${pageContext.servletContext.contextPath}/_00_init/getImage?id=${param.id}&type=Recipes')">

	</div>
	<div class="container"
		style="font-family: '微軟正黑體'; margin-top: 100px; margin-bottom: 100px;">
		<div class="row">
			<div class="col-md-6">
				<table class="table table-hover"
					style="background: #fff; border-radius: 50px 50px; border: none;">
					<tr >
						<td style="text-align:center; border:none;"><h2 style="font-size:36px;">${recipe_items.select.name} </h2></td>
					</tr>
				</table>
				<table class="table table-hover"
					style="background: #fff; border-radius: 50px 50px; border: none;">

					<!-- 				<TD><div> -->

					<!-- 						<img height='150' width='150' -->
					<%-- 							src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${param.id}&type=Recipes'> --%>
					<!-- 					</div></TD> -->
					<c:forEach var="item" items="${recipe_items.recipesItems}">
						<jsp:useBean id="item_NAME" class="_04_listItems.model.item_DAO" />
						<jsp:setProperty property="selectID" name="item_NAME"
							value="${item.itemID}" />
						<TR>
							<TD style="border: none; font-size: 20px;" width="300"
								align="center"><a class="hover"
								href="${pageContext.servletContext.contextPath}/_04_listItems/iteminfo.jsp?id=${item_NAME.select.itemID}">${item_NAME.select.name}</a>
							</TD>
							<TD style="font-size: 20px; text-align: center; border: none;">${item.detail}</TD>
						</TR>
					</c:forEach>
				</table>
				<table class="table table-hover"
					style="background: #fff; border-radius: 50px 50px; border: none;">
					<tr>
						<td style="text-align: center;">
							<%-- 		<form method="POST" action="<c:url value='/favour.do' />" --%>
							<!-- 		id="favour.do"> --> <input width="300" type="submit"
							value="Add to my favourite" class="btn btn-success"
							onClick="addFavour(${param.id},${param.pageNo},'${param.type}')"  >
						</td>
						<td style="text-align: center; padding-left: 25px; width: 300px;"><a
							style="font-size: 20px;"
							href="<c:url value='/_03_recipes/listRecipes.jsp?pageNo=${param.pageNo}&type=${param.type}'/>">Return page</a>&nbsp;&nbsp;&nbsp;

						</td>
						<td>
							<!-- Button trigger modal -->
							<button type="button" class="btn btn-warning "
								data-toggle="modal" data-target="#myModal">Click for Recipe steps</button> <!-- Modal -->
							<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
								aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">
												<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
											</button>
											<h4 class="modal-title" id="myModalLabel" style="text-align:center;">All steps</h4>
										</div>
										<div class="modal-body">
											<c:forEach var="process"
												items="${recipe_items.recipesProcess}">
												<table style="margin-top:5px;">
													<tr >
														<td>

															<DIV
																style=" position:relative; background-position:center; background-size:cover; height:100px; width:100px; background-image:url('${pageContext.servletContext.contextPath}/_00_init/getImage?id=${process.processID}&type=Process&recipeID=${process.recipeID}');">
															</DIV>
														</td>
														<td style="padding-left:10px;">
															<h4><strong>Step 
																${process.processID}:</strong></h4><h5><strong>${process.process}</strong></h5>

														</td>


													</tr>


												</table>



											</c:forEach>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-success"
												data-dismiss="modal">Close</button>			
										</div>
									</div>
								</div>
							</div>

						</td>

					</tr>
				</TABLE>
			</div>
			<form>
				<input type="hidden" name="a" />
			</form>

			<div class="col-md-6" style="margin-bottom: 400px;">
				<c:forEach var="process" items="${recipe_items.recipesProcess}">
					<DIV class="mySlides"
						style="border:2px solid; position:absolute; background-position:center; background-size:cover; height:400px; width:600px; background-image:url('${pageContext.servletContext.contextPath}/_00_init/getImage?id=${process.processID}&type=Process&recipeID=${process.recipeID}');">
						<h3 style="text-shadow: 1px 1px 5px #000; color: #fff; padding-left:20px; padding-right:100px;"
							class="mySlides1"><strong>Step ${process.processID}:<br>${process.process}</strong></h3>
					</DIV>
				</c:forEach>
			</div>

			<c:choose>
				<c:when test="${not empty param.pageNo}">
					<c:set var="returnPage" value="pageNo=${param.pageNo}" />
				</c:when>
				<c:otherwise>
					<c:set var="returnPage" value="" />
				</c:otherwise>
			</c:choose>

		</div>
	</div>






	<jsp:include page="/fragment/newrecipefooter.jsp" />




	<script type="text/javascript">
function addFavour(n,x,z) {
		document.forms[0].action="<c:url value='/_08_favour/addFavour.do?recipeID=" + n +"&pageNo="+x+"&type="+z+"'/>";
		document.forms[0].method="POST";
		document.forms[0].submit();
}
</script>
</body>
</html>