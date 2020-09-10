/**
 * Open the web socket connection and subscribe the "/notify" channel.
 */
let socket = new SockJS('/ws');
    let stompClient = Stomp.over(socket);


    stompClient.connect({}, function (frame) {
        fetch(`/api/user/MyNotifsNbr`)
            .then(response => response.json())
            .then(result => {
                result.forEach(element =>
                    stompClient.send('/user/queue/notify', {}, JSON.stringify(element)))
                bell(con,result.length)
            })

});


//P.S :

    //На случай если нужно будет отправлять уведомления от имени администратора !
    // stompClient.subscribe('/user/queue/notify', function (notification) {
    //     // Call the notify function when receive a notification
    //     let data = JSON.parse(notification.body); // notification data
    //     if (notification) {
    //         bell(con,counter+=1);
    //     }
    //
    // });
