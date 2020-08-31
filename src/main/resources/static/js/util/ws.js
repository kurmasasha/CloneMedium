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
        let data = JSON.parse(notification.body); // notification data
        if (notification) {
            bell(con,counter+=1);

            //заполняем div id = drop_note данными
            $('#drop_note').append('<a class="dropdown-item" href="#">'
                + data.title + '</a>') //" " + data.text +

                /*ограничиваем текст элемента <a сlass = dropdown-item> 20 символами,
                  если символов более 20 , режется до 20 , и прибавляется "..."
                */
            let elem = $('.dropdown-item')
            for(var i = 0; i < elem.length; i++ ) {
                if (elem[i].innerText.length > 20) {
                    elem[i].innerText = elem[i].innerText.slice(0, 20) + "...";
                }
            }

         }
    });
});

