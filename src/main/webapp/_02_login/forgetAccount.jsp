<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>C.G.LOHAS</title>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>
<body>
	<jsp:include page="/fragment/header.jsp" />
	<c:if test="${!empty ErrorMsg}">
		<script type="text/javascript">
			alert("wrong");
		</script>
	</c:if>
	<section class="software sm-parallax-window" data-parallax="scroll"
		style="margin:0 0; padding:0 0;">
	<div style="margin-bottom: 200px; font-family: 微軟正黑體;">
		<div class="container">
			<div class="row login-from">
				<div class="col-md-12">
					<h1>
						<strong>Forget Account</strong>
					</h1>
				</div>
				<div class="col-md-8">
					<div align="center">
						<form action="<c:url value='forgetAccount.do' />" method="POST">
							<table class="table table-hover sm-text"
								style="background: #fff; font-size: 20px; text-align: left; border-radius: 10px; box-shadow: 10px 10px 5px #888888;">
								<tr>
									<td
										style="border-radius: 10px; padding: 5px 15px; border: none;">
										<div class="form-group"
											style="padding: 5px 15px; border: none;">

											<label style="font-size: 30px;">Password&nbsp;&nbsp;<font
												id="errorPw" size="5" color="#FF0000">${MsgMap.errorAcc}</font></label>
											<br>
											<input type="text" name="Password" value="${param.Password}"
												class="form-control" placeholder="Insert your password"
												style="font-size: 30px;">
										</div>
									</td>
								</tr>
								<tr>
									<td
										style="border-radius: 10px; padding: 5px 15px; border: none;">
										<div class="form-group"
											style="padding: 5px 15px; border: none;">

											<label style="font-size: 30px;">E-mail&nbsp;&nbsp;<font
												id="errorEm" size="5" color="#FF0000">${MsgMap.errorEm}</font></label>
											<br> <input type="text" name="Email"
												value="${param.Email}" class="form-control"
												placeholder="Insert your E-mail" style="font-size: 30px;">
										</div>
									</td>
								</tr>
								<tr>
									<td
										style="border-radius: 10px; padding: 5px 15px; border: none;">
										<div class="form-group"
											style="padding: 5px 15px; border: none;">

											<label style="font-size: 30px;">Mobile&nbsp;&nbsp;<font
												id="errorPh" size="5" color="#FF0000">${MsgMap.errorPh}</font></label>
											<br> <input type="text" name="Phone"
												value="${param.Phone}" class="form-control"
												placeholder="Insert your mobile" style="font-size: 30px;">
										</div>
									</td>
								</tr>
								<tr>
									<td
										style="border-radius: 10px; padding: 5px 15px; border: none;">
										<div class="form-group"
											style="padding: 5px 15px; border: none;">
											<div>
												<div class="g-recaptcha"
													data-sitekey="6LcLfQcUAAAAACCrN4x3uNDWUi-zoiJLyco9BnfN"></div>
												<font color="red" size="5">${MsgMap.errorRecaptcha}</font>
											</div>
											<br>
											<c:if test="${! empty timeLeft}">
												<font id="errorEm" size="5" color="#FF0000">Remaining attempts:
													${timeLeft }, up to 3 attempts, IP will be block for 3 hours</font>
											</c:if>
										</div>
									</td>
								</tr>
								<tr>
									<td
										style="border-radius: 10px; padding: 5px 15px; border: none; text-align:center;">
										<div class="form-group"
											style="padding: 5px 15px; border: none;">
										
											<input type="submit" value="Submit" class="btn btn-warning login-button"
												style="font-size: 20px; width: 120px;">
											<a
									href="<c:url value='login.jsp' />"><button
											type="button" class="btn btn-success login-button"
											style="font-size: 20px; width: 120px;">Login</button></a> <a
									href="<c:url value='forgetPassword.jsp' />"><button
											type="button" class="btn btn-success login-button"
											style="font-size: 20px; width: 120px;">Forget Password</button></a>
											
											
											
												
												
										</div>
									</td>
								</tr>

							</table>
						</form>
					</div>
				</div>
				<div class="col-md-4">
					<img
						style="height: 360px; width: 400px; margin-bottom: 30px; border-radius: 10px; box-shadow: 10px 10px 5px #888888;"
						src="${pageContext.request.contextPath}/image/0001.jpg"> <img
						style="height: 360px; width: 400px; margin-bottom: 30px; border-radius: 10px; box-shadow: 10px 10px 5px #888888;"
						src="${pageContext.request.contextPath}/image/blog1.png">

				</div>


			</div>
		</div>
	</div>

	<jsp:include page="/fragment/newfooter.jsp" /> </section>

	<script src='https://www.google.com/recaptcha/api.js' async defer></script>
	<script>
		$('.sm-parallax-window')
				.parallax(
						{
							imageSrc : '${pageContext.request.contextPath}/image/1-1241886505MO2J.jpg'
						});
	</script>
</body>
</html>