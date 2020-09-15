
/**
 * метод для отображения счетчика уведомлений в колокольчике,
 * обновление каждую минуту
 *
 */
function bellCount() {
    let interval = 0
    setInterval(function (){
        dbCallFunc();
    }, 60000)

    function dbCallFunc() {
        fetch(`/api/user/MyNotifsNbr`)
            .then(response => response.json())
            .then(result => {
                console.log(result)
                if(result) {
                    bell(con, result.length)
                }
                showNote(result)
            })
        console.log('fetch call ' + interval)
    }
}


/**
 *  метод для отображения уведомлений
 *
 */
function getAllNotifications() {
    $('#drop_note').empty();
    fetch(`/api/user/MyNotifsNbr`)
        .then(response => response.json())
        .then(result => {
            console.log(result)
            if(result) {
                bell(con, result.length)
            }
            showNote(result)
        })
}

/**
 * метод для удаления уведомлений (ознакомлен) , выполняется при помощи запроса в NotificationRestController
 */
$(document).on('click','.info', function (){
    let notification = $(this).data('notification')
    console.log(notification)
    $('#notifText').val(notification.title).text(notification.title)
    $('#notifTitle').val(notification.text).text(notification.text)

    $(document).on('click','.deleteNotification', function () {
        console.log(notification.id)
        $.ajax({
            type: 'DELETE',
            url: '/api/user/notification/delete',
            data: {id: notification.id},
            success: function () {
                location = location
            }
        })
    })
})


/**
 * метод для заполнения выпадающего окна уведомлений
 * @param result
 */
function showNote(result) {
    $('#drop_note').empty();
    result.forEach(notification =>
        $('#drop_note').append($('<button>').text(notification.text).attr({
            "class":"dropdown-item info",
            "data-toggle":"modal",
            "data-target":"#notificationModalSamp",
        }).data("notification", notification))
    )
    let elem = $('.dropdown-item')
    for(let i = 0; i < elem.length; i++ ) {
        if (elem[i].innerText.length > 20) {
            elem[i].innerText = elem[i].innerText.slice(0, 20) + "...";
        }
    }
}

let con = $('#notif_counter');

/**
 * метод для заполнения количества уведомлений
 * @param con - let con
 * @param counter
 */
function bell(con, counter){
    if(counter > 0){
        con.empty();
        con.append(counter);
    }
}

