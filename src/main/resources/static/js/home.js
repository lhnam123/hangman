lastRoomID = 0;
// url config
checkStatusURL = '/xhr/main/';
heartbeat = '/game/heartbeat';
gameRoomURL = '/game/play/';
createRoomUrl = '/xhr/_create';

function createRoom(roomName) {
	$.ajax({ 
		type: "POST",
		url : createRoomUrl,
		data: {name:roomName} ,
		success : function(result) {
			if (result.status == 'ok') {
				if (result.data) {
					window.location.href = "/game/play/"+result.data;
				} else {
					message('Cannot create new room with name: '+roomName, 'danger');
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


// call long polling to check status
function checkStatus() {
	console.log('A polling created !');
	$.ajax({
		url : checkStatusURL+lastRoomID,
		success : function(result) {
			if (result.status == 'ok') {
				if (result.data) {
					updateRoomList(result.data);
				}
			} else {
				console.log(result);
			}
			checkStatus(); // new connect
		},
		error : function() {
			message('Disconnected to server! reconnect soon', 'danger');
			console.log('Server error !');
			setTimeout(function() {
				checkStatus();
			}, 5000);
		},
		dataType : 'json'
	});
}
// 
function updateOnlineCount(alive) {
	$('.online-count').text(alive);
}
// 
function updateRoomList(data) {
	//
	for (i = 0; i < data.length; ++i) {
		if (data[i].creator == $("#userName").text()) {
			$('#my-game').prepend(renderGameBlock(data[i]));
		} else {
			$('#all-game').prepend(renderGameBlock(data[i]));
		}
		if (i == (data.length - 1)) {
			lastRoomID = data[i].id;
		}
	}
}
function renderGameBlock(GameData) {
	owner = '';
	if (GameData.creator) {
		owner = '<span style="font-style:italic">(By ' + GameData.creator
				+ ')</span>';
	}
	html = '<div href="#" class="list-group-item">' + '<div class="game-name">'
			+ '<span class="oi accessible-icon"></span> '
			+ '<span style="font-weight:bold"><a href="' +gameRoomURL + GameData.id + '">'
			+ GameData.name + '</a> </span>' + owner + '</div>'
			+ '<div class="game-status">' + GameData.status + '</div>'
			+ '</div>';
	return html;
}

function message(content, type) { // show a message notice
									// -secondary/success/danger/warning
	if (!type)
		type = 'danger';
	colorClass = 'text-' + type;
	$('#message-box').text(content);
	$('#message-box').attr('class', colorClass);
}

function heartbeat() {
	setInterval(function() {
		$.get("/game/heartbeat", null, function() {
		});
	}, 5000);
}
