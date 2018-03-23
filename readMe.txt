The threads allow for private chatting capabilities, you can also send messages to a person inside a chat room.
The source code contains Java file and Java compile file.
How to use this: 
	make sure you have javac and Java in your environment.
	In Windows cmd, cd to the directory, type command: java Server [port number (12000 or 12001 or 12012)]
	In new Windows cmd, cd to the directory, type: java Client [client Number] [server port Number].
	you can create multiple clients and multiple servers, if on the same ip (3 clients) but if on different ips(limitless)
the command in the client window: 
	(1) broadcast message "[your message]"
	(2) broadcast file "[your file path (can be either relative or absolute)]"
	(3) private message "[your message]" [client Target]
	(4) private file "[your file path]" [client Target]

when you finish running the program, press ctrl + c to quit server or client.