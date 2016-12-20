<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <jsp:useBean id="memberBean" class="ch05_09.MemberDAO" />  --%>
<c:set var="subTitle" value="查詢會員資料(_01_register.model)" />
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="/fragment/header.jsp" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Member Center</title>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>
<body >
	
	
		<section class="software sm-parallax-window" data-parallax="scroll" style="margin:0 0; padding:0 0;">
		<div class="container" style="font-family:'微軟正黑體 '">
			<div class="row">
				<div class="col-md-12 sm-text" >
					<h1 style="font-family:微軟正黑體 ;color:#fff; text-shadow:1px 1px 8px #000;"><strong>Member Detail</strong></h1>
				</div>
				<table class="table table-hover col-md-12 sm-text"> 
					<tr>
						<td>
							<h4 class="sm-h4">Name</h4>
						</td>
						<td>
							<h4 class="sm-h4">${LoginOK.name}</h4>
						</td>
					</tr>
					<tr>
						<td>
							<h4 class="sm-h4">Mobile</h4>
						</td>
						<td>
							<h4 class="sm-h4">${LoginOK.cellphone}</h4>
						</td>
					</tr>
					<tr>
						<td>
							<h4 class="sm-h4">BirthDate</h4>
						</td>
						<td>
							<h4 class="sm-h4">${LoginOK.birthdate1}</h4>
						</td>
					</tr>
					<tr>
						<td>
							<h4 class="sm-h4">E-mail</h4>
						</td>
						<td>
							<h4 class="sm-h4">${LoginOK.email}</h4>
						</td>
					</tr>
					<tr>
						<td>
							<h4 class="sm-h4">Register date</h4>
						</td>
						<td>
							<h4 class="sm-h4">${LoginOK.createTime}</h4>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="poster" style="font-family:微軟正黑體 ">
			<div class="container">
				<div class="row sm-row">
					<div class="col-md-2 col-sm-6 pos">
						<span class="fa-stack fa-5x"> <i
							class="fa fa-stack-2x sm-icon sm-icon5"></i>

						</span> <a
							href="<c:url value='/_08_favour/DisplayPageFavour.do?from=memberCenter'/>"><h3
								class="coutext sm-icon-text" data-toggle="modal"
								data-target="#myModal" style="font-family:微軟正黑體 ;color:#fff; text-shadow:1px 1px 8px #000;">My Favourite</h3></a>
					</div>
					<div class="col-md-2 col-sm-6 pos">
						<span class="fa-stack fa-5x"> <i
							class="fa fa-stack-2x sm-icon sm-icon1"></i>

						</span> <a href="UpdateDetail.jsp"><h3 class="coutext sm-icon-text" style="font-family:微軟正黑體 ;color:#fff; text-shadow:1px 1px 8px #000;">Update Info</h3></a>
					</div>
					<div class="col-md-2 col-sm-6 pos">
						<span class="fa-stack fa-5x"> <i
							class="fa fa-stack-2x sm-icon sm-icon2"></i>

						</span> <a
							href="${pageContext.request.contextPath}/_06_orderProcess/OrderList.jsp"><h3
								class="coutext sm-icon-text" style="font-family:微軟正黑體 ;color:#fff; text-shadow:1px 1px 8px #000;">My Order</h3></a>
					</div>
					<div class="col-md-2 col-sm-6 pos">
						<span class="fa-stack fa-5x"> <i
							class="fa  fa-stack-2x sm-icon sm-icon3"></i>

						</span> <a href="${pageContext.request.contextPath}/_09_community/Holder.jsp" class="context"><h3
								class="coutext sm-icon-text" style="font-family:微軟正黑體 ;color:#fff; text-shadow:1px 1px 8px #000; ">My Activity</h3></a>
					</div>
					<c:if test="${LoginOK.verified eq 'F'}">
					<div class="col-md-2 col-sm-6 pos">
						<span class="fa-stack fa-5x"> <i
							class="fa fa-stack-2x sm-icon sm-icon4"></i>

						</span> <a
							href="${pageContext.request.contextPath}/_00_register/reVerified.jsp">
							<h3 class="coutext sm-icon-text" style="font-family:微軟正黑體 ; color:#fff; text-shadow:1px 1px 8px #000;">Resend verify email</h3>
						</a>
					</div>
					</c:if>
				</div>
			</div>
		</div>


	<jsp:include page="/fragment/newfooter.jsp" />
	</section>
	<script>
		$('.sm-parallax-window')
				.parallax(
						{
							imageSrc : '${pageContext.request.contextPath}/image/clear.jpeg'
						});
	</script>
</body>
</html>

