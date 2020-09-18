<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.article.model.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html>




<head>
  <meta charset="utf-8">
  <meta name="robots" content="noindex, nofollow">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
  <title>Easy Image Plugin</title>
  
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
  <script src="https://cdn.ckeditor.com/4.14.1/standard-all/ckeditor.js"></script>
  <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
  <meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <!-- style not here -->
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>
  <form class="w3-container" method="POST" action="<%=request.getContextPath()%>/ArtServlet">

<!--文章標題-->
<div class="w3-card-4">
  <div class="w3-container w3-light-grey">
     <c:if test="${ empty artVO}">
    <h2 >發表文章</h2>

    
</c:if>
 <c:if test="${ not empty artVO}">
    <h2 >修改文章</h2>

    
</c:if>
    
  </div>
    <p>      
    <label class="w3-text-black"><b>文章標題</b></label>
    <input class="w3-input w3-border w3-sand" name="articleTitle" placeholder="請輸入文章標題" value="${artVO.articleTitle}" type="text"></p>
    <p>      
    <label class="w3-text-black"><b>副標題</b></label>
    <input class="w3-input w3-border w3-sand" name="articleTitleSub" placeholder="請輸入文章副標題" value="${artVO.articleTitleSub}"  type="text"></p>
    
    
   
<!--     <p>
 --><!--      <label class="w3-text-black"><b>現在時間</b></label>
 -->   
 
<!-- <p>Date: <input type="text" posttime id="datepicker"></p>
 --> 
    <p>
    <label class="w3-text-black"><b>文章分類</b></label>
    <div class="custom-select" style="width:200px;">
    
    <p>
    
  <%-- <%@ include file="page1.file" %> --%>
  <jsp:useBean id="artCatService" scope="page" class="com.articlecat.model.ArtCatService"/>
  <select name="articleCatNo">
   <c:forEach var="ArticleCatVO"   items="${artCatService.all}"  >
			 <option value="${ArticleCatVO.articleCatNo}">${ArticleCatVO.articleCatName}
	</c:forEach>
  </select>
  </div>
 <p>
 <c:if test="${ empty artVO}">
    <button type="submit" class="w3-btn w3-green">新增文章</button>
    <button type="button" class="btn btn-info" onclick="addData()">神奇小按鈕</button>
    <input type="hidden" id="action" name="action" value="insert">
    
</c:if>
 <c:if test="${ not empty artVO}">
    <button type="submit" class="w3-btn w3-green">修改文章</button>
    <input type="hidden" id="action" name="action" value="update_front">
    <input type="hidden" id="articleNo" name="articleNo" value="${artVO.articleNo}">
    
</c:if>
</p>


<textarea cols="80" id="editor1" name="articleContent" rows="10" data-sample-short>${artVO.articleContent}</textarea>
</div>
</form>				

</body>
 <script>
  /* 
  $( function() {
    $( "#datepicker" ).datepicker();
  } ); 
  
  */
  
  
     $( function() {
	    <c:if test="${ not empty errorMsgs}">
	    var msgStr="";
	    
	    <c:forEach var="msg" items="${errorMsgs}">
	    	msgStr+="${msg}";
	    </c:forEach>
	    
	    alert(msgStr);
	    </c:if>
	    
	    var catNo= '${artVO.articleCatNo}' =='' ? "01":"${artVO.articleCatNo}"
	    $("select[name='articleCatNo']").val(catNo);

     } ); 
    // Don't forget to add CSS for your custom styles.
    CKEDITOR.addCss('figure[class*=easyimage-gradient]::before { content: ""; position: absolute; top: 0; bottom: 0; left: 0; right: 0; }' +
      'figure[class*=easyimage-gradient] figcaption { position: relative; z-index: 2; }' +
      '.easyimage-gradient-1::before { background-image: linear-gradient( 135deg, rgba( 115, 110, 254, 0 ) 0%, rgba( 66, 174, 234, .72 ) 100% ); }' +
      '.easyimage-gradient-2::before { background-image: linear-gradient( 135deg, rgba( 115, 110, 254, 0 ) 0%, rgba( 228, 66, 234, .72 ) 100% ); }');

    CKEDITOR.replace('editor1', {
      extraPlugins: 'easyimage',
      removePlugins: 'image',
      removeDialogTabs: 'link:advanced',
      toolbar: [{
          name: 'document',
          items: ['Undo', 'Redo']
        },
        {
          name: 'styles',
          items: ['Format']
        },
        {
          name: 'basicstyles',
          items: ['Bold', 'Italic', 'Strike', '-', 'RemoveFormat']
        },
        {
          name: 'paragraph',
          items: ['NumberedList', 'BulletedList']
        },
        {
          name: 'links',
          items: ['Link', 'Unlink']
        },
        {
          name: 'insert',
          items: ['EasyImageUpload', 'Table']
        }
        
        
        
      ],
      height: 630,
      cloudServices_uploadUrl: 'https://33333.cke-cs.com/easyimage/upload/',
      // Note: this is a token endpoint to be used for CKEditor 4 samples only. Images uploaded using this token may be deleted automatically at any moment.
      // To create your own token URL please visit https://ckeditor.com/ckeditor-cloud-services/.
      cloudServices_tokenUrl: 'https://33333.cke-cs.com/token/dev/ijrDsqFix838Gh3wGO3F77FSW94BwcLXprJ4APSp3XQ26xsUHTi0jcb1hoBt',
      easyimage_styles: {
        gradient1: {
          group: 'easyimage-gradients',
          attributes: {
            'class': 'easyimage-gradient-1'
          },
          label: 'Blue Gradient',
          icon: 'https://ckeditor.com/docs/ckeditor4/4.14.1/examples/assets/easyimage/icons/gradient1.png',
          iconHiDpi: 'https://ckeditor.com/docs/ckeditor4/4.14.1/examples/assets/easyimage/icons/hidpi/gradient1.png'
        },
        gradient2: {
          group: 'easyimage-gradients',
          attributes: {
            'class': 'easyimage-gradient-2'
          },
          label: 'Pink Gradient',
          icon: 'https://ckeditor.com/docs/ckeditor4/4.14.1/examples/assets/easyimage/icons/gradient2.png',
          iconHiDpi: 'https://ckeditor.com/docs/ckeditor4/4.14.1/examples/assets/easyimage/icons/hidpi/gradient2.png'
        },
        noGradient: {
          group: 'easyimage-gradients',
          attributes: {
            'class': 'easyimage-no-gradient'
          },
          label: 'No Gradient',
          icon: 'https://ckeditor.com/docs/ckeditor4/4.14.1/examples/assets/easyimage/icons/nogradient.png',
          iconHiDpi: 'https://ckeditor.com/docs/ckeditor4/4.14.1/examples/assets/easyimage/icons/hidpi/nogradient.png'
        }
      },
      easyimage_toolbar: [
        'EasyImageFull',
        'EasyImageSide',
        'EasyImageGradient1',
        'EasyImageGradient2',
        'EasyImageNoGradient',
        'EasyImageAlt'
      ]
    });
    
    function addData(){
    	$("input[name='articleTitle']").val("運動好處多！你該運動的五大理由");
    	$("input[name='articleTitleSub']").val("規律的運動習慣，對於生理與心理健康都有非常直接的正向影響");
    	$("select[name='articleCatNo']").val("02");

    	CKEDITOR.instances.editor1.setData('專家都推崇「運動的5大好處」<br>'+'<img style="width:500px;height:500px;"  src="https://www.workingmums.co.uk/ezoimgfmt/2u4kxw37ohx51ecfrgln2k11-wpengine.netdna-ssl.com/wp-content/uploads/2018/10/Fotolia_130932802_Subscription_Monthly_M.jpg?ezimgfmt=ng:webp/ngcb1">' + 
				'<br>1.促進身體健康<br>'+
    			 
    			'2.增進「心理」健康<br>'+
    			'腦內啡（endorphin）是一種神經傳導素，在緊張、疼痛、大笑等情緒發生時，大腦就會產生腦內啡，來減緩當下的緊張疼痛，或是感覺更加愉悅，因此腦內啡也有「人體的嗎啡」之稱。運動時大腦也會產生腦內啡，除了心情變愉悅、有助於穩定情緒外，也能抒發壓力。如果你是上班族或陷入中年危機，感到焦慮、壓力大，運動絕對能成為你宣洩的出口！<br>'+
    			 
    			'3.提升學習與工作表現<br>'+
    			'大腦的重量約佔人體總和的2%，卻需要消耗人體約20%左右的氧氣！大腦的運作能量主要來自葡萄糖的有氧氧化，若是大腦供血量和供氧量不足，理解能力、專注力、記憶力...等智力思考的能力就會下降，此時透過有氧運動改善血液循環、提高血液含氧量，則可有效提升學習與工作的表現。<br>'+
    			 
    			'4.增加肌肉量，讓身體內、外都漂亮<br>'+
    			'在訓練的過程中，肌肉被破壞造成發炎反應，這時候身體為了要修復受傷的肌肉，就會派出「吞嗜菌細胞」，它能夠幫我們處理掉已經壞死的細胞或細菌，幫助身體各部位、組織都可以保時健康的狀態，另外，有研究報告指出，肌肉也是一種分泌器官，能夠分泌多種免疫細胞激素，這種激素就像是指揮官，負責對T細胞、NK細胞…等攻擊病毒的細胞發號施令，並且活化它們，因此增肌可以有效的抵禦外來病毒、殲滅內在凋亡細胞，更好的保衛身體健康！<br>'+
    			 
    			'5.增添自信，帶來正向的影響與改變<br>'+
    			'運動帶來的身心健康、學習工作表現提升，也會連帶讓人擁有成就感、增添自信的光芒，而這樣的改變也會帶來更多美好的事物。就像蝴蝶效應一樣，為了各自目的開始運動，之後會帶來的正向影響卻可能是我們無法想像的！<br>'+
    			 
    			'去健身房參加團體課是很愉快的運動體驗。運動的好處非常多，儘管生活再忙碌，也一定要空出時間運動喔！<br>');
    	// TODO write down your mock data here
    }
    
    function sendEdit(){
    	
    }
  </script>
</html>

