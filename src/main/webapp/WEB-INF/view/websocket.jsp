<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Insert title here</title>
</head>
<body>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/sockjs-client/1.1.1/sockjs.js"></script>
<script type="text/javascript">
    var websocket = null;
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/springmvc/websocket/socketServer.do");
    }
    else if ('MozWebSocket' in window) {
        websocket = new MozWebSocket("ws://localhost:8080/springmvc/websocket/socketServer.do");
    }
    else {
        websocket = new SockJS("http://localhost:8080/springmvc/sockjs/socketServer.do");
    }
    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onerror = onError;
    websocket.onclose = onClose;

    function onOpen(openEvt) {
        //alert(openEvt.Data);
    }

    function onMessage(evt) {
        alert(evt.data);
    }
    function onError() {
    }
    function onClose() {
    }

    function doSend() {
        if (websocket.readyState == websocket.OPEN) {
            var msg = document.getElementById("inputMsg").value;
            websocket.send(msg);//调用后台handleTextMessage方法
            websocket.onopen = function() {
                alert("Socket 已打开");
                //socket.send("这是来自客户端的消息" + location.href + new Date());
            };
            //获得消息事件
            websocket.onmessage = function(msg) {
                alert(msg.data);
            };
        }
        else {
            alert("连接失败!");
        }
    }
    window.close = function () {
        websocket.onclose();
    }

    //TODO
    function doSend2() {
        //实现化WebSocket对象，指定要连接的服务器地址与端口
        socket = new WebSocket("ws://localhost:8080/springmvc/websocket/socketServer.do");
        //打开事件
        socket.onopen = function() {
            alert("Socket 已打开");
            //socket.send("这是来自客户端的消息" + location.href + new Date());
        };
        //获得消息事件
        socket.onmessage = function(msg) {
            alert(msg.data);
        };
        //关闭事件
        socket.onclose = function() {
            alert("Socket已关闭");
        };
        //发生了错误事件
        socket.onerror = function() {
            alert("发生了错误");
        }
    };

</script>
请输入：<textarea rows="5" cols="10" id="inputMsg" name="inputMsg"></textarea>
<button onclick="doSend2();">发送</button>
</body>
</html>