document.addEventListener("DOMContentLoaded", function(event) {
  var postDateElements = document.getElementsByClassName('post-date');
  for (var i = 0; i < postDateElements.length; i++) {
    var e = postDateElements[i];
    var timestamp = Number(e.innerHTML)*1000;
    e.innerHTML = new Date(timestamp).toLocaleString();
  }
});

function thumbup(e) {
  console.log(e);
  e.classList.add("thumbed-up");
  var nodeId = e.getAttribute('data-nodeid');
  var url = '/p/node/thumbUp?id=' + nodeId;
  console.log(nodeId);
  $.get(
    url,
    {},
    function(data) {
      data = String(data);
      if (data.indexOf('error') >= 0) return;
      e.classList.add('thumbed-up');
      nume = $(e).find('.post-thumbup')[0];
      tb = Number(nume.text);
      nume.text = tb+1;
    }
  )
}
