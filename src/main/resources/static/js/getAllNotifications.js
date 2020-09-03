
/**
 *  метод для отображения уведомлений
 *
 */
function getAllNotifications() {
    $.ajax({
        type:'GET',
        url: '/api/user/MyNotifsNbr',
        success: function (data) {
            console.log(data)
            $.each(data, function (i, notification) {
                $('#drop_note').append($('<button>').text(notification.text).attr({
                    "class":"dropdown-item info",
                    "data-toggle":"modal",
                    "data-target":"#notificationModalSamp",
                }).data("notification", notification))
            })
            let elem = $('.dropdown-item')
            for(var i = 0; i < elem.length; i++ ) {
                if (elem[i].innerText.length > 20) {
                    elem[i].innerText = elem[i].innerText.slice(0, 20) + "...";
                }
            }
        }
    })
}
/**
 *  метод для удаления уведомлений (ознакомлен) , выполняется при помощи запроса в NotificationRestController
 *
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
                getAllNotifications();
                location = location
            }
        })
    })
})
