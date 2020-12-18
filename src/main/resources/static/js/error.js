// var errorType = [[${errorType}]]
$(document).ready(function () {
    var jwtToken = $.cookie("JwtToken");
    var errorInfo = $("#errorInfo")
    if (jwtToken === undefined || jwt_decode(jwtToken)["exp"] < Math.round((new Date()).getTime()/1000)){
        var $notLoginInfo = $("<h3 class='center-align'>未登录或令牌过期, 3s后自动跳转至登录页面......</h3>")
        errorInfo.append($notLoginInfo)
        setTimeout(function (){$(location).attr("href", "/user/login")}, 3000)
    }else {
        var $unknown = $("<h3 class='center-align'>未知错误，3s后自动跳转至主界面</h3>")
        errorInfo.append($unknown)
        setTimeout(function (){$(location).attr("href", "/index")}, 3000)
    }
})
