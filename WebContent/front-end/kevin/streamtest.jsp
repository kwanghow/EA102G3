<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>

<!-- �z�L <video> �N�J�v�� -->
<div class="row mb-5 justify-content-center align-items-center">
  <div class="col-md-6 text-center">
    <div class="video-container d-flex align-items-center justify-content-center">
      <div class="is-recording"></div>
      <video id="inputVideo" alt="�b�o�̿��v" muted>Video stream not available.</video>
    </div>
  </div>

  <div class="col-md-6 text-center">
    <div class="video-container d-flex align-items-center justify-content-center">
      <video id="outputVideo" alt="���n���e���N�|�X�{�b�o" muted>Video stream not available.</video>
    </div>
  </div>
</div>

<!-- �ާ@���� -->
<div class="row mb-4 justify-content-center align-items-center">
  <div class="col-4 d-flex justify-content-center align-items-center">
    <button id="startBtn" class="btn btn-sm btn-outline-primary">Start Recording</button>
    <button id="stopBtn" class="btn btn-sm btn-outline-danger" style="display:none">Stop Recording</button>
    <button id="resetBtn" class="btn btn-sm btn-outline-info" style="display:none">Restart Recorder</button>
  </div>
</div>

<!-- ��ܿ��~�T�� -->
<div class="row">
  <div class="col-12">
    <div role="alert" id="errorMsg"></div>
  </div>
</div>





	<script>
	
	

'use strict'
/* global MediaRecorder, Blob, URL */

/**
 * Get DOM element
 */
// <video> element
let inputVideo = document.querySelector('#inputVideo')
let outputVideo = document.querySelector('#outputVideo')

// <button> element
let startBtn = document.querySelector('#startBtn')
let stopBtn = document.querySelector('#stopBtn')
let resetBtn = document.querySelector('#resetBtn')

// error message
let errorElement = document.querySelector('#errorMsg')

// is-recording icon
let isRecordingIcon = document.querySelector('.is-recording')

/**
 * Global variables
 */
let chunks = [] // �b mediaRecord �n�Ϊ� chunks

// �b getUserMedia �ϥΪ� constraints �ܼ�
let constraints = {
  audio: true,
  video: true
}

// �Ĥ@���Ұ���v��
mediaRecorderSetup()

/**
 * MediaRecorder Related Event Handler
 */
let mediaRecorder = null
let inputVideoURL = null
let outputVideoURL = null

startBtn.addEventListener('click', onStartRecording)
stopBtn.addEventListener('click', onStopRecording)
resetBtn.addEventListener('click', onReset)

/**
 * MediaRecorder Methods
 */
// Start Recording: mediaRecorder.start()
function onStartRecording (e) {
  e.preventDefault()
  e.stopPropagation()
  isRecordingBtn('stop')
  mediaRecorder.start()
  console.log('mediaRecorder.start()')
}

// Stop Recording: mediaRecorder.stop()
function onStopRecording (e) {
  e.preventDefault()
  e.stopPropagation()
  isRecordingBtn('reset')
  mediaRecorder.stop()
  console.log('mediaRecorder.stop()')
}

// Reset Recording
function onReset (e) {
  e.preventDefault()
  e.stopPropagation()

  // ����O����
  URL.revokeObjectURL(inputVideoURL)
  URL.revokeObjectURL(outputVideoURL)
  outputVideo.src = ''
  outputVideo.controls = false
  inputVideo.src = ''

  // ���s�Ұ���v��
  mediaRecorderSetup()
}

/**
 * Setup MediaRecorder
 **/

function mediaRecorderSetup () {
  // �]�w��ܪ�����
  isRecordingBtn('start')

  // mediaDevices.getUserMedia() ���o�ϥΪ̴C��v����
  navigator.mediaDevices
    .getUserMedia(constraints)
    .then(function (stream) {
      /**
       * inputVideo Element
       * �N��y�� inputVideo �]�w�� <video> �W
       **/
      // Older browsers may not have srcObject
      if ('srcObject' in inputVideo) {
        inputVideo.srcObject = stream
      } else {
        // Avoid using this in new browsers, as it is going away.
        inputVideo.src = window.URL.createObjectURL(stream)
      }
      inputVideo.controls = false

      /**
       * �z�L MediaRecorder ���s�v����y
       */
      // �إ� MediaRecorder �ǳƿ��v
      // �p�G�S�����w mimeType�A���U�Ӫ� webm �v���b Firefox �W�i�ण��ݡ]Firefox �]���䴩 h264)
      mediaRecorder = new MediaRecorder(stream, {
        mimeType: 'video/webm;codecs=VP9',
        // bitsPerSecond: '512000',
      })

      /* MediaRecorder EventHandler */
      mediaRecorder.addEventListener(
        'dataavailable',
        mediaRecorderOnDataAvailable
      ) // ����ƶǤJ��Ĳ�o
      mediaRecorder.addEventListener('stop', mediaRecorderOnStop) // ������v��Ĳ�o

      function mediaRecorderOnDataAvailable (e) {
        console.log('mediaRecorder on dataavailable', e.data)
        chunks.push(e.data)
      }

      function mediaRecorderOnStop (e) {
        console.log('mediaRecorder on stop')
        outputVideo.controls = true
        var blob = new Blob(chunks, { type: 'video/webm' })
        chunks = []
        outputVideoURL = URL.createObjectURL(blob)
        outputVideo.src = outputVideoURL

        saveData(outputVideoURL)

        // ����Ҧ�����J�ο�X����y�˸m�]�Ҧp�A����v���^
        stream.getTracks().forEach(function (track) {
          track.stop()
        })
      }
    })
    .catch(function (error) {
      if (error.name === 'ConstraintNotSatisfiedError') {
        errorMsg(
          'The resolution ' +
            constraints.video.width.exact +
            'x' +
            constraints.video.width.exact +
            ' px is not supported by your device.'
        )
      } else if (error.name === 'PermissionDeniedError') {
        errorMsg('Permissions have not been granted to use your media devices')
      }
      errorMsg('getUserMedia error: ' + error.name, error)
    })
}

/**
 * DOM EventListener
 */
inputVideo.addEventListener('loadedmetadata', function () {
  inputVideo.play()
  console.log('inputVideo on loadedmetadata')
})

/**
 * Other Function
 */
function errorMsg (msg, error) {
  console.log('errorElement', errorElement)
  errorElement.classList.add('alert', 'alert-warning')
  errorElement.innerHTML += msg
  if (typeof error !== 'undefined') {
    console.error(error)
  }
}

function saveData (dataURL) {
  var fileName = 'my-download-' + Date.now() + '.webm'
  var a = document.createElement('a')
  document.body.appendChild(a)
  a.style = 'display: none'
  a.href = dataURL
  a.download = fileName
  a.click()
}

function isRecordingBtn (recordBtnState) {
  startBtn.style.display = 'none'
  stopBtn.style.display = 'none'
  resetBtn.style.display = 'none'
  isRecordingIcon.style.display = 'none'
  switch (recordBtnState) {
    case 'start':
      startBtn.style.display = 'block' // show startBtn
      break
    case 'stop':
      stopBtn.style.display = 'block' // show stopBtn
      isRecordingIcon.style.display = 'block'
      break
    case 'reset':
      resetBtn.style.display = 'block' // show resetBtn
      break
    default:
      console.warn('isRecordingBtn error')
  }
}

</script>
</body>
</html>