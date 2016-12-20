<!--
   執行本網頁之前，會先執行_02_login.filter.FindUserPassword.java這個Filter。
        本網頁 login.jsp 提供登入的畫面，然後將userId與pswd傳給
   <Form>的action屬性為login.do，它對應的程式為_02_login.controller.LoginServlet.java
   ，由該Servlet來檢查userId與pswd是否正確。    -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
<script type="text/javascript">
	//由<body>的onLoad事件處理函數觸發此函數
	function setFocusToUserId() {
		document.forms[0].userId.focus(); // 將游標放在userId欄位內
	}
</script>
</head>

<body onLoad="setFocusToUserId()">
	<!-- 下列敘述設定變數funcName的值為LOG，top.jsp 會用到此變數 -->
	<c:set var="funcName" value="LOG" scope="session" />
	<c:set var="msg" value="Login" />
	<c:if test="${ ! empty sessionScope.timeOut }">
		<!-- 表示使用逾時，重新登入 -->
		<c:set var="msg"
			value="<font color='red'>${sessionScope.timeOut}</font>" />
	</c:if>
	<!-- 引入共同的頁首 -->
	<jsp:include page="/fragment/header.jsp" />
	<section class="software sm-parallax-window" data-parallax="scroll"
		style="margin:0 0; padding:0 0;">
	<div style="margin-bottom: 200px;">
		<div class="container">
			<div class="row login-from">
				<h1>
					<strong>Login</strong>
				</h1>
				<div class="col-md-8">
					<form action="<c:url value='login.do' />" method="POST"
						name="loginForm" role="form ">
						<table class="table table-hover sm-text"
							style="background: #fff; font-size: 20px; text-align: left; border-radius: 10px; box-shadow: 10px 10px 5px #888888;">
							<tr>
								<td
									style="border-radius: 10px; padding: 5px 15px; border: none;">
									<div class="form-group"
										style="padding: 5px 15px; border: none;">
										<label for="exampleInputEmail1">
											<h4 style="font-size: 30px;">
												<strong>Account</strong>&nbsp;&nbsp;<small><Font
													color='red' size="5"><strong>${ErrorMsgKey.AccountEmptyError}</strong></Font></small>
											</h4>
										</label><br> <input type="text" class="form-control"
											name="Account" value="${sessionScope.user}"
											placeholder="Insert your account"
											style="font-size: 30px; font-family: 微軟正黑體;">
									</div>
								</td>
							</tr>
							<tr>
								<td
									style="border-radius: 10px; padding: 5px 15px; border: none;">
									<div class="form-group"
										style="padding: 5px 15px; border: none;">
										<label for="exampleInputPassword1">
											<h4 style="font-size: 30px;">
												<strong>Password</strong>&nbsp;&nbsp;<small><Font
													color='red' size="5"><strong>${ErrorMsgKey.PasswordEmptyError}</strong></Font></small>
											</h4>
										</label><br> <input type="password" name="pswd"
											value="${sessionScope.password}" class="form-control"
											id="exampleInputPassword1" placeholder="Insert your password"
											style="font-size: 30px; font-family: 微軟正黑體;">
									</div>
								</td>
							</tr>
							<!-- 提供額外視覺上的重量和識別一組按鈕中主要的操作項目 -->
							<tr>
								<td
									style="padding: 0px 25px; padding-top: 30px; border-radius: 10px; border: none; text-align: center"><input
									type="submit" value="Login" class="btn btn-warning login-button"
									style="font-size: 20px; width: 120px;"> <a
									href="<c:url value='forgetPassword.jsp' />"><button
											type="button" class="btn btn-success login-button"
											style="font-size: 20px; width: 120px;">Forget Password</button></a> <a
									href="<c:url value='forgetAccount.jsp' />"><button
											type="button" class="btn btn-success login-button"
											style="font-size: 20px; width: 120px;">Forget Account</button></a> <br>
									<Font color='red' size="5">${ErrorMsgKey.LoginError}&nbsp;</Font><br>
									<c:if test="${! empty timeLeft}">
										<font id="errorEm" size="5" color="#FF0000" style="font-family:微軟正黑體;">Remaining attempts:
											${timeLeft }, up to 3 attempts, IP will be block for 3 hours</font>
									</c:if></td>


								<input type="hidden" name="fromRecipes" size="10"
									value="${param.fromRecipesDetail}">
								<input type="hidden" name="pageNo" size="10"
									value="${param.pageNo}">
								<input type="hidden" name="type" size="10" value="${param.type}">

							</tr>
						</table>
					</form>
				</div>
				<div class="col-md-4">
					<img
						style="height: 360px; width: 400px; margin-bottom: 30px; border-radius: 10px; box-shadow: 10px 10px 5px #888888;"
						src="${pageContext.request.contextPath}/image/restaurant-fine-dining.jpg">
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
</body>
</html>