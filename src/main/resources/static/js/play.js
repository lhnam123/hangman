function clearCanvas(canvas) {
  canvas.width = canvas.width;
};

function setColor(context, color) {
  context.strokeStyle = color;
};

function setLineWidth(context, width) {
  context.lineWidth = width;
}

function s1(context) {
  context.beginPath();
  context.moveTo(175, 225)
  context.lineTo(5, 225);
  context.stroke();
};

function s2(context) {
  context.beginPath();
  context.moveTo(35, 225);
  context.lineTo(35, 5);
  context.stroke();
};

function s3(context) {
  context.beginPath();
  context.moveTo(33, 5);
  context.lineTo(102, 5);
  context.stroke();
};

function s4(context) {
  context.beginPath();
  context.moveTo(100, 5);
  context.lineTo(100, 25);
  context.stroke();
};

function s5(context) {
  context.beginPath();
  context.arc(100, 50, 25, 0, Math.PI*2, true);
  context.closePath();
  context.lineWidth = 2;
  context.stroke();
};

function s6(context) {
  context.beginPath();
  context.moveTo(100, 75);
  context.lineTo(100, 150);
  context.stroke();
};

function s7(context) {
  context.beginPath();
  context.moveTo(100, 85);
  context.lineTo(75, 125);
  context.stroke();
};

function s8(context) {
  context.beginPath();
  context.moveTo(100, 85);
  context.lineTo(125, 125);
  context.stroke();
};

function s9(context) {
  context.beginPath();
  context.moveTo(100, 150);
  context.lineTo(75, 190);
  context.stroke();
};

function s0(context) {
  context.beginPath();
  context.moveTo(100, 150);
  context.lineTo(125, 190);
  context.stroke();
};

function heartbeat(){
  setInterval(function () {
      $.get("/xhr/heartbeat", null, function () {});
   }, 5000);
}

$(document).ready(function(){


  heartbeat();

  canvas_left = $('#hangman_left')[0];
  pleft = canvas_left.getContext("2d");

  
  clearCanvas(canvas_left);
  setColor(pleft, '#000000');
  setLineWidth(pleft, 5);

  s1(pleft);
  s2(pleft);
  s3(pleft);
  s4(pleft);
  //s5(pleft);
  //s6(pleft);
  //s7(pleft);
  //s8(pleft);
  //s9(pleft);
  //s0(pleft);

  canvas_right = $('#hangman_right')[0];
  pright = canvas_right.getContext("2d");
  clearCanvas(canvas_right);
  setColor(pright, '#000000');
  setLineWidth(pright, 5);

  s1(pright);
  s2(pright);
  s3(pright);
  s4(pright);
  s5(pright);
  s6(pright);
  s7(pright);
  //s8(pright);
  //s9(pright);
  //s0(pright);
});