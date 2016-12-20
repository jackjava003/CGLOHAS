<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Detail</title>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>
<body>
	<jsp:include page="/fragment/header.jsp" />
	<section class="software sm-parallax-window" data-parallax="scroll" style="margin:0 0; padding:0 0;">
	<div style="font-family: '微軟正黑體';">
	
	<div class="container" >
		<div class="row">
			<div class="col-md-12 sm-text">
				<h1 style="color:#fff; text-shadow:1px 1px 8px #000;"><strong>Update Detail</strong></h1>
			</div>
			<table class="table table-hover col-md-12 sm-text">
				<tr>
					<td>
						<h4 class="sm-h4" style="color:#fff; text-shadow:1px 1px 8px #000;" >Name</h4>
					</td>
					<td>
						<h4 class="sm-h4" >
							<a href="<c:url value='UpdateName.jsp' />">${LoginOK.name}</a>
						</h4>
					</td>
				</tr>
				<tr>
					<td>
						<h4 class="sm-h4" style="color:#fff; text-shadow:1px 1px 8px #000;">Mobile</h4>
					</td>
					<td>
						<h4 class="sm-h4" >
							<a href="<c:url value='UpdatePhone.jsp' /> ">${LoginOK.cellphone}</a>
						</h4>
					</td>
				</tr>
				<tr>
					<td>
						<h4 class="sm-h4" style="color:#fff; text-shadow:1px 1px 8px #000;">E-mail</h4>
					</td>
					<td>
						<h4 class="sm-h4">
							<a href="<c:url value='UpdateEmail.jsp' /> ">${LoginOK.email}</a>
						</h4>
					</td>
				</tr>
				<tr>
					<td>
						<h4 class="sm-h4" style="color:#fff; text-shadow:1px 1px 8px #000;">Password</h4>
					</td>
					<td>
						<h4 class="sm-h4">
							<a href="<c:url value='UpdatePassword.jsp' /> ">Change password</a>
						</h4>
					</td>
				</tr>
				<tr>
					<td>
						<h4 class="sm-h4" style="color:#fff; text-shadow:1px 1px 8px #000;">Profile picture</h4>
					</td>
					<td>
						<h4 class="sm-h4">
							<a href="<c:url value='UpdatePhoto.jsp' /> "><img
								height='50px' width='40px'
								src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${LoginOK.userID}&type=userImg'></a>
						</h4>
					</td>
				</tr>
			</table>
		</div>
	</div>
	</div>

	<jsp:include page="/fragment/newfooter.jsp" /> 
	</section>
	
	<script>
	
		
		$('.sm-parallax-window')
				.parallax(
						{
							imageSrc : '${pageContext.request.contextPath}/image/blue.jpg'
						});
	</script>

</body>
</html>