(function(){
	var video = document.querySelector('video');
	
	
	var constraints = {
			video:true,
	        audio:false
	};
	navigator.mediaDevices.getUserMedia(constraints).then(function(stream){
		
	}).catch(function(err){
		video.srcObjec=stream;
		video.play();
	});
	
	
})();