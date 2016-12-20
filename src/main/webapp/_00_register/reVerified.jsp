<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>C.G.LOHAS</title>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>
<body>
	<jsp:include page="/fragment/header.jsp" />
	<section class="software sm-parallax-window" data-parallax="scroll" style="margin:0 0; paddomg:0 0;">
	<div class="container">
		<div class="row"
			style="font-family: '微軟正黑體'; margin-bottom: 500px;">
			<h1 style="text-align:center; color:#fff; text-shadow:1px 1px 8px #000;"><strong>Send verify email</strong></h1>
			<span style="color: red;">${Errors}</span><br>

			

			<form role="form" method="POST" action="<c:url value='/reVerifiedMail.do' />"
				id="/reVerifiedMail.do">
				<div class="form-group">
					<label for="exampleInputEmail1"  style="font-size: 30px; color:#fff; text-shadow:1px 1px 8px #000;">E-mail</label> <input type="email"
						name="email" class="form-control" value="${LoginOK.email}"  readonly="readonly" style="font-size: 24px;">
				</div>
				
				<div id="btnArea" class="modal-footer">
					<input type="submit" name="submit" id="submit" value="Submit and send"  class="btn btn-success" />
					<input type="button" name="submit" id="submit" value="Change e-mail" onclick="location.href='<c:url value='/_01_userUpdate/UpdateEmail.jsp' />'" class="btn btn-success"  />
				
				</div>
		
			</form>
			
		
			
		
		</div>
	</div>
	<jsp:include page="/fragment/newfooter.jsp" /> </section>
	<script>
		$('.sm-parallax-window')
				.parallax(
						{
							imageSrc : '${pageContext.request.contextPath}/image/blue.jpg'
						});
	</script>
</body>
</html>