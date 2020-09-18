<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.pro.cart.model.*" %>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
  
<!DOCTYPE html>
<html lang="en">

<!-- 	購物車 -->
	<% @SuppressWarnings("unchecked")
	   Vector<Cart> buylist = (Vector<Cart>) session.getAttribute("shoppingcart");
	String amountStr = (String) session.getAttribute("amountStr");
	System.out.println("amountStr=" + amountStr);
	int amountInt ;
	if(amountStr != null && !"".equals(amountStr)){
	 amountInt = new Integer (amountStr.trim());		
	}else{
		 amountInt= 0 ;	}
	System.out.println("amountInt=" + amountInt);
	session.setAttribute("amountStr",amountStr);
	session.setAttribute("amountInt",amountInt);
	
	
	
String lochead = "https://namisaikou.tk" + request.getContextPath();
System.out.println("lochead=" + lochead);

String loca = lochead + "/front-end/haoren/cradi_checkout.jsp";
System.out.println("loca=" + loca);
request.setAttribute("loca", loca);

// 會員
MemVO memLogIn =(MemVO)session.getAttribute("memLogIn");
CoachVO coachLogIn = (CoachVO)session.getAttribute("coachLogIn");
String member_id = memLogIn.getMember_Id();
System.out.println(member_id);

session.setAttribute("member_id", member_id);



MemVO memVO =(MemVO)session.getAttribute("memLogIn");
	   %>
	   
	   
	   <%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message.value}</li>
		</c:forEach>
	</ul>
</c:if>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Bootstrap後台管理</title>
        
	
	
	
	
    


		<meta charset="utf-8">
	<title></title>
<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>

 <link rel="stylesheet" href="https://hsiangfeng.github.io/ShoppingCart/css/custom.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css" integrity="sha384-/rXc/GQVaYpyDdyxK+ecHPVYJSN9bmVFBvjA/9eOB+pb3F2w2N6fc5qB9Ew5yIns"
            crossorigin="anonymous">
     
     
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
    color: #004085;
    background-color: #ed563ba8;
    border-color: #ed563ba8;
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
   .map{
   display:none;
    position: relative;
    right: 21%;
   }
   
   .error{
       height: 50px;
   }
     /*  商品彈窗        */
  .modal-xl{
 height:900px;
 margin-left:-300px;
 width: 1200px; 
  } 
  
 .shop{
  position: relative;	
      top: -3px;
  }
 
 .btn-link {
    color:#ed563ba8 !important;
    font-weight: bold !important;
    font-size: 25px !important;
}
 
.alert-primary {
    color: #818182 !important ;
    background-color: #ffccccde !important;
    border-color: #ffb8b8 !important;
}
  .name{
    
    padding-left: 0px !important;
  
  }
  .cradi{
  position: absolute;
    bottom: 53px;
    left: 194px;
  }
  
  .zipcode{
  width: 90px;
  }
 

    </style>
 
 
  
  
  
  
 
  
  
  

    <body>
   	
  <!-- header -->
     <%@ include file="../haoren/header.jsp"%>
	<!-- header -->
       
       
        <div class="container main-contant py-5">
            <h1 class="text-center mb-3 text-secondary margin-top-100"><b>商品結帳</b></h1>
            <div class="form-row text-center">
                <div class="col-12 col-sm">
                    <div class="alert alert-primary alert-rounded borderlen font-thick a" role="alert">
                        1.付款方式
                    </div>
                </div>
                <div class="col-12 col-sm">
                <div class="alert alert-light alert-rounded font-thick b" role="alert">
                        2.收件資訊
                    </div>
                </div>
                <div class="col-12 col-sm">
                    <div class="alert alert-light alert-rounded font-thick" role="alert">
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
                                顯示購物車細節
                            </button>
                        </div>
                        <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">
                            <table class="table table-sm text-center">
                                <thead>
                                    <th width="30px"></th>
                                    <th width="100px"></th>
                                    <th class="text-center">商品名稱</th>
                                    <th width="100px" class="text-center">數量</th>
                                    <th width="80px" class="text-center">小計</th>
                                </thead>
                                <tbody>
                                 <%
               						 int index_del = 0;
                									if (buylist != null && (buylist.size() > 0)) {
                				
							 for (int index = 0; index < buylist.size(); index++) {
							Cart order = buylist.get(index);
							index_del = index;
						%>
                                    <tr>
                                        <td class="align-middle">
                                            <a href="#" data-toggle="modal" data-target="#removeModal" data-title="這個商品"><i
                                                    class="far fa-trash-alt fa-1x text-danger delete-button"></i></a>
                                        </td>
                                        <td class="align-middle">
                                            <img src="<%=request.getContextPath() %>/ProImg?name=PRODUCT_Lpic&PRODUCT_ID=<%=order.getProduct_Id()%>"
                                                height="100px" width="100px">
                                        </td>
                                        <td class="align-middle"><%=order.getProduct_Name()%></td>
                                        <td class="align-middle"><%=order.getOrder_buymount()%>件</td>
                                        <td class="align-middle text-right">$<%=order.getProduct_Price()%></td>
                                    </tr>
                                     <%}%>
                                    <tr>
                                        <td colspan="4" class="text-right">運費</td>
                                        <td class="align-middle text-right">
                                     
                                     
                                     
                 <c:choose>
								 <c:when test="${amountInt >= 3000}">								
								  <strong>免運</strong>
								 </c:when>								 
								 <c:otherwise>
								  <strong>$60 </strong>
								 </c:otherwise>							
				</c:choose>   
                                        
                                
                                            
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="4" class="text-right">合計</td>
                                        <td class="align-middle text-right">
                                            <strong>$${amountInt}</strong>
                                        </td>
                                    </tr>
                                    <%}%> 
                                </tbody>
                            </table>
                        </div>
                    </div>
                    </div>
                    
<div class="error">
             <%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message.value}</li>
		</c:forEach>
	</ul>
</c:if>
</div>

 					
                    
                  <form name="checkoutForm" action="<%=request.getContextPath()%>/front-end/order.do" method="POST" style="width: 730px;">
                    <div class="needs-validation mt-3">
                        <div class="form-row">
                        <!--付款方式 -->
                            <div class="form-group col-md-6">
                                <label for="inputEmail">付款方式</label>
                                <div>
                                <select class="pay form-control" name="order_pay">
                                	<option value="1">貨到付款</option>
                                	<option value="0" class="card-op">信用卡付款</option>　									
                                </select>
                                </div>
                            </div>
                            <!--付款方式 -->
                         	<!--取貨方式 -->
                            <div class="form-group col-md-6 delivery-menu" name="delivery">
                             <label for="inputEmail">取貨方式</label>
                              <select class="delivery form-control" name="delivery">
                              		<option value="0">超商取貨</option>　
                                	<option value="1">貨運寄送</option>
                                </select>            
							</div>
                            </div>
                        <!--取貨方式 -->
                        <!-- 地址 -->
                        
                        <div class="address" id="address">
                        <div class="form-group col-md-4 name ">
                                <label for="name">姓名</label>
                                <input type="text" class="form-control" id="name" placeholder="姓名" name="Buy_Name" >
                            </div>
                        <div class="form-row row city-selector-set">
						<!--縣市 -->
                            <div class="form-group col-md col-md-4 col-sm-12">
                                <label for="address1">縣市</label>
                                <span id="errorMsgs"></span> <%--錯誤驗證未完成 --%>
                               <fieldset>
							    	<!-- 縣市選單 -->
							    	<select class="county custom-select"></select>
								</fieldset>  
                                <div class="invalid-feedback"></div>
                            </div>
                            
                            <div class="form-group col-md col-md-4 col-sm-12">
                                <label for="address2">地區</label>
                                <fieldset>
							    	<!-- 區域選單 -->
							    	<select class="district custom-select"></select>
						    	</fieldset>
                                <div class="invalid-feedback"></div>
                            </div>
<!--                             郵遞區號 -->
                            <div class="form-group  col-md-4 col-sm-12">
                                <label for="number" required>郵遞區號</label>
                                <fieldset>
								    <!-- 郵遞區號欄位 (建議加入 readonly 屬性，防止修改) -->
								    <input class="zipcode" type="text" size="3" name="zipcode" readonly placeholder="郵遞區號">
								</fieldset>
                                <div class="invalid-feedback"></div>
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group col-md-12">
                                <label for="inputCity">地址</label>
                                <input type="text" class="form-control must-address" id="inputCity"  name="order_address" >
                                
                              </div>
                            </div>
                        </div>
                        <!-- 地址 -->
                        
                        <!--便利商店 -->
                        <div class="conshop">
                         <div class="form-row" style="width: 169%;">
                            <div class="form-group col-md-3">
                            	<label for="inputCity">請輸入便利商店店名</label>
                               <input type="text" class="form-control must-shop" id="address_shop" name="shop_address" required="required">
                               </div>
                               <div class="form-group col-md-6" style="margin-top: 35px;">
								<button class="btn btn-secondary shop" type="button" >查詢店名</button>                               </div>                               
                               <div class="form-group col-md-12 map">
                               <%@ include file="../haoren/googlemap.jsp"%>
                               </div>
                              </div>
                            </div>
						<!--便利商店 -->

                       
                       
                        
                        
                        
                        
                        <div class="text-right action_change">
                        	<button type="button" id="magic" class="btn btn-secondary">一鍵掃蕩</button>
                        	<button type="button"  class="btn btn-secondary com-pay">付款確認</button>
                            <a href="${pageContext.request.contextPath}/front-end/haoren/list-all-product.jsp" class="btn btn-secondary">繼續選購</a>
                            <button class="btn btn-secondary" type="submit" >訂單成立</button>
                            <input type="hidden" name="order_status" value="0">
                            <input type="hidden" name="action"  value="insert">
                            <input type="hidden" name="order_fee" value="60">
                            <input type="hidden" name="delivery" value="1">
                            <input type="hidden"  name="member_id" value="${member_id}">           
                            
                        </div> 
                                            
                        </div>
                     </form>
                      
                </div>
               			 <!-- 信用卡  綠界-->
                        <div class="credit">
                         <div class="form-row">
                        <div class="form-group col-md-2">
                        		<form name="creditForm" action="<%=request.getContextPath()%>/front-end/order.do" method="POST" >
                                <button class="btn btn-secondary cradi" type="submit">信用卡付款頁面</button>
                                <input type="hidden"  name="member_id" value="${member_id}">                                
                                <input type="hidden"  name="action" value="so_checkout">                                
                                <input type="hidden"  name="amountStr" value="${amountStr }">                                
                                <input type="hidden"  name="loca" value="${loca }">                                
                                </form>
                              </div>
                         
                        </div>
                        <!-- 信用卡 --> 
                     
            </div>
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
          
        
        
        
        <!-- footer -->
    	<%@ include file="../haoren/footer.jsp"%>
		<!-- footer -->
		
		
		

      <!-- 刪除方塊 -->
  
 		<div class="modal fade" id="removeModal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel"
            aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-danger">
                        <h5 class="modal-title text-white" id="removeModalLabel">刪除視窗</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        您確定要刪除該商品?
                    </div>
                      <form name="deleteForm" action="Shopping.html" method="POST">
             		 <input type="hidden" name="del" value="<%= index_del %>">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">取消</button>
                         <button type="submit" class="btn btn-outline-danger">刪除</button> 
                         	 <input type="hidden" name="action" value="PAY_DELETE">                      
                    </div>
                     </form>
                </div>
            </div>
        </div>   
             <!-- 刪除方塊 -->
        
        
   
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
	if($(this).val()=="1"){
		$(".address").slideDown();
		$(".conshop").slideUp();
		$(".must-address").attr("required","");
		$(".must-shop").removeAttr("required","");
		}else if($(this).val()=="0"){
		$(".conshop").slideDown();
		$(".address").slideUp();
		$(".must-address").removeAttr("required","");
		$(".must-shop").attr("required","");
		}
  });
});

//付款方式


$(document).ready(function(){
$('.pay').change(function(){
if($(this).val()=="0"){
$(".credit").slideDown();
$('.delivery-menu').hide();
$(".conshop").slideUp();
$(".address").slideUp();
$('.com-pay').hide();

}else{
$(".credit").slideUp();
$('.delivery-menu').slideDown();
$(".conshop").slideDown();
$('.com-pay').slideDown();
}
});
});

$(document).ready(function(){
	$('.com-pay').click(function(){	
	$(".card-op").remove();
	$(".delete-button").remove();
	$(".credit").slideUp();
	$(".a").removeClass("alert-primary");
	$(".b").addClass("alert-primary");

	
	});
	}); 


//google map


$(document).ready(function(){
$('.shop').click(function(){
$('.map').slideToggle();
});
});


//一鑑搞定
$(document).ready(function(){
	$("#magic").click(function(){
		$("#name").val("JAVA館館長");		
		$("#inputCity").val("崇德二路二段218號成吉思汗 台中旗艦館");
	})
});



        </script>
        
        
        
        
   
        
        
        <!-- Bootstrap -->
		<script src="${pageContext.request.contextPath}/front-end/haoren/css/js/popper.js"></script>


		<!-- Plugins -->
		<script src="${pageContext.request.contextPath}/front-end/haoren/css/js/scrollreveal.min.js"></script>
		<script src="${pageContext.request.contextPath}/front-end/haoren/css/js/waypoints.min.js"></script>
		<script src="${pageContext.request.contextPath}/front-end/haoren/css/js/jquery.counterup.min.js"></script>
		<script src="${pageContext.request.contextPath}/front-end/haoren/css/js/imgfix.min.js"></script> 
		<script src="${pageContext.request.contextPath}/front-end/haoren/css/js/mixitup.js"></script> 
		<script src="${pageContext.request.contextPath}/front-end/haoren/css/js/accordions.js"></script>

		<!-- Global Init -->
		<script src="${pageContext.request.contextPath}/front-end/haoren/css/js/custom.js"></script>
		
		
		 
    </body>

</html>
