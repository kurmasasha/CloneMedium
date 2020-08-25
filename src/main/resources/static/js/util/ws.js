/**
 * Open the web socket connection and subscribe the "/notify" channel.
 */
let socket = new SockJS('/ws');
    let stompClient = Stomp.over(socket);
    let con = $('#notif_counter');
    let counter = 0;
    function container(container){
    con = container;
    }
    function bell(con, counter){
        if(counter > 0){
            con.empty();
            con.append(counter);
        }
    }
    stompClient.connect({}, function (frame) {
        fetch(`/api/user/MyNotifsNbr`)
            .then(response => response.json())
            .then(result => {
                result.forEach(element =>
                    stompClient.send('/user/queue/notify', {}, JSON.stringify(element)))
            })
    stompClient.subscribe('/user/queue/notify', function (notification) {
        // Call the notify function when receive a notification
        let data = JSON.parse(notification.body);
        if (notification) {
            bell(con,counter+=1);
        }
    });
});