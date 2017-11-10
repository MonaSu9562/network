Enhancement:
a)  Returning of binary images (GIF, JPEG and PNG).
b)  Multithreading	– support multiple concurrent client connection requests up	to a specified limit.
    This limitation could be changed through the variable MAX_NUM_OF_THREADS in Configuration.java.
c)  Supporting DELETE method.
    Instruction: 
d)  Logging	– each time requests are made, log them	to a file, indicating date/time request type,
    and response code.
    The name and the directory of that log document could be changed in Configuration.java. 
    The default name is log.txt and the default directory is "../".
    The file content in the respose code is in the form of String. Thus the content of image should be
    unreadable.
    Example content of log file:
    
Fri Nov 10 01:55:37 GMT 2017

Request type: GET

Response code:

HTTP/1.1 200 OK
Server: MySimpleServer written in Java 6
Content-Length: 1279
Content-Type: text/html


<html>
<head>
<title> Interesting Times </title>
<style type="text/css">
<!--
h1	{text-align:center;
	font-family:Arial, Helvetica, Sans-Serif;
	}

p	{text-indent:20px;
	}
-->
</style>
</head>
<body bgcolor = "#ffffcc" text = "#000000">
<p> <h1> Reproduced from "Interesting Times", by Terry Pratchett </h1> </p>

<p>
Many things went on at the Unseen University and, regrettably teaching had to be one of them. 
The faculty had long ago confronted this fact and had perfected various devices for avoiding it. 
But this was perfectly all right because to be fair, so had the students.
</p>
<p>
The system had worked quite well and, as happens in such cases, had taken on the status of a 
tradition. Lectures clearly took place, because they were down there on the timetable in black and white. 
The fact that no-one attended was an irrelevant detail. It was occasionally maintained that this meant 
that the lectures did not in fact happen at all, but no-one ever attended them to find out if this was true. 
Anyway, it was argued (by the Reader in Woolly Thinking, which is like Fuzzy Logic, only less so) that lectures 
had taken place in essence, so that was all right, too.
</p>

<p>
<a href=page2.html> Page 2 </a>
</p>

</body>
</html>



Fri Nov 10 01:57:28 GMT 2017

Request type: GET

Response code:

HTTP/1.1 200 OK
Server: MySimpleServer written in Java 6
Content-Length: 1144
Content-Type: text/html


<html>
<head>
<title> Interesting Times 3 </title>
</head>
<body bgcolor = "#acdcef" text = "#000000">
<p> <h1> Second Reproduction from "Interesting Times", by Terry Pratchett </h1> </p>

<p>
Many things went on at the Unseen University and, regrettably teaching had to be one of them. 
The faculty had long ago confronted this fact and had perfected various devices for avoiding it. 
But this was perfectly all right because to be fair, so had the students.
</p>
<p>
The system had worked quite well and, as happens in such cases, had taken on the status of a 
tradition. Lectures clearly took place, because they were down there on the timetable in black and white. 
The fact that no-one attended was an irrelevant detail. It was occasionally maintained that this meant 
that the lectures did not in fact happen at all, but no-one ever attended them to find out if this was true. 
Anyway, it was argued (by the Reader in Woolly Thinking, which is like Fuzzy Logic, only less so) that lectures 
had taken place in essence, so that was all right, too.
</p>

<p>
<a href=page2.html> Page 2 </a>
</p>

</body>
</html>


Fri Nov 10 01:57:52 GMT 2017

Request type: GET

Response code:

HTTP/1.1 404 Not Found
Server: MySimpleServer written in Java 6
Content-Length: 0
Content-Type: text/html


