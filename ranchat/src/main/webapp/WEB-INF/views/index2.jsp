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
	body {
	  background-color: #74EBD5;
	  background-image: linear-gradient(90deg, #74EBD5 0%, #9FACE6 100%);
	
	  min-height: 100vh;
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
</style>
<script>
$(function(){
	$("#connectBtn").off().on("click", function(){joinRoom()});
	$("#sendChatBtn").off().on("click", function(){sendMessage()});
	$("#disConnectBtn").off().on("click", function(){sendBye()});
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
    <!-- Users box-->
    <div class="col-5 px-0">
      <div class="bg-white">

        <div class="bg-gray px-4 py-2 bg-light">
          <p class="h5 mb-0 py-1">최근</p>
        </div>
        
        <div class="messages-box">
          <div class="list-group rounded-0">
          
          없음
          <!-- 최근 탭 엘리먼트-->
            <!-- <a class="list-group-item list-group-item-action list-group-item-light rounded-0">
              <div class="media"><img src="https://res.cloudinary.com/mhmd/image/upload/v1564960395/avatar_usae7z.svg" alt="user" width="50" class="rounded-circle">
                <div class="media-body ml-4">
                  <div class="d-flex align-items-center justify-content-between mb-1">
                    <h6 class="mb-0">Jason Doe</h6><small class="small font-weight-bold">25 Dec</small>
                  </div>
                  <p class="font-italic mb-0 text-small">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore.</p>
                </div>
              </div>
            </a> -->
          <!-- /최근 탭 엘리먼트-->
            
          </div>
        </div>
      </div>
    </div>
    
    
    <!-- Chat Box-->
    <div class="col-7 px-0">
      <div id="chatBox" class="px-4 py-5 chat-box bg-white">
      	<div id="chatBoxContents"></div>
      </div>

      <!-- Typing area -->
      <form action="#" class="bg-light">
        <div class="input-group">
          <input id="btn-input" type="text" placeholder="Type a message" aria-describedby="button-addon2" class="form-control rounded-0 border-0 py-4 bg-light">
          <div class="input-group-append">
            <button id="sendChatBtn" class="btn btn-link"> <i class="fa fa-paper-plane"></i></button>
          </div>
        </div>
      </form>
    </div>
    <div>이름 : <input type="text" id="name"></div>
    <button id="connectBtn" class="btn btn-info">채팅 접속</button>
	<button id="disConnectBtn" class="btn btn-danger" disabled>접속 해제</button>
  </div>
</div>
	
	<!-- 최근 탭 엘리먼트 -->
	<script id=""></script>
	
	<!-- 받은 메세지 -->
	<script id="recvMsgTemplate" type="text/template">
		<div class="media w-50 mb-3"><img src="https://res.cloudinary.com/mhmd/image/upload/v1564960395/avatar_usae7z.svg" alt="user" width="50" class="rounded-circle">
	    	<div class="media-body ml-3">
	     		<div class="bg-light rounded py-2 px-3 mb-2">
					<p class="text-small mb-0 text-muted">{{message}}</p>
	      		</div>
	      		<p class="small text-muted">12:00 PM | Aug 13</p>
	    	</div>
		</div>
	</script>
	
	<!-- 보낸 메세지 -->
	<script id="sendMsgTemplate" type="text/template">
		<div class="media w-50 ml-auto mb-3">
		    <div class="media-body">
		      <div class="bg-primary rounded py-2 px-3 mb-2">
		        <p class="text-small mb-0 text-white">{{message}}</p>
		      </div>
		      <p class="small text-muted">12:00 PM | Aug 13</p>
		    </div>
		</div>
	</script>
</body>
</html>