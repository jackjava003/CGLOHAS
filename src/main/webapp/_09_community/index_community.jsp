<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="Shortcut Icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/logo.jpg">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>C.G.LOHAS</title>
</head>
<body>
<jsp:include page="/fragment/communityHeader.jsp" />
  <section style="margin: 0 0;padding: 0 0;">
        <div class="container-fuild">
            <div class="row">
                <div class="col-md-12">
                    <div class="co-bg">
                        <div class="co-bg1">  </div>
                            <div class="co-text">
                                <h1 class="ab_us_8" style="font-size:36px;"><strong>C.G. LOHAS</strong></h1>
                                <br>
                                <h4 class="ab_us_9"><strong>A meal, to create a new relationship</strong></h4>
                                <h4 class="ab_us_10"><strong>A meal, a hundred kinds of imagination</strong></h4>
                                <h4 class="ab_us_11"><strong>Sharing the joy of cooking</strong></h4>
                            </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <div class="co-poster">  
    <section class="re-section" style="margin-top: 30px;">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="re-h1-bg ab_us_12">
                        <div class="re-h1-bg1">
                            <h1 class="re-h1  ab_us_13"><strong>Operation Guide<br><br>Please select the category first</strong></h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section class="re-section ">
        <div class="container">
            <div class="row">
                <div class="cou31 ab_us_13">
                    <div class="col-md-12 re-cou"></div>
                    <div class="col-md-6 re-null">
                    <a  href="${pageContext.request.contextPath}/_09_community/Holder.jsp">
                        <div class="re-icon2 img-circle">
                            <h3 class="re-text"><strong>Become HOST</strong></h3>
                        </div>
                       </a>
                    </div>
                    <div class="col-md-6 re-null">
                        <a  href="${pageContext.request.contextPath}/_09_community/HolderList.jsp">
                            <div class="re-icon1 img-circle">
                                <h3 class="re-text"><strong>HOST List</strong></h3>
                            </div>
                       </a>
                    </div>
                </div>
            </div>
        </div>
         </section>




<jsp:include page="/fragment/newfooter.jsp" />
</div>
    <script>
        $('.co-poster').parallax({imageSrc: '${pageContext.request.contextPath}/image/mutou_qiangbi-010.jpg'});
        

    </script>

</body>
</html>