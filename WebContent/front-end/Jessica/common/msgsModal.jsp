<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- msgs Modal -->
<div class="modal fade" id="msgsModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">提示訊息</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			</div>
			<div class="modal-body">
				<ul>
					<c:forEach var="message" items="${msgs}">
						<li class="text-danger">${message}</li>
					</c:forEach>
				</ul>
			</div>
        	<div class="modal-footer">
        		<button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
        	</div>
        </div>
    </div>
</div>