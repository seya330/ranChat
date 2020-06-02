<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- lib -->
<link rel="stylesheet" type="text/css" href="/static/lib/css/bootstrap/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="/static/lib/css/font-awesome/font-awesome.min.css"/>
<script src="/static/lib/js/jquery/jquery-3.5.1.js"></script>
<script src="/static/lib/js/sockjs/sockjs.js"></script>
<script src="/static/lib/js/stomp/stomp.js"></script>
<script src="/static/lib/js/bootstrap/bootstrap.bundle.min.js"></script>
<script src="/static/lib/js/mustache/mustache.js"></script>

<!-- ranChat -->
<script src="/static/ranChat/js/chat.js"></script>
<title>ranChat</title>

<style>
	@font-face{
		font-family: "Cafe24Dangdanghae";
		src: url("/static/font/Cafe24Dangdanghae.ttf");
	}
	body {
	  background-color: #74EBD5;
	  background-image: linear-gradient(90deg, #74EBD5 0%, #9FACE6 100%);
	
	  min-height: 100vh;
	  font-family: Cafe24Dangdanghae;
	}
	
	::-webkit-scrollbar {
	  width: 5px;
	}
	
	::-webkit-scrollbar-track {
	  width: 5px;
	  background: #f5f5f5;
	}
	
	::-webkit-scrollbar-thumb {
	  width: 1em;
	  background-color: #ddd;
	  outline: 1px solid slategrey;
	  border-radius: 1rem;
	}
	
	.text-small {
	  font-size: 0.9rem;
	}
	
	.messages-box,
	.chat-box {
	  height: 510px;
	  overflow-y: scroll;
	}
	
	.rounded-lg {
	  border-radius: 0.5rem;
	}
	
	input::placeholder {
	  font-size: 0.9rem;
	  color: #999;
	}

	.spinning-loader {
  	width: 50px;
  	height: 50px;
 	border-radius: 50%;
	border: 5px solid rgba(29, 161, 242, 0.2);
	border-left-color: rgb(29, 161, 242);
	background: transparent;
	animation: rotate-s-loader 1s linear infinite;
	margin: 6rem auto;
}

@keyframes rotate-s-loader {
  from {
    transform: rotate(0);
  }
  to {
    transform: rotate(360deg);
  }
}

</style>
<script>
$(function(){
	$("#connectBtn").off().on("click", function(){joinRoom()});
	$("#sendChatBtn").off().on("click", function(){sendMessage()});
	$("#disConnectBtn").off().on("click", function(){disconnect()});
	$("#chatText").off().on("keyup", function(){keyDownMapping()});
	$("#loadingCircle").hide();
	
	$("#chatBox").fadeTo(0, 0.5);
});
</script>
</head>
<body>
<div class="container py-5 px-4">
  <!-- For demo purpose-->
  <header class="text-center">
    <h1 class="display-4 text-white">Ran Chat</h1>
    <p class="text-white lead mb-0">남들에게 말하기 애매한 사연을 익명으로 나누어 보세요!</p>
    <p class="text-white lead mb-4">from 
      <a href="https://seya330.github.io" class="text-white">
        <u>seya330</u></a>
    </p>
  </header>

  <div class="row rounded-lg overflow-hidden shadow">
    <!-- Chat Box-->
    <div class="col-12 px-0" id="fullCover">
      <div id="chatBox" class="px-4 py-5 chat-box bg-white ">
      	<div id="chatBoxContents" style="">
      	</div>
      	<div id="loadingCircle" class="spinning-loader" style="position: absolute; left: 50%; top: 50%; margin-left: -25px; margin-top: -25px; display: none;"></div>
      </div>

      <!-- Typing area -->
        <div class="input-group">
          <input id="chatText" type="text" placeholder="Type a message" aria-describedby="button-addon2" class="form-control rounded-0 border-0 py-4 bg-light">
          <div class="input-group-append">
            <button id="sendChatBtn" class="btn bg-light"> <i class="fa fa-paper-plane"></i></button>
          </div>
        </div>
        	<span class="col-3">이름 : </span><input type="text" id="userName" class="form-control col-5" style="display: inline">
       <button id="connectBtn" class="btn btn-info">채팅 접속</button>
		<button id="disConnectBtn" class="btn btn-danger" disabled>접속 해제</button>
    </div>
    <div>
    
    </div>
   
  </div>
</div>
	
	<!-- 최근 탭 엘리먼트 -->
	<script id=""></script>
	
	<!-- 받은 메세지 -->
	<script id="recvMsgTemplate" type="text/template">
		<p class="small text-muted">{{sendUserName}}</p>
		<div class="media w-50 mb-3"><img src="https://res.cloudinary.com/mhmd/image/upload/v1564960395/avatar_usae7z.svg" alt="user" width="50" class="rounded-circle">
	    	<div class="media-body ml-3">
	     		<div class="bg-light rounded py-2 px-3 mb-2">
					<p class="text-small mb-0 text-muted">{{message}}</p>
	      		</div>
	      		<p class="small text-muted">{{sendDateString}}</p>
	    	</div>
		</div>
	</script>
	
	<!-- 보낸 메세지 -->
	<script id="sendMsgTemplate" type="text/template">
		<p class="small text-muted" style="text-align: right">{{sendUserName}}</p>
		<div class="media w-50 ml-auto mb-3">
		    <div class="media-body">
		      <div class="bg-primary rounded py-2 px-3 mb-2">
		        <p class="text-small mb-0 text-white">{{message}}</p>
		      </div>
		      <p class="small text-muted">{{sendDateString}}</p>
		    </div>
		</div>
	</script>
</body>
</html>