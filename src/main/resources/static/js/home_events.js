/**
 * Обработчики событий на странице home.html (/home)
 */
$(document).ready(function() {

    $('#main_page_link').addClass('active');
    getAndPrintAllTopicsOfUser($('#topics_container'))
        .then();


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

    const showNotifModal = $("#showNotifModal");
    let notifBbody = $('#modalNotifBody');

    showNotifModal.on('show.bs.modal', function (event) {

         //let notifBbody = $('#modalNotifBody');

         getAndPrintAllNotificationsOfUser(notifBbody).then();

    });

    $('#modalNotifCloseButton').on('click', function () {
        console.log("close-modal")
        $('#modalNotifBody').empty();
    });



    // $('.btn-sm btn-warning').on('click', function () {
    //     console.log("re-picturing")
    //     getAndPrintAllNotificationsOfUser(notifBbody).then();
    // });









});