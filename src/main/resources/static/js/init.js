M.AutoInit()
$(document).ready(function (){
    var $footer = $("footer")
    $footer.removeClass("fixed-bottom")
    var contentHeight = document.body.scrollHeight,
        winHeight = window.innerHeight
    if (!(contentHeight > winHeight)){$("footer").addClass("fixed-bottom")}
})