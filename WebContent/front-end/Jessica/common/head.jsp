<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">    

<!-- CSS Files -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/front-end/Jessica/static/css/bootstrap.min.css"> <!-- v4.3.1 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/front-end/Jessica/static/css/font-awesome.css"> <!-- v4.7.0 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/front-end/Jessica/static/css/templatemo-training-studio.css"> <!-- 主題CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/front-end/Jessica/static/css/sweetalert2.css"> <!-- v6.10.3 -->
<!-- JS Files -->
<!-- jQuery -->
<script src="${pageContext.request.contextPath}/front-end/Jessica/static/js/jquery-2.1.0.min.js"></script>
<!-- Bootstrap -->
<script src="${pageContext.request.contextPath}/front-end/Jessica/static/js/popper.js"></script>
<script src="${pageContext.request.contextPath}/front-end/Jessica/static/js/bootstrap.min.js"></script>
<!-- Plugins -->
<script src="${pageContext.request.contextPath}/front-end/Jessica/static/js/scrollreveal.min.js"></script>
<script src="${pageContext.request.contextPath}/front-end/Jessica/static/js/waypoints.min.js"></script>
<script src="${pageContext.request.contextPath}/front-end/Jessica/static/js/jquery.counterup.min.js"></script>
<script src="${pageContext.request.contextPath}/front-end/Jessica/static/js/imgfix.min.js"></script> 
<script src="${pageContext.request.contextPath}/front-end/Jessica/static/js/mixitup.js"></script> 
<script src="${pageContext.request.contextPath}/front-end/Jessica/static/js/accordions.js"></script>    
<!-- Global Init -->
<script src="${pageContext.request.contextPath}/front-end/Jessica/static/js/custom.js"></script>
<!-- sweetalert2 v6.10.3 -->
<script src="${pageContext.request.contextPath}/front-end/Jessica/static/js/sweetalert2.js"></script>

<style>
    /*nav下拉*/
    .dropdown-menu li:hover .sub-menu {visibility: visible;}
    .dropdown:hover .dropdown-menu {display: block;}
    /*調整footer*/
    #bg-video{
        min-height: 30vh;
        max-height: 30vh;
    }
    .main-banner .caption{
        top: 60%;
    }
    .main-banner .caption h2 {
        font-size: 40px;
    }
    .video-overlay {
        bottom: 0;
    }
    /*副標的麵包屑*/
    .breadcrumb {
        background-color: transparent;
        padding: 0;
        margin-bottom: 0;
    }
    .breadcrumb a{
        color: white;
    }
    .breadcrumb-item.active {
        color: lightgray;
    }
    /*字體主題色*/
    .theme-colors{
        color: #ed563b;
    }
	/*調整區塊間的高度*/
	.section-heading {
        margin-top: 20px;
        margin-bottom: 10px;
    }
    /*調整內容上距*/
    #section-content{
        margin-top: 50px;
    }
    /*回到最上面*/
    .back-to-top{
        font-size: 30px;
        color: #ed563b;
    }
    #back-to-top{
        position: fixed;
        bottom: 10vh;
        right: 20vh;
    }
    .btn-circle-xl {
        width: 60px;
        height: 60px;
        padding: 10px 16px;
        font-size: 24px;
        line-height: 1.33;
        border-radius: 35px;
        border: 1px solid #ed563b;
    }
</style>