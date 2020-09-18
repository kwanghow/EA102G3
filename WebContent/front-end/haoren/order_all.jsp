<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.pro.order.model.*"%>
<%@ page import="com.pro.order.controller.*"%>
<%@ page import="com.pro.detail.model.*"%>
    
    
    
    
 
    
    
<jsp:useBean id="detailSvc" scope="page" class="com.pro.detail.model.DetailService" />
    
    
    
<!DOCTYPE html>
<html>
<head>


<meta charset="BIG5">
<title>Insert title here</title>


<script src="${pageContext.request.contextPath}/front-end/Jessica/static/js/jquery-2.1.0.min.js"></script>
<!--     <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&display=swap" rel="stylesheet"> -->
	<!-- Bootstrap 的 CSS -->
<%-- 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/assets/css/bootstrap.min.css"> --%>
<%-- 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/assets/css/font-awesome.css"> --%>
<%-- 	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/assets/css/templatemo-training-studio.css"> --%>


</head>
<body>
 <table class="table table-sm text-center table table-striped " style="width:800px"  >
                                <thead>
                                <tr>
                                    <th width="80px" class="text-center">訂單編號</th>
                                    <th width="120px" class="text-center">訂單狀態</th>
                                    <th width="100px" class="text-center">付款方式</th>
                                    <th width="80px" class="text-center">運送方式</th>                                    
                                    <th width="120px" class="text-center">訂單成立時間</th>
                                    <th width="120px" class="text-center">地址</th>     
                                  </tr>                              
                                </thead>                                
                                <tbody> 
                                <c:forEach var="OrderVO" items="${List_All}">                                                             
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
                                        <td><button class="btn btn-info btn-icon-split" type="button" data-toggle="modal" data-target="#${OrderVO.order_id}">商品資訊</button></td>
                                       
<!--                                        	如果訂單狀態為已下訂已付款 -->
                                       	<c:if test="${(OrderVO.order_status)==0 }">
                                        <form name="okForm" action="${pageContext.request.contextPath}/front-end/order.do" method="POST">
                                        <input type="hidden" name="action" value="cancel">
                                        <input type="hidden" name="Order_Id" value="${OrderVO.order_id}">
                                        <td><button class="btn btn-info btn-icon-split" type="submit" >取消訂單</button></td>
                                        </form>
                                        </c:if>
<!--                                        	如果訂單狀態為已出貨 -->
                                        <c:if test="${(OrderVO.order_status)==1 }">                                       
                                        <form name="okForm" action="${pageContext.request.contextPath}/front-end/order.do" method="POST">
                                        <input type="hidden" name="action" value="complete">
                                        <input type="hidden" name="Order_Id" value="${OrderVO.order_id}">
                                        <td><button class="btn btn-info btn-icon-split" type="submit" >訂單完成</button></td>  
                                        </form> 
                                        <form name="okForm" action="${pageContext.request.contextPath}/front-end/order.do" method="POST">
                                        <input type="hidden" name="action" value="cancel">
                                        <input type="hidden" name="Order_Id" value="${OrderVO.order_id}">
                                        <td><button class="btn btn-info btn-icon-split" type="submit" >退貨</button></td>
                                        </form>
                                        </c:if>                                      
                                    </tr>
                                    

  <div class="modal fade" id="${OrderVO.order_id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content modal-xl">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">商品詳情</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">      
        <div class="container">
        
        
       
        <c:forEach var="detailVO" items="${detailSvc.getAllOrder(OrderVO.order_id)}">
    <ul class="list-group">
    <li class="list-group-item list-group-item-danger de-header" >商品名稱 ${detailVO.product_name}</li>
  <li class="list-group-item list-group-item-light de-con" >購買數量:${detailVO.order_buymount}</li>
  <li class="list-group-item list-group-item-light de-con">商品單價:${detailVO.product_price}</li>
      </ul>				
		</c:forEach> 
								
      </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
  
                                    
                                    
                               </c:forEach>    
                                </tbody>
                                 
                            </table>
                            
                            
                            
            
 
                            
                            
                            
                            

                            
                            
                            
                            
                            
</body>
</html>