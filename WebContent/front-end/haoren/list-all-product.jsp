<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.pro.model.*"%>
<%@ page import="com.product_category.model.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.pro.cart.model.*" %>
<%@ page import="com.profav.model.*" %>
<%@ page import="com.profav.controller.*" %>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>



<% 

ProductService productService = new ProductService();
String Category_Id =request.getParameter("Category_Id");
System.out.println( "Category_Id=" +  Category_Id);

// System.out.println(Category_Id);
// System.out.println(Category_Id == (null));
List<ProVO> list = productService.getAllByCat(Category_Id);
pageContext.setAttribute("list", list);	
String cat;
if(Category_Id == null){
	
	cat = "all";
}else{
	cat = Category_Id;
}

pageContext.setAttribute("cat", cat);	


	//類別
	CategoryService CatService = new CategoryService();
	List<Product_categoryVO> list_cat = CatService.getAll();
	Iterator<Product_categoryVO> list_cat_it = list_cat.iterator();	
	
	//類別
	
// 	購物車 
	 @SuppressWarnings("unchecked")
	   Vector<Cart> buylist = (Vector<Cart>) session.getAttribute("shoppingcart");
	 session.setAttribute("buylist",buylist);
	 int listnum = 0;
if(buylist ==null){
	listnum = 0;	
}else if(buylist !=null){
	listnum = buylist.size();	
}
//	購物車 
//會員
MemVO memLogIn = (MemVO)session.getAttribute("memLogIn");
CoachVO coachLogIn = (CoachVO)session.getAttribute("coachLogIn");
	 
	 boolean islog = false;
	 String member_id = null;
if((MemVO)session.getAttribute("memLogIn")!=null){
	System.out.println((MemVO)session.getAttribute("memLogIn"));
			islog = true;
			MemVO MemVO =(MemVO)session.getAttribute("memLogIn");
			     MemService memsvc = new MemService();
			     MemVO memVO = memsvc.getOneMem(MemVO.getMember_Id());
			     session.setAttribute("memVO", memVO);
			    member_id = memVO.getMember_Id();
}
request.setAttribute("member_id", member_id);
request.setAttribute("islog", islog);
String requestURL = request.getRequestURI();
request.setAttribute("requestURL",requestURL);
	 %> 
	
	
	  
<jsp:useBean id="favorSvc" scope="page" class="com.profav.model.Product_FavoriteService" />
<jsp:useBean id="categoryService" scope="page" class="com.product_category.model.CategoryService" />

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title></title>
	
<!-- 	sweetalert -->
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>

	<!-- Bootstrap 的 CSS -->
	
	<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/haoren/css/js/bootstrap.min.css">

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/haoren/css/fonts/font-awesome.css">

	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/haoren/G3header.css">
	<!-- 凱文 -->
	
	<!-- 紹威 -->
	
	     <!-- favicon -->
     <link rel=icon href="<%=request.getContextPath()%>/front-end/haoren/assets/img/favicon.png sizes="20x20" type="image/png">
    <!-- animate -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/haoren/assets/css/animate.css">
    <!-- bootstrap -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/haoren/assets/css/bootstrap.min.css">
    <!-- magnific popup -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/haoren/assets/css/magnific-popup.css">
    <!-- Slick -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/haoren/assets/css/slick.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/haoren/assets/css/slick-theme.css" />
    <!-- owl carousel -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/haoren/assets/css/owl.carousel.min.css">
    <!-- fontawesome -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/haoren/assets/css/font-awesome.min.css">
    <!-- flaticon -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/haoren/assets/css/flaticon.css">
    <!-- hamburgers -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/haoren/assets/css/hamburgers.min.css">
    <!-- nice select -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/haoren/assets/css/nice-select.css">
    <!-- Main  -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/haoren/assets/css/style.css">
     <!-- responsive  -->
     <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/haoren/assets/css/responsive.css">
	<!-- 紹威 -->
	
	

	<!-- 購物車 -->
	
	  <link rel="stylesheet" href="https://hsiangfeng.github.io/ShoppingCart/css/custom.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css" integrity="sha384-/rXc/GQVaYpyDdyxK+ecHPVYJSN9bmVFBvjA/9eOB+pb3F2w2N6fc5qB9Ew5yIns"
            crossorigin="anonymous">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
            integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
<!--       


    </head>
    

<!-- 購物車 -->

<style> 

 .row shopping-slider-item text-center{
   width:100px;;
  }  
  body{
    font-family: "微軟正黑體";
}
.btn-cart{
    
    position: relative;
}
.dropdown-menu-right{
    position: absolute;
    right: 0;
    left: auto;
}
.btn-cart .badge{
    position: absolute;
    top: -1px;
    right: -1px;
}

.jumbotron-bg{
    background-image: url(https://images.unsplash.com/photo-1465199549974-7d82de6e2830?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1489&q=80);
    background-position: center 0;
    background-size: cover;
    min-height: 400px;
}
.jumbotron-text{
    background-color: rgba(0,0,0, 0.25);
}
.box-shadow{
     box-shadow: 1px 0px 5px 3px #862e1e26;
    cursor: pointer;
    transition: 0.5s;
}
.box-shadow1:hover{
    transform: scale(1.1,1.1);
}
.btn.disabled{
    pointer-events: none;
}
.alert-rounded{
    border-radius: 50px;
}
.card-bottom{
    border-bottom: solid 1.5px #eee !important;
}
.fa-trash-alt{
    color: #000;
}
.fa-trash-alt:hover{
    color: #f77754;
}
.cart-div{
	margin-top:50px;
	margin-right:50px;
	position:fixed;
	top:50px;
	right:50px;	
/* 	圖層優先度 */
	z-index:3; 
	
}
.btn btn-cart btn-sm{
	width:50px;
}
/* 購物車圖片 */
.cart-img{
width:100px;
height:100px;
}


.badge-danger{
background-color:#ff5105c4;
}


.search-menu ul { 
   margin: 0; 
   } 
   .search-menu ul li { 
     float: left; 
     color: #161616; 
     list-style: none; 
     margin-right: 20px; 
     } 
     .search-menu ul li a { 
       -webkit-transition: all .3s; 
       -o-transition: all .3s; 
       transition: all .3s; 
       } 
       .search-menu ul li a:hover, .search-menu ul li a.active { 
         color: #ed563b; 
         border-bottom: 1px solid #ed563b; 
         font-size: 20px; 
        font-weight: bolder; 
   } 
        .detail-win:hover, .detail-win.active {  
          color: #ed563b;  
         border-bottom: 1px  #ed563b;  
          font-size: 20px;  
         font-weight: bolder;  
    }  


/*透明度-要用就加在class */
 .opacity{
 opacity:0.8;
 }
 
/*  all超連結 */
 a{
 color:#ed563ba8;
 }
 
 
 .cart{ 
 	position: fixed;
 	top:120px;
    right: 50px;    
    z-index:3;
    
  }
  .cart-page{
   position: fixed;
  z-index:3;
  }
  
  /*圖片*/
   .course-img{
      width: 100%;
      height: 0;
      padding-bottom: 200px;
      position: relative;
      overflow: hidden;
        }
    .course-img img{
      position: absolute;
      left: 0;
      display: block;
      min-width: 100%;
      min-height: 100%;
      transform:translate(-50%,-50%);
        }
   .course-img-pro{
      width: 100%;
      height: 0;
      padding-bottom: 200px;
      position: relative;
      overflow: hidden;
        }
    .img-pro{
      position: absolute;
      left: 0;
      display: block;
      min-width: 100%;
      min-height: 100%;
        }
        
/*         彈窗img */
        .win-img{
      width: 300px;
      height: 0;
      padding-bottom: 300px;
      position: relative;
        }
    .win-img img{
   position: relative;
    top: 0%;
    left: -10%;
    display: block;
    min-width: 100%;
    min-height: 100%;
        }
 
/*  商品彈窗        */
  .modal-xl{
 height:1000px;
 margin-left:-300px;
 width:1200px;
  } 
   
  .float-left{
  display:inline-block;
  }   
  
  .pro-win{   
      width: 400px;
      height: 0px;
      padding-bottom: 300px;
      position: relative;
      overflow: hidden;
        
  }
  #proname{
  text-align:center;
  } 
  #NT{
  text-align:left;
     vertical-align: top;
    font-size: 14px;
    margin-right: 5px;
    color: #ea0000;
    font-family: Arial;
    font-weight: bold;
    margin-top: 10px;
  }
  #proprice{
  text-align:left;
    font-family: 'lativAbweb','Century Gothic','Tw Cen MT Condensed';
    font-weight: bold;
    font-size: 40px;
    line-height: 1;
    color: #ea0000;
  }
  #prostock{
  text-align:left;
  float:right;
  }
 /*  商品彈窗        */     
        
        
        
   body {
      font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", "Microsoft JhengHei", Roboto, "Helvetica Neue", Arial, sans-serif 
    }


/*     商品彈窗按鈕 */
    .btn-link {
    font-weight: 900;
    color: #ed563ba8;
    background-color: transparent;
} 
  /*     商品彈窗按鈕 */
  
  .btn-primary {
    color: #fff;
    background-color: #f3907e !important;
    border-color: #007bff;
}

/* 愛心特效 */

.HeartAnimation {
	position: absolute;
	background-image: url(<%=request.getContextPath()%>/front-end/haoren/images/web_heart_animation_edge.png);
	background-position: left;
	background-repeat: no-repeat;
	/*background-size: 2900%;*/
	height: 100px;
	width: 100px;
	right: -20%;
    bottom: -80px;
	-ms-transform: translate(-50%, -47.5%);
	transform: translate(-50%, -47.5%)
}

.like-active {
	animation-timing-function: steps(28);
	animation-name: heart-burst;
	animation-duration: .8s;
	animation-iteration-count: 1;
	display: inline-block;
	animation-fill-mode: forwards;
}

.HeartAnimation :hover {
background-position: 3.571428571428571%
}
 @keyframes heart-burst {
            0% {
                background-position: left
            }

            100% {
                background-position: right
            }
        }
/*    愛心特效  */

  .con{
  	border:soild 1px;
  }
  
  .modal-title{
  font-size:30px;
  }
  
  .font-30{
  font-size:30px !important;
  }
/*   加入購物車按鈕 */
  .shop-single-item .top-content .hover-content .btn {
    background: #ed563ba8;
    color: #fff;
}
/* 分頁 */
.pagination-area ul li span.current, .pagination-area ul li span:hover {
    background-color: #ed563ba8;
    color: #fff;
}
/* 類別標題 */
.shop-single-item .bottom-content .subtitle {
    font-size: 16px;
    color: #678f57;
    font-weight: bolder;
}


/* 旋轉 */
/* .flip3D > .front{ */
/*  position:absolute; */
/*  transform: perspective( 600px ) rotateY( 0deg ); */
/*  background:#FC0; width:240px; height:200px; border-radius: 7px; */
/*  backface-visibility: hidden; */
/*  transition: transform .5s linear 0s; */
/* } */
/* .flip3D > .back{ */
/*  position:absolute; */
/*  transform: perspective( 600px ) rotateY( 180deg ); */
/*  background: #80BFFF; width:240px; height:200px; border-radius: 7px; */
/*  backface-visibility: hidden; */
/*  transition: transform .5s linear 0s; */
/* } */
/* .flip3D:hover > .front{ */
/*  transform: perspective( 600px ) rotateY( -180deg ); */
/* } */
/* .flip3D:hover > .back{ */
/*  transform: perspective( 600px ) rotateY( 0deg ); */
/* } */

/* 旋轉 */


.zoom-in{
transform:scale(1,1);
transition: all 0.3s ease-out;
z-index:3;
box-shadow: 1px 0px 5px 3px #862e1e26;
    height: 300px;

}

.zoom-in:hover{
 	transform:scale(1.5,1.5);
}
.zoom-in-pro{
transform:scale(1,1);
transition: all 0.3s ease-out;
box-shadow: 1px 0px 5px 3px #862e1e26;
    height: 100%;
    

}

.zoom-in-pro:hover{ 
transform:scale(1.2,1.2);
opacity: 0.5;
}

.pro-ho{
}
.pro-ho:hover{
 	transform:scale(1.2,1.2);
 	  opacity: 0.5
}
.chose{
    color: #ed563b;
    border-bottom: 1px solid #ed563b;
    font-size: 20px;
    font-weight: bolder;
}
  
</style>

</head>

<body>
	 <!-- header -->
     <%@ include file="../haoren/header.jsp"%>
	<!-- header -->









<!-- 購物車數量 -->
<script >
$(document).ready(function(){
// $('#cartnum').append($("#cartcount").children().length);
$('#cartnum').text(<%=listnum%>);
// alert(count1);
});
</script>
<!-- 購物車數量 -->

<!-- 購物車 -->
<div class="dropdown ml-auto cart" >
                    <button class="btn btn-sm btn-cart" data-toggle="dropdown">
                        <i class="fas fa-shopping-cart text-dark fa-2x " ></i>
                        <span class="badge badge-pill badge-danger" id="cartnum"></span>
                    </button>
            <div class="dropdown-menu dropdown-menu-right" style="min-width: 300px" aria-labelledby="dropdownMenuButton">
           
           
           
           <div class="p-3">
                    <table class="table table-sm ">
                        <h5>已選擇商品</h5>
                        
                <%
                int index_del = 0;
                if (buylist != null && (buylist.size() > 0)) {%>
                <tbody id="cartcount">
                        <%
							 for (int index = 0; index < buylist.size(); index++) {
							Cart order = buylist.get(index);
							index_del = index;
							System.out.println("index_del=" + index_del);
						%>
						
								
						
						
						
                            <tr >
                            <div></div>
								<!--垃圾桶按鈕 -->
                                 <td>                                  
                                 <a href="#" data-toggle="modal" data-target="#removeModal" data-title="這個商品">
                                 <i class="far fa-trash-alt fa-1x text-danger"></i>
                                 </a> 
                                 </td>
                                 
                                <td class="align-middle"><%=order.getProduct_Name()%></td>
                                <td class="align-middle"><%=order.getOrder_buymount()%>件</td>
                                <td class="align-middle text-right">$<%=order.getProduct_Price()%></td>
                            </tr>
                            
                            
              
                              <%}%>                          
                     </tbody>
                    <form name="checkoutForm" action="Shopping.html" method="POST">
              <input type="hidden" name="action"  value="CHECKOUT"> 
              
			  <button type="submit"  class="btn btn-block btn-primary btn-sm text-white">確認結帳</button>
			  
              
         			 </form> 
              <%}%>
               
                 
                    </table>
                    </div>
            </div>            
        </div>
        
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
                     <input type="hidden" name="action"  value="DELETE">
             		 <input type="hidden" name="del" value="<%= index_del %>">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">取消</button>
                         <button type="submit" class="btn btn-outline-danger">刪除</button>   
                         <input type="hidden" name="category_id" value="<%=Category_Id%>">                         
                    </div>
                     </form>
                </div>
            </div>
        </div>   
             <!-- 刪除方塊 -->
             
             

				
             

                
                <section class="shopping-area margin-top-90">
        <div class="container">
            <!-- search banner -->
            <div class="search-banner margin-bottom-50">
                <div class="row" style="height:30px;">
                    <div class="col-lg-12">
                        <div class="row d-flex justify-content-lg-between flex-column flex-md-row align-items-center text-center">
                        
 
                           
                            <div class="search-menu">
                                <ul>
                                    <li><a  id="all" class="cateclick" href="${pageContext.request.contextPath}/front-end/haoren/list-all-product.jsp" >All</a></li>
                                  
									<%
										Product_categoryVO cvo = null;

										while (list_cat_it.hasNext()) {
											cvo = list_cat_it.next();
									%>
							<li>
<%-- 										<form action="${pageContext.request.contextPath}/product_category/category.do"	enctype="multipart/form-data"> --%>
<!-- 											<input type="hidden" name="action"	value="getProductByCategory"/>  -->
											<a id="<%=cvo.getCategory_Id()%>" class="cateclick" href="${pageContext.request.contextPath}/front-end/haoren/list-all-product.jsp?Category_Id=<%=cvo.getCategory_Id()%>"	onclick=subCategory(this)> <%=cvo.getCategory_Name()%> 
<%-- 											<input type="hidden" name="categoryNo" value="<%=cvo.getCategory_Id()%>"/> --%>
											</a>
<!-- 										</form> -->
									</li>
									<%
										}
									%>
								</ul>
								
							</div>
						</div>
                    </div>
                </div>
            </div>
            <!-- search banner end -->
           
            
	
            	
          <%@ include file="../haoren/pages/page1.file"%>
                      <div class="row shopping-slider-item text-center ">  
              <c:forEach var="proVO" items="${list}"  begin="<%=pageIndex%>"
					end="<%=pageIndex+rowsPerPage-1%>">
            <form class="col-md-3" name="shoppingForm" action="Shopping.html" method="POST">
                     <div class="shop-single-item margin-bottom-50">
                        <div class="top-content">
                            <div class="thumb course-img-pro box-shadow flip3D">
                            <!-- 商品圖片 -->
                                <img class="img-pro zoom-in-pro" src="<%=request.getContextPath() %>/ProImg?name=PRODUCT_Lpic&PRODUCT_ID=${proVO.product_Id}" alt="shopping">
<%--                                 <img class="back" src="<%=request.getContextPath() %>/ProImg?name=PRODUCT_Lpic1&PRODUCT_ID=${proVO.product_Id}" alt="shopping"> --%>
                            </div>
                            <div class="hover-content" >
                                <div class="btn-wrapper desktop-center">                    
                                     <button type="submit" class="btn btn-element btn-normal-size btn-rounded opacity put">放入購物車</button> 
                                </div>
                            </div>
                        </div>

<%--                         ${categoryService.getOneCategory(proVO.getCategory_Id()).category_Name} --%>
                        <div class="bottom-content">
                            <p class="subtitle" style="margin-top:-11px;">${proVO.product_Brand}</p>
                            <h3 style="height: 60px;"><button  type="button" class="btn btn-link detail-win" data-toggle="modal" data-target="#${proVO.product_Id}">${proVO.product_Name}</button></h3>
                            <p class="price">
                                <ins>
                                    <span class="woocommerce-Price-amount amount">
                                        <span class="woocommerce-Price-currencySymbol">$</span>${proVO.product_Price}
                                    </span>
                                </ins>
                                <del>
                                    <span class="woocommerce-Price-amount amount">
                                   
                                    <select class ="form-control"name="order_buymount">
                                    <c:forEach var="i" begin="1" end="9">
                                        <option  value="${i}">${i}件</option>
                                    </c:forEach>
                                    </select>
                                    
                                    </span>
                                </del> 
                            </p>
                        </div>
                    </div>
                
               
             
        
      <input type="hidden" name="product_Id" value="${proVO.product_Id}">
      <input type="hidden" name="product_Name" value="${proVO.product_Name}">
      <input type="hidden" name="product_price" value="${proVO.product_Price}"> 
      <input type="hidden" name="category_id" value="<%=Category_Id%>">  
      <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
	  <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->        
      <input type="hidden" name="action" value="ADD">
       </FORM>
       </c:forEach>
       
       </div>
      
        
             
             
                    <div class="row">               
                      <div class="col-md-12">
                      <%@ include file="../haoren/pages/page2.file"%></div>                    
                </div>
            </div>
        
    </section>
    
    
    
 

<!-- pro_detail_alert -->

<c:forEach var="proVO" items="${list}"  >
<div>
<div class="modal fade"  id="${proVO.product_Id}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false"> 
 	<div class="modal-dialog" > 
		<div class="modal-content modal-xl"  > 
 			<div class="modal-header">
        <h6 class="modal-title" id="myModalLabel">商品詳情</h6>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">      
        <div class="container">
								<div class="win-img row col-md-4  col-sm-4 float-left">								
								<img  src="<%=request.getContextPath() %>/ProImg?name=PRODUCT_Lpic1&PRODUCT_ID=${proVO.product_Id}" class="img zoom-in" >														
								</div>	
								<div class="win-img row col-md-4 col-sm-4 float-left" style="margin-left:50px"> 
								<img  src="<%=request.getContextPath() %>/ProImg?name=PRODUCT_Spic&PRODUCT_ID=${proVO.product_Id}" class="img zoom-in" >
								</div>	
								<div class="pro-win row col-md-4 col-sm-4 float-left" style="margin-left:100px"> 
								<h4 id="proname">${proVO.product_Name}
								<c:if test="${proVO.product_Hot == 1}">
								<img src="<%=request.getContextPath()%>/front-end/haoren/images/fire.jpg" style="width:40px;height:40px;">
								</c:if>								
								</h4>
								
	
	
	 <c:choose>
			  <c:when test="${islog}">
<!-- HeartAnimation -->
<c:if test="${empty favorSvc.getOneFavor(member_id, proVO.product_Id)}">

       		    <div class="HeartAnimation">
				<input type="hidden" class="member_id" name="member_id" value="${member_id}"/>
				<input type="hidden"  name="product_id1" class="product_id" value="${proVO.product_Id}"/>
				<input type="hidden" id="fav" class="action" name="action" value="" />.
		</div>
</c:if>
<c:if test="${not empty favorSvc.getOneFavor(member_id, proVO.product_Id)}">
                <div class="HeartAnimation like-active">
				<input type="hidden" class="member_id" name="member_id" value="${member_id}"/>
				<input type="hidden"  name="product_id1" class="product_id" value="${proVO.product_Id}"/>
				<input type="hidden" id="fav" class="action" name="action" value="" />
				</div>
</c:if>

			</c:when>            
           <c:otherwise>
              <a class="HeartAnimation heart-a" href="<%=request.getContextPath()%>/front-end/logIn2.jsp"></a>
              </c:otherwise>
              </c:choose>

<!-- HeartAnimation -->



								<div style="margin-bottom:30px">
								<span id="NT">NT$</span>
								<span id="proprice" style="margin-bottom:50px">${proVO.product_Price}</span>
								
								<c:choose>
								 <c:when test="${proVO.product_Stock >= 10}">								
								<span id="prostock">庫存:有貨</span>
								 </c:when>
								
								 <c:when test="${proVO.product_Stock < 10}">
								<span id="prostock" style="color:red;">庫存:即將售完</span>
								</c:when>
								
								<c:otherwise>
								<span id="prostock">庫存:缺貨中</span>
								</c:otherwise>							
								</c:choose>
								</div>
								<table style="width:100%">	
								<tr class="table-danger con">
								<th class="title">品牌:</th>
								<td class="content">${proVO.product_Brand}</td>
								</tr>
																
								<tr class="table-primary con">
								<th>付款方式:</th>
								<td>信用卡、貨到付款</td>
								</tr>
								
								<tr class="table-primary con">
								<th>運送方式:</th>
								<td>便利商店取貨、貨運寄送</td>
								</tr>
								
								<tr class="table-active con">
								<th>運費:</th>
								<td>一律60元，滿3000免運</td>
								</tr>
								
								</table>
								</div>
								</div>
   
    
    

		
		<div class="container">
					<div class="row col-md-12  col-sm-12 float-left pro-detail">	
					<hr>
					<H6 class="modal-title font-30">商品介紹</H6>
					<div id="show-detail">${proVO.product_Detail}</div>
					<hr>
									
		</div>	
		</div>
      </div>    
    </div>
  </div>
</div>
</div>
</c:forEach>









          
      <!------------------------------------------------------------- ***** 這裡以下複製貼上 ***** ------------------------------------------------------->
        <!-- footer -->
    	<%@ include file="../haoren/footer.jsp"%>
		<!-- footer -->
		
      

	<!-- Bootstrap -->
		<script src="<%=request.getContextPath()%>/front-end/haoren/css/js/popper.js"></script>
		<script src="<%=request.getContextPath()%>/front-end/haoren/css/js/bootstrap.min.js"></script>

		<!-- Plugins -->
		<script src="<%=request.getContextPath()%>/front-end/haoren/css/js/scrollreveal.min.js"></script>
		<script src="<%=request.getContextPath()%>/front-end/haoren/css/js/waypoints.min.js"></script>
		<script src="<%=request.getContextPath()%>/front-end/haoren/css/js/jquery.counterup.min.js"></script>
		<script src="<%=request.getContextPath()%>/front-end/haoren/css/js/imgfix.min.js"></script> 
		<script src="<%=request.getContextPath()%>/front-end/haoren/css/js/mixitup.js"></script> 
		<script src="<%=request.getContextPath()%>/front-end/haoren/css/js/accordions.js"></script>

		<!-- Global Init -->
		<script src="<%=request.getContextPath()%>/front-end/haoren/css/js/custom.js"></script>
		
		 <!-- Page level plugins -->
  			<script src="<%=request.getContextPath()%>/front-end/haoren/vendor/datatables/jquery.dataTables.min.js"></script>
  			<script src="<%=request.getContextPath()%>/front-end/haoren/vendor/datatables/dataTables.bootstrap4.min.js"></script>
		
		
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
            
            
            
            
            
            $('.HeartAnimation').click(            		
            		
    				function() {
    					var _this = $(this);
    					console.log($(_this).attr('class'));

    					var favform =$("#favform");
    					if(_this.hasClass('like-active')){
    					 _this.removeClass('like-active') ;
    					 $(".HeartAnimation").children('#fav').attr("value","cancel");						
//    						 favform.submit();
    					}
    					else{_this.addClass('like-active'); 
    					$(".HeartAnimation").children('#fav').attr("value","insert");						
//    						favform.submit();						
    					}
    					
    					var product_id = $(this).find("input.product_id").val();
    	            	var action = $(this).find("input.action").val();
    	            	var member_id = $(this).find("input.member_id").val();
    	            	
    	            	console.log($(this).find("input.product_id"));
    	            	console.log("product_id" + product_id);
    	            	console.log("action" + action);
    	            	console.log("member_id" +member_id);
    	            	
    	            	
    	            	$.ajax({
    	            		url:"<%=request.getContextPath()%>/product_favorite/favorite.do",
    	            		type: "POST",
    	            		dataType: "json",
    	            		data: {
    	            			"product_id" : product_id,
    	            			"action" : action,
    	            			"member_id" : member_id
    	            		},   	            		
   		            		success: function(data){
   		            			$(_this).attr('class','HeartAnimation like-active');
   		            		}
    	            		
    	            })
    					
    					
    							
    				});
            
            
        	let account = "${memLogIn.member_Id}";
        	$('.main-nav .nav .m1').click(function(){
        		if(!account){
        			event.preventDefault();
        			// 跳彈窗	 
        			Swal.fire({
        			  title: '沒有權限!',
        			  text: "會員請先登入!",
        			  icon: 'error',
        			}).then((result) => {
        			  if (result.value) {
        					$(location).attr('href', '<%=request.getContextPath()%>/front-end/logIn2.jsp');
        			  }
        			})
        			// 延遲跳轉
//         			setTimeout(function(){
        <%-- 				$(location).attr('href', '<%=request.getContextPath()%>/front-end/logIn2.jsp'); --%>
//         			}, 2000);
        		}
        	});
        	
        	let account2 = "${coachLogIn.coach_Id}";
        	$('.main-nav .nav .c1').click(function(){
        		if(!account2){
        			event.preventDefault();
        			// 跳彈窗	 
        			Swal.fire({
        			  title: '沒有權限!',
        			  text: "不是教練不能進入操作! 趕快申請加入吧!",
        			  icon: 'error',
        			}).then((result) => {
        			  if (result.value) {
        					$(location).attr('href', '<%=request.getContextPath()%>/front-end/logIn2.jsp');
        			  }
        			})
        		}
        	});
        	
        	 //我的最愛登入彈窗
        	$('.heart-a').click(function(){
        		if('${islog}'){
        			event.preventDefault();
        			// 跳彈窗	 
        			Swal.fire({
        			  title: '沒有權限!',
        			  text: "登入才能加入我的最愛喔!",
        			  icon: 'error',
        			}).then((result) => {
        			  if (result.value) {
        					$(location).attr('href', '<%=request.getContextPath()%>/front-end/logIn2.jsp');
        			  }
        			})
        		}
        	});
        	$(document).ready(function () {
        		if("${cat}"==="all"){
        			$('#all').addClass("chose");        			
        			$('#C001').removeClass("chose");        			
        			$('#C002').removeClass("chose");        			
        			$('#C003').removeClass("chose");        			
        			$('#C004').removeClass("chose");        			
        		}else if("${cat}"==="C001"){
        			$('#all').removeClass("chose");        			
        			$('#C001').addClass("chose");        			
        			$('#C002').removeClass("chose");        			
        			$('#C003').removeClass("chose"); 
        			$('#C004').removeClass("chose");         		
        		
        		}else if("${cat}"==="C002"){
        			$('#all').removeClass("chose");        			
        			$('#C001').removeClass("chose");        			
        			$('#C002').addClass("chose");        			
        			$('#C003').removeClass("chose"); 
        			$('#C004').removeClass("chose");
        		}else if("${cat}"==="C003"){
        			$('#all').removeClass("chose");        			
        			$('#C001').removeClass("chose");        			
        			$('#C002').removeClass("chose");        			
        			$('#C003').addClass("chose"); 
        			$('#C004').removeClass("chose"); 
        		}else if("${cat}"==="C004"){
        			$('#all').removeClass("chose");        			
        			$('#C001').removeClass("chose");        			
        			$('#C002').removeClass("chose");        			
        			$('#C003').removeClass("chose"); 
        			$('#C004').addClass("chose"); 
        		}
        		
        	});
        	
		
            
           
            
 
        </script>


	</body>
	</html>


