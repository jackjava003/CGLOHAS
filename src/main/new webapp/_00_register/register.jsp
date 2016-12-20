<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>加入會員</title>
<script
	src="${pageContext.request.contextPath}/javascript/ajax.checkRegister.js "></script>
	<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
</head>
<body>
	<jsp:include page="/fragment/header.jsp" />

	<section class="software sm-parallax-window" data-parallax="scroll"
		style="margin:0 0; padding:0 0;">
	<div style="font-family: '微軟正黑體';">
		<div class="section">
			<div class="container">
				<div class="row login-from">
					<h1>
						<strong>Sign Up Membership</strong>
					</h1>
					<div class="col-md-8">
						<form ENCTYPE="multipart/form-data" method="POST"
							action="<c:url value='/register.do' />" id="register.do"
							role="form form1"
							style="text-align: left; font-size: 30px; font-family: '微軟正黑體';">
							<table class="table table-hover sm-text"
								style="background: #fff; font-size: 20px; text-align: left; border-radius: 10px; box-shadow: 10px 10px 5px #888888;">

								<tr style="text-align: left; border-radius: 5px; ">
									<td style="text-align: left; border-radius: 5px; border: none;">
										<div class="form-group" style="font-size: 30px; padding:5px 15px;">
											<label for="exampleInputEmail1">Account</label> &nbsp;<font
												id="errorAc" size="5" color="#FF0000">${MsgMap.errorIDEmpty}${MsgMap.errorIDDup}</font>
											<input type="text" class="form-control" name="account"
												id="account" value="${param.account}" placeholder="Insert your account"
												style="font-size: 30px;">
										</div>
									</td>
								</tr>
								<tr>
									<td style="text-align: left; border: none;">
										<div class="form-group" style="font-size: 30px; padding:5px 15px;">
											<label for="exampleInputPassword1">Password</label> &nbsp;<font
												id="errorPw" color="red" size="5">${MsgMap.errorPasswordEmpty}</font>
											<input type="password" class="form-control" name="password"
												id="password" value="${param.password}" placeholder="Insert your password"
												style="font-size: 30px;">
										</div>
									</td>
								</tr>
								<tr>
									<td style="text-align: left; border: none;">
										<div class="form-group" style="font-size: 30px; padding:5px 15px;">
											<label for="exampleInputPassword1">Gender&nbsp; <font id="getsex" color="red" size="-1">${MsgMap.errorPassword2Empty}</font>
											<br>
												<input type="radio" name="sex" value="boy" id="boy">Male
												<input type="radio" name="sex" value="girl" id="girl">Female
												<font color="red" size="-1">${MsgMap.errorPassword2Empty}</font>
											</label>
										</div>
									</td>
								</tr>
								<tr>
									<td style="text-align: left; border: none;">
										<div class="form-group" style="font-size: 30px; padding:5px 15px;">
											<label for="exampleInputPassword1">Name</label>&nbsp; <font
												id="errorName" color="red" size="5">${MsgMap.errorName}</font>
											<input type="text" class="form-control" name="name"
												id="username" value="${param.name}" placeholder="Insert your name"
												style="font-size: 30px;">
										</div>
									</td>
								</tr>
								<tr>
									<td style="text-align: left; border: none;">
										<div class="form-group" style="font-size: 30px; padding:5px 15px;">
											<label for="exampleInputPassword1">Mobile</label>&nbsp; <font
												color="red" size="5" id="errorCellphone">${MsgMap.errorTel}${MsgMap.errorPhoneDup}</font>
											<input type="text" class="form-control" name="cellphone"
												id="cellphone" value="${param.cellphone}"
												placeholder="Insert your mobile" style="font-size: 30px;">
										</div>
									</td>
								</tr>
								<tr>
									<td style="text-align: left; border: none;">

										<div class="form-group" style="font-size: 30px; padding:5px 15px;">
											<label for="exampleInputPassword1">E-mail</label>&nbsp;<font
												color="red" size="5" id="errorMail">${MsgMap.errorEmail}${MsgMap.errorMailDup}</font>
											<input type="email" class="form-control" name="eMail"
												id="mail" value="${param.eMail}" placeholder="Insert your e-mail"
												style="font-size: 30px;">
										</div>
									</td>
								</tr>
								<tr>
									<td style="text-align: left; border: none;">
										<div class="form-group" style="font-size: 30px; padding:5px 15px;">
											<label for="exampleInputPassword1">BirthDate</label>&nbsp;<font
												color="red" size="5" id="errorBirthday">${MsgMap.errorFormat}</font>
											<input type="date" class="form-control" name="birthDate"
												id="birthday" value="${param.birthDate}"
												placeholder="Insert your BirthDate，yyyy-MM-dd" style="font-size: 30px;">
										</div>
									</td>
								</tr>
								<tr>
									<td style="text-align: left; border: none;">
										<div class="form-group" style="font-size: 30px; padding:5px 15px;">
											<label for="exampleInputFile">Profile picture</label>&nbsp;<font
												color="red" size="5" id="errorPic">${MsgMap.errPicture}</font>
											<input type="file" size="40"
												class="fieldWidth btn btn-warning" style="width: 400px;"
												name="file1" id="file1" accept="image/*">
										</div>
									</td>
								</tr>
								<tr>
									<td style="text-align: left; border: none;">
										<div class="form-group" style="font-size: 30px; padding:5px 15px;">
											<div class="g-recaptcha"
												data-sitekey="6LcLfQcUAAAAACCrN4x3uNDWUi-zoiJLyco9BnfN"></div>
											<font color="red" size="5">${MsgMap.errorRecaptcha}</font>
										</div>
									</td>
								</tr>
								<tr>
									<td style="text-align: left; border-radius: 5px; border: none; font-size: 30px;">
										<div id="btnArea" class="modal-footer">
											<input type="submit"
												name="submit" id="submit" value="Submit" class="btn btn-success"
												disabled="disabled" />
										</div>
									</td>
								</tr>

							</table>

						</form>
					</div>
					<div class="col-md-4">
						<img style="height: 300px; width: 400px; margin-bottom:30px; border-radius:10px; box-shadow: 10px 10px 5px #888888;"
							src="${pageContext.request.contextPath}/image/dinner-meal-table-wine.jpg">
						<img style="height: 300px; width: 400px;  margin-bottom:30px; border-radius:10px; box-shadow: 10px 10px 5px #888888;"
							src="${pageContext.request.contextPath}/image/Eat.jpg"> <img
							style="height: 300px; width: 400px;  margin-bottom:30px; border-radius:10px; box-shadow: 10px 10px 5px #888888;"
							src="${pageContext.request.contextPath}/image/o-FEMALE-CHEF-facebook.jpg">
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/fragment/newfooter.jsp" /> </section>
	<script>
		$('.sm-parallax-window')
				.parallax(
						{
							imageSrc : '${pageContext.request.contextPath}/image/1-1241886505MO2J.jpg'
						});
	</script>
	<script src='https://www.google.com/recaptcha/api.js' async defer></script>
</body>
</html>