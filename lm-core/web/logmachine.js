

/*
	for each incoming message
		addToTicker(msg)
		addToArchive(msg)






 */


var LogMachine = {};


// configuration

LogMachine.tickerSize = 50;
LogMachine.timer = null;
LogMachine.debug = true;


// functions

LogMachine.initialize = function(config) {
	if (LogMachine.socket) {
		LogMachine.socket.close();
	}

	var socket = new WebSocket("ws://"+config.host+":"+config.port);

	socket.onmessage = function(evt) {
		var data = jQuery.parseJSON(evt.data);
		if (LogMachine.debug) { console.log(data); }

		LogMachine.addToTicker(data);
		LogMachine.addToArchive(data);
	};

	socket.onclose = function() {
		console.log("connection closed, will retry soon");

		if (!LogMachine.timer) {
			LogMachine.timer = setInterval(function() {
				LogMachine.initialize(config);
			}, 1000);
		}
	};

	socket.onopen = function() {
		if (LogMachine.timer) {
			clearInterval(LogMachine.timer);
			LogMachine.timer = null;
		}
	};

	LogMachine.socket = socket;
};

LogMachine.addToArchive = function(data) {
	/*
		addToArchive:
		find the exact node

		if it does not exist
			 break off the classname and enum name
			 create the node at the full name, label with enum name
			 update the TOC

		create the event node
		add to the parent node

	 */

	// var eventNode
	// create the event node object


	// node is group1 > group2 > group3
	// create each node along the way if it doesn't exist

	//var containerNode = $('...') // the top level default node

	if (data.groups) {
		for (var i=0; i < data.groups.length; ++i) {
			var group = data.groups[i];
			//var node = containerNode.select('')
		}
	} else {
		// place it in the default node
		containerNode.append(eventNode);
	}


	// the id structure....
	// it gets stored under each group, as well as the total ordered group.
	// or maybe it's craftier...maybe it has a rudimentary key/value store
	// which allows it to filter when a toc category is clicked.
	// in this model, you store everything in one place.

	// so we can build a graph in memory.
	// worst case...all nodes all connections n^2 not too bad I figure

	// node has..
	//    id, events (data)
	//
	// we build a path and walk it.


	// A ^ B      means events in all of these categories, must be present in all three (intersection)
	// A U B      means events in any of the categories (union)
	// A / B      means events in A which are not in both (A - (A^B))
	//                         in A and not B (A - B)
	//





	var id = group

	var tocNode = $("#toc li['data-group'="+id+"]");




};

LogMachine.addToTicker = function(data) {
	var msg = data.message;

	if (data.cause) {
		msg += "<br/>";

		for (var y=0; y < data.cause.length; ++y) {
			if (y != 0) {
				msg += "<br/>caused by ";
			}

			var cause = data.cause[y];
			msg += cause.class + " - " + cause.message;

			for (var x=0; x < cause.stacktrace.length; ++x) {
				var ste = cause.stacktrace[x];
				msg += "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ste;
			}
		}
	}

	var groups;

	if (data.groups) {
		groups = " &ndash; [";

		for (var i=0; i < data.groups.length; ++i) {
			var group = data.groups[i];
			var lastDot = group.lastIndexOf(".");

			if (lastDot != -1) {
				group = group.substring(lastDot+1);
			}

			if (i != 0) {
				groups += " | ";
			}

			groups += group;
		}

		groups += "] ";
	} else {
		groups = "";
	}

	var partialTime = (data.time+"").substr(7);
	var location = data.location ? " &ndash; {@"+data.location+"}" : "";

	var text = "";
	text += partialTime;
	text += " ["+data.thread+"] ";
	text += data.level;
	text += " " + data.class;
	text += groups;
	text += location;
	text += " &ndash; " + msg;

	var row = $('<tr>');
	$('<td><p>'+text+'</p></td>').appendTo(row);

	var ticker = $('#ticker table tbody');
	ticker.append(row);

	if (ticker.children().length > LogMachine.tickerSize) {
		ticker.children().first().remove();
	}
};