function login(){
	var userObj = $("#loginForm").serializeObject();
	$.ajax({
		url				: "/user/login",
		method			: "post",
		contentType		: "application/json;charset=UTF-8",
		async			: false,
		data			: JSON.stringify(userObj),
		dataType		: "json",
		success			: function(result){
			location.replace("/");
		},
		error			: function(result){
			if(result.responseJSON == "INVALID_ID"){
				alert("잘못된 아이디 입니다.");
				$("#userId").focus();
			}else if(result.responseJSON == "INVALID_PASSWORD"){
				alert("잘못된 비밀번호 입니다.");
				$("#password").focus();
			}
		}
	});
}