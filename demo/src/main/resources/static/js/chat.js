const url = "ws://localhost:8080/chat";
const subscribe = "/app/chats/subscribe/greetings";
const publish = "/app/chats/publish/hello";
const client = new StompJs.Client({
    brokerURL: url
});

var buttonConnect;
var buttonDisConnect;
var buttonSend;
var conversation;
var greetings;
var formInput;
var messageInput;

client.onConnect = ((frame)=>{
    setConnected(true);
    console.log('Connected: ' + frame);
    client.subscribe(subscribe, (greeting)=>{
        console.log('Message received:', greeting.body);
        showGreeting(JSON.parse(greeting.body).content);
    });
});

client.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

client.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    buttonConnect.disabled = connected;
    buttonDisConnect.disabled = !connected;
    if(connected) {
        conversationDisplay.style.display = "block";
    }

    else {
        conversationDisplay.style.display = "none";
    }
    greetings.innerHTML = "";
}

function connect() {
    client.activate();
    console.log("Connected")
}

function disconnect() {
    client.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    console.log('sendMessage called');
    console.log('messageInput.value:', messageInput.value);
    client.publish({
        destination: publish,
        body: JSON.stringify({'message': messageInput.value})
    });
}

function showGreeting(message) {
    greeting.innerHTML += "<tr><td>" + message + "</td></tr>";
}

document.addEventListener("DOMContentLoaded", function() {
    buttonConnect = document.getElementById("connect");
    buttonDisConnect = document.getElementById("disconnect");
    buttonSend = document.getElementById("send");
    conversationDisplay = document.getElementById("conversation");
    greetings = document.getElementById("greetings");
    formInput = document.getElementById("form");
    messageInput = document.getElementById("message");

    buttonConnect.addEventListener("click", (e) => {
        connect();
        e.preventDefault();
    });

    buttonDisConnect.addEventListener("click", (e) => {
        disconnect();
        e.preventDefault();
    });

    buttonSend.addEventListener("click", (e) => {
        sendMessage();
        console.log("send it!");
        e.preventDefault();
    });

    formInput.addEventListener("submit", (e) => e.preventDefault());
    setConnected(false);
});