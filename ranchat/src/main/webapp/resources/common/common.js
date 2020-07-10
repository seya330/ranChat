//form 을 오브젝트로 반환
$.fn.serializeObject = function()
{
   var o = {};
   var a = this.serializeArray();
   $.each(a, function() {
       if (o[this.name]) {
           if (!o[this.name].push) {
               o[this.name] = [o[this.name]];
           }
           o[this.name].push(this.value || '');
       } else {
           o[this.name] = this.value || '';
       }
   });
   return o;
};

function getCookie(cookieName){
	var cookieArr = document.cookie.split(";");
	for(var i=0; i<cookieArr.length; i++){
		var name = cookieArr[i].substr(0, cookieArr[i].indexOf("=")).replace(/^\s+|\s+$/g, "");
		var val = cookieArr[i].substr(cookieArr[i].indexOf("=")+1);
		if(name == cookieName){
			return val;
		}
	}
}

function setCookie(name, value){
	document.cookie = name + "=" + value;
}