document.addEventListener("DOMContentLoaded", function(event) {
  var postDateElements = document.getElementsByClassName('post-date');
  for (var i = 0; i < postDateElements.length; i++) {
    var e = postDateElements[i];
    var timestamp = Number(e.innerHTML)*1000;
    e.innerHTML = new Date(timestamp).toLocaleString();
  }
});
