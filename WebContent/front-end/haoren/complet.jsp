<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.pro.cart.model.*" %>
<%@ page import="com.pro.order.model.*" %>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
  
<!DOCTYPE html>
<html lang="en">

<!-- 	購物車 -->
	<% 
	@SuppressWarnings("unchecked")
	   Vector<Cart> buylist = (Vector<Cart>) session.getAttribute("shoppingcart");
	String amount =  (String) request.getAttribute("amount");
	OrderVO OrderVO = (OrderVO)request.getAttribute("OrderVO");
	
	MemVO memLogIn =(MemVO)session.getAttribute("memLogIn");
	CoachVO coachLogIn = (CoachVO)session.getAttribute("coachLogIn");
	String member_id = memLogIn.getMember_Id();
	System.out.println(member_id);

	session.setAttribute("member_id", member_id);
	
	   %>
	   
	   
	  

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Bootstrap後台管理</title>
        <link rel="stylesheet" href="https://hsiangfeng.github.io/ShoppingCart/css/custom.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css" integrity="sha384-/rXc/GQVaYpyDdyxK+ecHPVYJSN9bmVFBvjA/9eOB+pb3F2w2N6fc5qB9Ew5yIns"
            crossorigin="anonymous">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
            integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>


		<meta charset="utf-8">
	<title></title>

	   
	               <!-- Bootstrap 的 CSS -->
	
	<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/haoren/css/js/bootstrap.min.css">

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/haoren/css/fonts/font-awesome.css">

	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/haoren/G3header.css">
	<!-- 凱文 -->
	
    </head>
    <style>
        body {
              font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", "Microsoft JhengHei", Roboto, "Helvetica Neue", Arial, sans-serif 微軟正黑體
            }
            
            .background-header {
    background: rgba(250,250,250,0.99) !important;
    height: 80px!important;
    position: fixed!important;
    top: 0px;
    left: 0px;
    right: 0px;
    box-shadow: 0px 0px 10px rgba(0,0,0,0.15)!important;
    }
    .margin-top-100{
    margin-top: 100px;
    }
.alert-primary {
    color: #818182 !important ;
    background-color: #ffccccde !important;
    border-color: #ffb8b8 !important;
}

/* 粗體 */
	.font-thick{
	font-size: 20px;
    font-weight: 800;
   } 
   .card-header{
   display: flex;
    justify-content: center;
   }
   
   .address{
   display:none;
   }
   .credit{
   display:none;
   }
   .error{
       height: 50px;
   }
   

        </style>

    <body>
    <!-- header -->
     <%@ include file="../haoren/header.jsp"%>
	<!-- header -->

           
       
        <div class="container main-contant py-5">
            <h1 class="text-center mb-3 text-secondary margin-top-100">訂單成立</h1>
             <div class="form-row text-center">
                <div class="col-12 col-sm">
                    <div class="alert alert-light alert-rounded borderlen font-thick a" role="alert">
                        1.付款方式
                    </div>
                </div>
                <div class="col-12 col-sm">
                <div class="alert alert-light alert-rounded font-thick b" role="alert">
                        2.收件資訊
                    </div>
                </div>
                <div class="col-12 col-sm">
                    <div class="alert alert-primary alert-rounded font-thick" role="alert">
                        2.完成
                    </div>
                </div>
            </div>
            <div class="row justify-content-center py-5">
                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header" id="headingOne">                       
                            <button class="btn btn-link" data-toggle="collapse" data-target="#collapseOne"
                                aria-expanded="true" aria-controls="collapseOne">
                               顯示訂單
                            </button>
                        </div>
                        <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">
                            <table class="table table-sm text-center">
                                <thead>
                                    <th width="80px" class="text-center">訂單編號</th>
                                    <th width="80px" class="text-center">訂單狀態</th>
                                    <th width="80px" class="text-center">付款方式</th>
                                    <th width="80px" class="text-center">運送方式</th>                                    
                                    <th width="120px" class="text-center">訂單成立時間</th>
                                    <th width="80px" class="text-center">地址</th>
                                   
                                </thead>
                                <tbody>                               
                                    <tr>
                                        <td class="align-middle">${OrderVO.order_id}</td>                                        
                                  <!-- 訂單狀態 -->
											<c:choose>
											<c:when test="${(OrderVO.order_status)==0 }">	
												<td class="align-middle">已下單已付款</td>
		    								</c:when>	
		    								<c:when test="${(OrderVO.order_status)==1 }">	
												<td class="align-middle">已出貨</td>
		    								</c:when>	
		   								    <c:when test="${(OrderVO.order_status)==2 }">	
												<td class="align-middle">結單</td>
		   								    </c:when>				
											<c:otherwise>
    											<td class="align-middle">訂單取消</td>
   											</c:otherwise> 
											</c:choose>	
								 <!-- 訂單狀態 --> 
                                 <!-- 信用卡付款 -->
                                        <c:choose>
										<c:when test="${(OrderVO.order_pay)==0 }">	
										<td class="align-middle">信用卡付款</td>
		    							</c:when>				
										<c:otherwise>
    									<td class="align-middle">貨到付款</td>
   										</c:otherwise> 
										</c:choose>	
                                 <!-- 信用卡付款 -->                                
                                <!-- 運送方式 -->
										<c:choose>
										<c:when test="${(OrderVO.delivery)==0 }">	
											<td class="align-middle">超商取貨</td>
		    							</c:when>				
											<c:otherwise>
    									    <td class="align-middle">貨運寄送</td>
   											</c:otherwise> 
										</c:choose>	
								<!-- 運送方式 -->
                                        <td class="align-middle"><fmt:formatDate value="${OrderVO.order_date}" type="time" pattern="MM/dd hh:mm"/></td>
                                        <td class="align-middle">${OrderVO.order_address}</td>
                                    </tr>
                                    
                                    <tr>
                                       
                                    </tr>
                                    <tr>
                                        
                                    </tr>
                                    
                                </tbody>
                            </table>
                        </div>
                    </div>
                    

                        
                        <div class="text-right">
                        <span>前往會員中心查看訂單</span>
                            <a href="${pageContext.request.contextPath}/front-end/haoren/orderPage.jsp" class="btn btn-secondary">查看訂單</a>
                            
                        </div>
                        </div>
                </div>
            </div>
        </div>
        <!-- footer -->
    	<%@ include file="../haoren/footer.jsp"%>
		    <!-- footer -->
		
		
		

      
        
        
   
  		<!-- kevin -->
  		

        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
            crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.0/Chart.bundle.min.js"></script>
        
        <!-- 縣市選單用 -->
		<script src="https://cdn.jsdelivr.net/npm/tw-city-selector@2.1.0/dist/tw-city-selector.min.js"></script>
        <script>
 			new TwCitySelector({
 				el: '.city-selector-set',
 			    elCounty: '.county', // 在 el 裡查找 element
 			    elDistrict: '.district', // 在 el 裡查找 element
 			    elZipcode: '.zipcode' // 在 el 裡查找 element
 			});
 		</script>
         <!-- 縣市選單用 -->
        
        
        
        
        
        
        
        <script>
            $(document).ready(function () {
                //validated
                (function () {
                    'use strict';
                    window.addEventListener('load', function () {
                        // Fetch all the forms we want to apply custom Bootstrap validation styles to
                        var forms = document.getElementsByClassName('needs-validation');
                        // Loop over them and prevent submission
                        var validation = Array.prototype.filter.call(forms, function (form) {
                            form.addEventListener('submit', function (event) {
                                if (form.checkValidity() === false) {
                                    event.preventDefault();
                                    event.stopPropagation();
                                }
                                form.classList.add('was-validated');
                            }, false);
                        });
                    }, false);
                })();
                //removeModal
                $("#removeModal").on('show.bs.modal', function (event) {
                    var btn = $(event.relatedTarget);
                    console.log(btn);
                    var removeTitle = btn.data('title');
                    console.log(removeTitle);
                    var modal = $(this);
                    modal.find('.modal-title').text('確定要刪除' + removeTitle + '?')
                    console.log(modal);
                })
            });
           
//             地址滑動

$(document).ready(function(){
$(".delivery").change(function(){
	if($(this).val()=="0"){
		$(".address").slideDown();
		$(".conshop").slideUp();
		$(".must-address").attr("required","");
		}else if($(this).val()=="1"){
		$(".conshop").slideDown();
		$(".address").slideUp();
		$(".must-address").removeAttr("required","");
		}
  });
});

//付款方式


$(document).ready(function(){
$('.pay').change(function(){
if($(this).val()=="0"){
$(".credit").slideDown();
$(".must-card").attr("required","");
}else{
$(".credit").slideUp();
$(".must-card").removeAttr("required","");
}
});
});



            
            
            
        </script>
    </body>

</html>
