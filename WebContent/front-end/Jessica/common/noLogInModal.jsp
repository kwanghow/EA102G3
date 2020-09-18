<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- No LogIn Modal -->
<div class="modal fade" id="NoLogInModal" tabindex="-1" role="dialog" aria-labelledby="modalCenterTitle" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="modalLongTitle">oh 尚未登入</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			</div>
			<div class="modal-body">
        		親愛的，您尚未登入，無法使用該功能<br>
        		請問是否要前往登入？
        	</div>
        	<div class="modal-footer">
        		<button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
        		<button type="button" class="btn btn-primary" id="goToLogIn">Yes!</button>
        	</div>
        </div>
    </div>
</div>