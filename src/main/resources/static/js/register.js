$(document).ready(function () {
    $("#GetRegisterCode").click(function () {
        var email_pattern = /^.*@.*$/
        var emailAddress = $("#emailAddress").val()
        if (email_pattern.test(emailAddress) === false) return
        $.post(
            "/user/register-code",
            {
                emailAddress: emailAddress,
            },
            function (data, status){
                if (data === "re register") $(location).attr("href", "/user/login")
            }
        )
        $(this).hide()
        alert("已发送注册码至您的邮件，请注意查收。再次获取注册码请在1分钟后进行")
        setTimeout(function (){$(this).show()}, 1000*60)
    })
})