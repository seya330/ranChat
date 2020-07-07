var overlapCheck = false;
function addUser(){
	if(!overlapCheck){
		alert("중복확인을 먼저 해주세요.");
		return;
	}
	
	var userObj = $("#addUserForm").serializeObject();
	if(!userObj.userId){
		alert("아이디를 입력해 주세요.");
		return;
	}
	if(!userObj.password){
		alert("비밀번호를 입력해 주세요");
		return;
	}
	if(!userObj.password2){
		alert("확인 비밀번호를 입력해 주세요.");
		return;
	}
	
	if(userObj.password != userObj.password2){
		alert("비밀번호를 확인해 주세요.");
		return;
	}
	$.ajax({
		url				: "/user/add",
		method			: "post",
		/*dataType		: "json",*/
		contentType		: "application/json;charset=UTF-8",
		async			: false,
		data			: JSON.stringify(userObj),
		success			: function(){
			alert("회원 가입에 성공 하였습니다.");
			location.replace("/");
		}, 
		error			: function(){
			alert("fail");
		}
	});
}

function isOverlap(){
	$.ajax({
		url				: "/user/isOverlap/" + $("#userId").val(),
		method			: "get",
		contentType		: "application/json;charset=UTF-8",
		async			: false,
		data			: "",
		success			: function(isOverlap){
			if(isOverlap){
				alert("중복되는 ID 입니다.");
				$("#userId").focus();
			}else{
				alert("사용 가능한 ID 입니다.");
				overlapCheck = true;
			}
		}, 
		error			: function(e){
			alert("서버 오류가 발생 하였습니다.");
		}
	});
	
}