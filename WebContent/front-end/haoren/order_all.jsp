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
	<!-- Bootstrap �� CSS -->
<%-- 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/assets/css/bootstrap.min.css"> --%>
<%-- 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/assets/css/font-awesome.css"> --%>
<%-- 	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/assets/css/templatemo-training-studio.css"> --%>


</head>
<body>
 <table class="table table-sm text-center table table-striped " style="width:800px"  >
                                <thead>
                                <tr>
                                    <th width="80px" class="text-center">�q��s��</th>
                                    <th width="120px" class="text-center">�q�檬�A</th>
                                    <th width="100px" class="text-center">�I�ڤ覡</th>
                                    <th width="80px" class="text-center">�B�e�覡</th>                                    
                                    <th width="120px" class="text-center">�q�榨�߮ɶ�</th>
                                    <th width="120px" class="text-center">�a�}</th>     
                                  </tr>                              
                                </thead>                                
                                <tbody> 
                                <c:forEach var="OrderVO" items="${List_All}">                                                             
                                    <tr>
                                        <td class="align-middle">${OrderVO.order_id}</td>                                        
                                  <!-- �q�檬�A -->
											<c:choose>
											<c:when test="${(OrderVO.order_status)==0 }">	
												<td class="align-middle">�w�U��w�I��</td>
		    								</c:when>	
		    								<c:when test="${(OrderVO.order_status)==1 }">	
												<td class="align-middle">�w�X�f</td>
		    								</c:when>	
		   								    <c:when test="${(OrderVO.order_status)==2 }">	
												<td class="align-middle">����</td>
		   								    </c:when>				
											<c:otherwise>
    											<td class="align-middle">�q�����</td>
   											</c:otherwise> 
											</c:choose>	
								 <!-- �q�檬�A --> 
                                 <!-- �H�Υd�I�� -->
                                        <c:choose>
										<c:when test="${(OrderVO.order_pay)==0 }">	
										<td class="align-middle">�H�Υd�I��</td>
		    							</c:when>				
										<c:otherwise>
    									<td class="align-middle">�f��I��</td>
   										</c:otherwise> 
										</c:choose>	
                                 <!-- �H�Υd�I�� -->                                
                                <!-- �B�e�覡 -->
										<c:choose>
										<c:when test="${(OrderVO.delivery)==0 }">	
											<td class="align-middle">�W�Ө��f</td>
		    							</c:when>				
											<c:otherwise>
    									    <td class="align-middle">�f�B�H�e</td>
   											</c:otherwise> 
										</c:choose>	
								<!-- �B�e�覡 -->
                                        <td class="align-middle"><fmt:formatDate value="${OrderVO.order_date}" type="time" pattern="MM/dd hh:mm"/></td>
                                        <td class="align-middle">${OrderVO.order_address}</td>
                                        <td><button class="btn btn-info btn-icon-split" type="button" data-toggle="modal" data-target="#${OrderVO.order_id}">�ӫ~��T</button></td>
                                       
<!--                                        	�p�G�q�檬�A���w�U�q�w�I�� -->
                                       	<c:if test="${(OrderVO.order_status)==0 }">
                                        <form name="okForm" action="${pageContext.request.contextPath}/front-end/order.do" method="POST">
                                        <input type="hidden" name="action" value="cancel">
                                        <input type="hidden" name="Order_Id" value="${OrderVO.order_id}">
                                        <td><button class="btn btn-info btn-icon-split" type="submit" >�����q��</button></td>
                                        </form>
                                        </c:if>
<!--                                        	�p�G�q�檬�A���w�X�f -->
                                        <c:if test="${(OrderVO.order_status)==1 }">                                       
                                        <form name="okForm" action="${pageContext.request.contextPath}/front-end/order.do" method="POST">
                                        <input type="hidden" name="action" value="complete">
                                        <input type="hidden" name="Order_Id" value="${OrderVO.order_id}">
                                        <td><button class="btn btn-info btn-icon-split" type="submit" >�q�槹��</button></td>  
                                        </form> 
                                        <form name="okForm" action="${pageContext.request.contextPath}/front-end/order.do" method="POST">
                                        <input type="hidden" name="action" value="cancel">
                                        <input type="hidden" name="Order_Id" value="${OrderVO.order_id}">
                                        <td><button class="btn btn-info btn-icon-split" type="submit" >�h�f</button></td>
                                        </form>
                                        </c:if>                                      
                                    </tr>
                                    

  <div class="modal fade" id="${OrderVO.order_id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content modal-xl">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">�ӫ~�Ա�</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">      
        <div class="container">
        
        
       
        <c:forEach var="detailVO" items="${detailSvc.getAllOrder(OrderVO.order_id)}">
    <ul class="list-group">
    <li class="list-group-item list-group-item-danger de-header" >�ӫ~�W�� ${detailVO.product_name}</li>
  <li class="list-group-item list-group-item-light de-con" >�ʶR�ƶq:${detailVO.order_buymount}</li>
  <li class="list-group-item list-group-item-light de-con">�ӫ~���:${detailVO.product_price}</li>
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