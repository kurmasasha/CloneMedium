


$(document).ready(function(){
    $('.menu a').filter(function(){
        return this.href==location.href}).parent().addClass('active').siblings().removeClass('active');

    $('.menu a').click(function(){
        $(this).parent().addClass('active').siblings().removeClass('active')
    });

    /**
     *  обновляем "колокольчик" - счётчик уведомлений
     *
     */
    $(document).ready(container($('#notif_counter')));

    /**
     *  получаем все уведомления
     *
     */

    $(document).ready(getAllNotifications());
    /**
     * Нажатие на кнопку поиска по хэштегу
     */
    $('#hashtag_home_button').on('click', function() {
        let hashtag = $('#hashtag_input').val();
        hashtag = hashtag.trim();
        if (hashtag.startsWith('#')) {
            hashtag = hashtag.slice(1);
        }
        if (hashtag !== '') {
            getTopicsOfUserByHashtag(hashtag, $('#topics_container'))
                .then();
        } else {
            let contentContainer = $('#topics_container');
            contentContainer.empty();
            getAndPrintAllTopicsOfUser(contentContainer)
                .then();
        }
    });
});


// function getAllNotifications() {
//     $.ajax({
//         type:'GET',
//         url: '/api/user/MyNotifsNbr',
//         success: function (data) {
//             console.log(data)
//             $.each(data, function (i, notification) {
//                 $('#drop_note').append($('<button>').text(notification.text).attr({
//                     "class":"dropdown-item info",
//                     "data-toggle":"modal",
//                     "data-target":"#notificationModalSamp",
//                 }).data("notification", notification))
//             })
//             let elem = $('.dropdown-item')
//             for(var i = 0; i < elem.length; i++ ) {
//                 if (elem[i].innerText.length > 20) {
//                     elem[i].innerText = elem[i].innerText.slice(0, 20) + "...";
//                 }
//             }
//         }
//     })
// }
//
// $(document).on('click','.info', function (){
//     let notification = $(this).data('notification')
//     console.log(notification)
//     $('#notifText').val(notification.title).text(notification.title)
//     $('#notifTitle').val(notification.text).text(notification.text)
//
//     $(document).on('click','.deleteNotification', function () {
//         console.log(notification.id)
//         $.ajax({
//             type: 'DELETE',
//             url: '/api/user/notification/delete',
//             data: {id: notification.id},
//             success: function () {
//                 getAllNotifications();
//                 location = location
//             }
//         })
//     })
// })

