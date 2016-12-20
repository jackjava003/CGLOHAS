<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:useBean id="store" class="_07_storeInfo.model.StoreAccessDAO" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cooperator Detail</title>
<script src="${pageContext.request.contextPath}/javascript/jquery-3.1.0.min.js"></script>
<script src="${pageContext.request.contextPath}/javascript/jquery-ui.min.js"></script>
					  
<!-- <script type="text/javascript" -->
<!-- 	src="http://maps.google.com/maps/api/js?sensor=false&libraries=places"></script> -->
<!-- 
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
  -->

<!-- <script src="../js/jquery.googlemap.js"></script> -->
<style type="text/css">
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}

#map {
	height: 500px;
	width: 500px;
	margin-left: auto;
	margin-right: auto
}

#map_canvas {
	height: 700px;
	width: 500px;
	margin-left: auto;
	margin-right: auto
}
</style>


<script type="text/javascript">  

//透過JSON載入所有分店地點經緯度並在googlemap做marker
	 var centerX = 23.632358;
	 var centerY = 120.970506;
	 var map;
	 var infowindow;
	 
	 $(document).ready(function (){
		//在DOM(map_canvas)標籤中 造一個googleMap	
		  map = new google.maps.Map(document.getElementById('map_canvas'), {
		      zoom: 8,
		      center: new google.maps.LatLng(centerX, centerY),
		      mapTypeId: google.maps.MapTypeId.ROADMAP
		    });
		    infowindow = new google.maps.InfoWindow();
		    var marker, i;
		 				//從body內藏的input標籤(#placeJson)中取出並存成json物件
		 				var jsonObj = $.parseJSON($("#placeJson").val());
		 				//對取出的每個json物件內藏的<List>placeBeam取出經緯度做成可點擊顯示infowindow的Marker
		 				$.each(jsonObj, function(index, place) {
		 					for (i = 0; i < jsonObj.length; i++) {
		 						//做marker
		 					      marker = new google.maps.Marker({
		 					        position: new google.maps.LatLng(place.lat, place.longi),
		 					        map: map,
		 					        icon: '${pageContext.request.contextPath}/image/carrot3.png'
		 					      });
								//marker點擊跳出資訊視窗	
		 					      google.maps.event.addListener(marker, 'click', (function(marker, i) {
		 					        return function() {
		 					        	//設定infowindow內容
		 					          infowindow.setContent("<img height='50' width='50'src='${pageContext.servletContext.contextPath}/_00_init/getImage?id="+place.storeid+"&lid="+place.locationid+"&type=locationImg'>" + "<br>" + place.s_name + "<br>" + place.phone + "<br>" + place.address);
		 					          infowindow.open(map, marker);
		 					        }
		 					      })(marker, i));
		 					    }

		 		    		});		
		 });
	 
		 
		//USER點擊查詢單一分店按鈕(詳細地圖),並傳入經緯度,就執行
		function replaceMap(x , y, z, a) {
			var storeLatLng = new google.maps.LatLng(x, y);
			map.setCenter(storeLatLng);
			map.setZoom(16);	
		}
		
		//點擊搜尋我附近的店家		
// 		var taiwan = new google.maps.LatLng(23.632358, 120.970506);
		var taiwan = [23.632358, 120.970506];
		function searchNearBy(){
			
			 //Try W3C Geolocation (Preferred)定位功能
			 	//如果瀏覽器支援定位
			   if(navigator.geolocation) {
			     browserSupportFlag = true;
			     //得到目前位置
			     navigator.geolocation.getCurrentPosition(function(position) {
			    	 //存入initialLocation
			       var initialLocation = new google.maps.LatLng(position.coords.latitude,position.coords.longitude);
			      	//取得目前的map物件,設定中心點及縮放
			       map.setCenter(initialLocation);
			       map.setZoom(13);
			     },
			     function() {
			       handleNoGeolocation(browserSupportFlag);
			     });
			   }
			   // 如果瀏覽器不支援定位
			   else {
			     browserSupportFlag = false;
			     handleNoGeolocation(browserSupportFlag);
			   }
			   
			   function handleNoGeolocation(errorFlag) {
			     if (errorFlag == true) {
			       alert("Map Targeting failed");
			     } else {
			       alert("Your browser does not support location services");
			     }
			 	initialLocation = taiwan;
			     map.setCenter(initialLocation);
			   }
		}
		
		
	
	 </script>
</head>
<body style="position: realtive;" >

<!-- <body > -->
<!--   ... -->
<!--   <div class="navbar-example"> -->
<!--     <ul class="nav nav-tabs" role="tablist"> -->
<!--       ... -->
<!--     </ul> -->
<!--   </div> -->
<!--   ... -->
<!-- </body> -->





	<jsp:include page="/fragment/header.jsp" />
	<section style="padding:0 0; margin:0 0;">
	<div class="container-fuild si-parallax-window2">
		<div class="row"></div>
	</div>
	</section>

	<div class="si_bg">
		<div class="container">
			<div class="row">

				<div class="col-md-12"
					style="text-align: center; margin-bottom: 50px; padding-top: 50px;">
					<%-- 	<jsp:useBean id="store" class="_07_storeInfo.model.StoreAccessDAO" /> --%>
					<jsp:setProperty property="selectStoreID" name="store"
						value="${param.id}" />
					<!-- 	動態新增單一店家資訊 -->
					<c:forEach var="aStore" items="${store.selectOne}">
						<img height='170' width='360' style="margin-bottom: 5px;"
							src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${aStore.storeid}&type=storeImg'>
						<div>
							<button type="button" class="btn btn-success btn-lg"
								data-toggle="modal" data-target="#${aStore.storeid}">Store information</button>
								<button id='markNearBy' value='Search for stores near me' onClick='searchNearBy()' class="btn btn-warning btn-lg">Search for stores near me</button>
						</div>
						<div class="modal fade si_font" id="${aStore.storeid}"
							tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
							aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button>
										<h2 class="modal-title si_font1" id="myModalLabel">Info</h2>
									</div>
									<div class="modal-body">
										<h3 style="text-align: left;">${aStore.shortdesc}</h3>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-success"
											data-dismiss="modal">Close</button>

									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
				<div class="col-md-6">

					<c:forEach var="aStore" items="${store.selectLoc}">

						<div
							style="text-align: left; margin-bottom: 10px; font-family: '微軟正黑體';">

							<img height='150' width='150'
								src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${aStore.storeid}&lid=${aStore.locationid}&type=locationImg'>
							<div style="float: right; text-align: left; width: 400px;">
								<span id="NameInfoToJs${aStore.locationid}">Name:&nbsp;${aStore.s_name }</span><br>

								<span id="TelInfoToJs${aStore.locationid}">Phone:&nbsp;${aStore.phone }</span><br>

								<span id="AddressInfoToJs${aStore.locationid}">Address:&nbsp;${aStore.address }</span><br>
								
								<a class="page-scroll" href="#map1" ><Input type="button" name="location"
									value="Show on map" class="btn btn-warning"
									onClick="replaceMap(${aStore.lat }, ${aStore.longi },${aStore.storeid},${aStore.locationid})"></a>
								
							</div>

						</div>
					</c:forEach>
				</div>

				<%-- 		ID          &nbsp; storeid:   &nbsp;               ${aStore.storeid} <br> --%>
				<%-- 		店名  		&nbsp; storename: &nbsp;			   ${aStore.storename} <br> --%>





				<!-- 	動態新增單一店家所有分店資訊,並提供地圖標記定位按鈕 -->


				<%-- 		<c:forEach var="aStore" items="${store.selectLoc}"> --%>
				<!-- 			<img height='150' width='150' -->
				<%-- 				src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${aStore.storeid}&lid=${aStore.locationid}&type=locationImg'> --%>
				<!-- 			<br> -->
				<%-- 		LID 		&nbsp;	locationid:	${aStore.locationid }<br> --%>
				<%-- 		ID  		&nbsp;	storeid:	${aStore.storeid }<br> --%>
				<%-- 		分店名稱		&nbsp;	s_name:     ${aStore.s_name }<br> --%>
				<%-- 		經度			&nbsp;  lat:        ${aStore.lat }<br> --%>
				<%-- 		緯度      		&nbsp;  longi:      ${aStore.longi }<br> --%>
				<!-- 			<Input type="button" name="location" value="詳細地圖" -->
				<%-- 				onClick="replaceMap(${aStore.lat }, ${aStore.longi },${aStore.storeid},${aStore.locationid})"> --%>
				<!-- 			<br> -->
				<%-- 		地址			&nbsp;  address:    ${aStore.address }<br> --%>
				<%-- 			<span id="NameInfoToJs${aStore.locationid}">店名:&nbsp;${aStore.s_name }</span> --%>

				<%-- 			<span id="TelInfoToJs${aStore.locationid}">電話:&nbsp;${aStore.phone }</span> --%>

				<%-- 			<span id="AddressInfoToJs${aStore.locationid}">地址:&nbsp;${aStore.address }</span> --%>
				<!-- 			<br> -->



				<!--  
		<span  id="LatInfoToJs${aStore.locationid}">${aStore.lat }</span><br>
		<span  id="LongiInfoToJs${aStore.locationid}">${aStore.longi }</span><br>
		-->
				<!-- script 一產生就run 以下程式碼
		<link onload="var LatInfoToJs=${aStore.lat } ; var LongiInfoToJs=${aStore.longi }; markMap(LatInfoToJs, LongiInfoToJs);" ></link>
		-->
				<%-- 		</c:forEach> --%>
				
				<div id ="map1" ></div>
				<div class="col-md-6" >
					<div id="map_canvas"></div>
					<br> <input type="hidden" id='placeJson' value='${placeJson}' />
					<input type="hidden" id='id' value='${id}' />
				</div>
				
				</div>
			</div>
		</div>
		<div id="pfirst"
			style="text-align: center; padding-bottom: 50px; padding-top: 50px;">
			<a
				href="<c:url value='/_07_storeInfo/DisplayPageRecipes.do?${returnPage}'/>">Previous page</a>&nbsp;&nbsp;&nbsp;
			<a href='..'>Home page</a>
		</div>
	</div>






	<section style="padding-bottom:0px; padding-top:0px;">
	<div class="container-fuild si-parallax-window2">
		<div class="row"></div>
	</div>
	</section>



	<jsp:include page="/fragment/storefooter.jsp" />

	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBavOTDnzjQUVMRGV_buPcliPDgs67pnQs"
		type="text/javascript"></script>
</body>
</html>