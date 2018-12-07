var pLeft, pRight;
var iAmOwner = false;
var roomId = -1;
var canPlay = false;
var my_fail = 0;
var enemy_fail = 0;
var userId = -1;
var game_status = "init";

var GameStartCheckURL = '/xhr/room/';
var GameStartURL = '/xhr/room/'; // ajax start game
var GameUpdateURL = '/xhr/play/';
var GameSubmitURL = '/xhr/room/';

$(document).ready(function() {
	roomId = $("#roomId").text();
	userId = $("#userId").text();
	// heartbeat();
	// init canvas
	pLeft = canvasInit('#hangman_left');
	pRight = canvasInit('#hangman_right');
	// init long polling handle
	waitingForGameStart();

	// Playing game
	$('.word').on('click', function() {
		if (!canPlay)
			return;
		if ($(this).hasClass('fales-word'))
			return;
		$(this).addClass('fales-word');
		submitResult($(this).text());
	});

	$('#start-game').on('click', function() {
		$("#leave-game").addClass('invisible')
		clickStartGame(roomId);
	});

});

// Play game
function playingGame() {
	$('.anphabe').show();
}

function submitResult(charSubmit) {
	$.ajax({
		url : GameSubmitURL+roomId+"/word/"+charSubmit,
		success : function(result) {
			if (result.status == 'ok') {
				if (result.data) {
					for (let i = 0; i < result.data.length; i++) {
						$('#invil_text_' + (result.data[i] + 1)).text(charSubmit).addClass(
								'show-text');
					}
				} else {
//					//my_fail += 1;
				}
			} else {
				message('Server error! please try again later', 'danger');
				console.log(result);
			}
		},
		error : function() {
			message('Server error! please try again later', 'danger');
			console.log('Server error !');
		},
		dataType : 'json'
	});
}

// Polling
// Owner wait player
function waitingForGameStart() {
	$.ajax({
		url : GameStartCheckURL + roomId + "/status/" + game_status,
		success : function(result) {
			if (result.status == 'ok' && result.data) {
				game_status = result.data.gameStatus;

				if (result.data.gameStatus == 'ready') {
					showStartGameButton();
				} else if (result.data.gameStatus == 'started'|| result.data.gameStatus == 'finished') {
					if (!canPlay) {
						autoStartGame();
					}
					return;
				}
			} else {
				console.log(result);
			}
			waitingForGameStart();
		},
		error : function() {
			message('Disconnected to server! reconnect soon', 'danger');
			console.log('Server error !');
			setTimeout(function() {
				waitingForGameStart();
			}, 5000);
		},
		dataType : 'json'
	});
}

function clickStartGame() {
	$.ajax({
		url : GameStartURL + roomId + "/_start",
		success : function(result) {
			if (result.status == 'ok') {
				autoStartGame();
			} else {
				console.log(result);
			}
		},
		error : function() {
			message('Can not start game! please try again later', 'danger');
			console.log('Server error !');
		},
		dataType : 'json'
	});
}

function showStartGameButton() {
	$('#start-game').removeClass('invisible').addClass('visible');
}
function autoStartGame() {
	$('.game-action').remove();
	canPlay = true;
	playingGame();
	updateGame();
//	var countDown = 5;
//	message('Game start in ' + countDown + 's', 'success');
//	setTimeout(function() {
//		playingGame();
//		updateGame();
//	}, countDown * 1000);
}

function updateGame() { // update game status from server
	$.ajax({
		url : GameUpdateURL+roomId+"/my/"+my_fail+"/enemy/"+enemy_fail,
		success : function(result) {
			if (result.status == 'ok') {
				if (result.data) { // hase new change
					drawHangman('left', result.data.myFail);
					drawHangman('right', result.data.enemyFail);
					if (result.data.status) {
						canPlay = false;
						if (result.data.status == 'win') {
							message('You Win! Congratulations', 'danger');
						} else {
							message('Game over ! Better luck next time!',
									'danger');
						}
						return;
					}
					my_fail = result.data.myFail;
					enemy_fail = result.data.enemyFail;
				}
			} else {
				console.log(result);
			}
			updateGame();
		},
		error : function() {
			message('Server Error! Can not update status', 'danger');
			console.log('Server error !');
		},
		dataType : 'json'
	});

}

function canvasInit(ID) {
	drawArer = $(ID)[0];
	context = drawArer.getContext("2d");
	clearCanvas(drawArer);
	setColor(context, '#000000');
	setLineWidth(context, 5);
	return context;
}

// draw hang man
function drawHangman(Position, FailCount) {
	dContext = pRight;
	if (Position == 'left') {
		dContext = pLeft;
	}
	for (i = 1; i <= FailCount; i++) {
		eval('s' + i + '(dContext)');
	}
}

function clearCanvas(canvas) {
	canvas.width = canvas.width;
};

function setColor(context, color) {
	context.strokeStyle = color;
};

function setLineWidth(context, width) {
	context.lineWidth = width;
}

function s0(context) {
	//
};
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
	context.arc(100, 50, 25, 0, Math.PI * 2, true);
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

function s10(context) {
	context.beginPath();
	context.moveTo(100, 150);
	context.lineTo(125, 190);
	context.stroke();
};

function heartbeat() {
	setInterval(function() {
		$.get("/game/heartbeat", null, function() {
		});
	}, 5000);
}
