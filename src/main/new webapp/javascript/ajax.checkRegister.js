var ajax = new XMLHttpRequest();

var acResult = 0;
var pwResult = 0;
var sexResult = 0;
var nameResult = 0;
var mobileResult = 0;
var mailResult =0;
var birthDResult = 0;
var pictureResult = 1;

function doFirst() {
	var myVar = setInterval(checkOK, 500);
	 var boy = document.getElementById('boy');
     var girl = document.getElementById('girl');
	//var passwordCheck = document.getElementById('password');
	password.addEventListener('blur',checkPw,false);
	account.addEventListener('blur',checkAc,false);
	cellphone.addEventListener('blur',checkPh,false);
	username.addEventListener('blur',checkName,false);
	mail.addEventListener('blur',checkMail,false);
	birthday.addEventListener('blur',checkBD,false);
	file1.addEventListener('change',checkImg,false);
	boy.addEventListener('click',updateSex,false);
	girl.addEventListener('click',updateSex,false);
}

function checkOK(){
	
	var okCount = 0;
	var response = grecaptcha.getResponse();
	if(response.length == 0){
    //reCaptcha not verified
	return;
	}else{
		//alert(response);
		okCount += acResult+pwResult+sexResult+nameResult
					+mobileResult+mailResult+birthDResult+pictureResult;
	}
	
	if(okCount==8){
		document.getElementById("submit").disabled = false;
	}else{
		document.getElementById("submit").disabled = true;
	}
	//alert(okCount)
}



function checkPw(){
	pwResult = 0;
	
	var pw = document.getElementById('password').value;
	if(pw== undefined || pw==null ||pw.trim().length == 0){ 
		document.getElementById('errorPw').innerHTML = ' 【Password must enter】';
	}else if(isPwValid(pw)==false){
		document.getElementById('errorPw').innerHTML = ' 【Password can not contain symbols and must between 8 and 16 characters in English and numerals】';
	}else{
		document.getElementById('errorPw').innerHTML = '<img style="height:25px; width:25px;" src="../image/png/success.png">';
		pwResult=1;
	}
}

function checkAc(){
	acResult=0;
	var ac = document.getElementById('account').value;
	if(ac== undefined || ac==null ||ac.trim().length == 0){ 
		document.getElementById('errorAc').innerHTML = ' 【Account must enter】';
	}else if(isAcValid(ac)==false){
		document.getElementById('errorAc').innerHTML = ' 【Account can not contain symbols and must between 8 and 16 characters in English and numerals】';
	}else{
		$.ajax({
			type : 'GET',
			url : "/CGLOHAS/_01_register/ajaxAccount.do?account=" + ac,
			success : function(obj) {
				if(obj!= 'OK'){
					document.getElementById('errorAc').innerHTML = " 【This account already exists, please change account】";
				}else{
					document.getElementById('errorAc').innerHTML = '<img style="height:25px; width:25px;" src="../image/png/success.png">';
					acResult = 1;
				}
			}
		})		
	}
}

function checkName(){
	nameResult = 0;
	var username = document.getElementById('username').value;
	if(username== undefined || username==null ||username.trim().length == 0){ 
		document.getElementById('errorName').innerHTML = ' 【Name must enter】';
	}else if(username.length > 8){
		document.getElementById('errorName').innerHTML = ' 【Name must be less than 8 characters】';
	}else{
		document.getElementById('errorName').innerHTML = '<img style="height:25px; width:25px;" src="../image/png/success.png">';;
		nameResult = 1;
	}	
}

function checkPh(){
	mobileResult=0;
	var cellphone = document.getElementById('cellphone').value;
	if(cellphone== undefined || cellphone==null ||cellphone.trim().length == 0){ 
		document.getElementById('errorCellphone').innerHTML = ' 【Mobile must enter】';
	}else if(isPhoneValid(cellphone)==false){
		document.getElementById('errorCellphone').innerHTML = ' 【Please confirm your Mobile number】';
	}else{
		$.ajax({
			type : 'GET',
			url : "/CGLOHAS/_01_register/ajaxMobile.do?cellphone=" + cellphone	,
			success : function(obj) {
				if(obj!= 'OK'){
					document.getElementById('errorCellphone').innerHTML = " 【This Mobile already exists, please change Mobile】";
				}else{
					document.getElementById('errorCellphone').innerHTML = '<img style="height:25px; width:25px;" src="../image/png/success.png">';;
					mobileResult = 1;
				}
			}
		})		
	}
	
}

function checkMail(){
	mailResult=0;
	var mail = document.getElementById('mail').value;
	if(mail== undefined || mail==null ||mail.trim().length == 0){ 
		document.getElementById('errorMail').innerHTML = ' 【E-mail must enter】';
	}else if(isEmailValid(mail)==false){
		document.getElementById('errorMail').innerHTML = ' 【Please confirm your E-mail】';
	}else{
		$.ajax({
			type : 'GET',
			url : "/CGLOHAS/_01_register/ajaxEmail.do?mail=" + mail,
			success : function(obj) {
				if(obj!= 'OK'){
					document.getElementById('errorMail').innerHTML = " 【This E-mail already exists, please change E-mail】";
				}else{
					document.getElementById('errorMail').innerHTML = '<img style="height:25px; width:25px;" src="../image/png/success.png">';;
					mailResult = 1;
				}
			}
		})		
	}
	
}

function checkBD(){
	birthDResult = 0;
	var birthday = document.getElementById('birthday').value;
	if(birthday== undefined || birthday==null ||birthday.trim().length == 0){ 
		document.getElementById('errorBirthday').innerHTML = ' 【BirthDate must enter】';
	}else if(isBirthDayValid(birthday)==false){
		document.getElementById('errorBirthday').innerHTML = ' 【Please confirm your BirthDate】';
	}else{
		document.getElementById('errorBirthday').innerHTML ='<img style="height:25px; width:25px;" src="../image/png/success.png">';;
		birthDResult = 1;
	}
}

function checkImg(){
	pictureResult =0;
	file = document.getElementById('file1').files[0];
	if(verifyPicFile(file.name)==false){
		document.getElementById('errorPic').innerHTML = ' 【Please confirm the file type】';
	}else{
		document.getElementById('errorPic').innerHTML = '<img style="height:25px; width:25px;" src="../image/png/success.png">';;
		pictureResult =1;
	}
}

function updateSex(){
	sexResult = 1;
	document.getElementById('getsex').innerHTML='<img style="margin-bottom:10px;  height:25px; width:25px;" src="../image/png/success.png">';
}

function isPwValid(pw){
	var regularExpression  = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}$/;
	return regularExpression.test(pw);
}

function isAcValid(ac){
	var regularExpression  = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{8,16}$/;
	return regularExpression.test(ac);	
}

function isPhoneValid(ph){
	var regularExpression  = /(04)+[\d]{8}$/;
	return regularExpression.test(ph);	
}

function isEmailValid(em){
	var regularExpression  = /^[_a-z0-9-]+([._a-z0-9-]+)*@[a-z0-9-]+([.a-z0-9-]+)*$/;
	return regularExpression.test(em);	
}

function isBirthDayValid(bd){
	var regularExpression  = /^([0-9]{4})\-([0-9]{2})\-([0-9]{2})$/;
	return regularExpression.test(bd);	
}

function verifyPicFile(fileName) {

	var nameIndex = fileName.lastIndexOf(".");
	var result = false;
	if (nameIndex != -1) {
		fileName = fileName.substring(nameIndex);
		var types = [ ".png", ".jpg", ".gif", ".jpeg" ];
			for (x in types) {
				if (fileName.toUpperCase() == types[x].toUpperCase()) {
					result = true;
				}
			}
		}
	return result;
}

window.addEventListener('load', doFirst, false);