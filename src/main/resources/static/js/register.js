$(document).ready(function () {
    $("#GetRegisterCode").click(function () {
        var emailAddress = $("#emailAddress").val()
        $.post(
            "/user/register-code",
            {
                emailAddress: emailAddress,
            },
            function (data, status){
                if (data === "re register") $(location).attr("href", "/user/login")
            }
        )
    })
})