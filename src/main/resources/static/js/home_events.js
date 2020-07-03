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


    //--------------------------------------------------------------------------------
    /**
     *   Работа с модальным окном уведомлений
     */
    const showNotifModal = $("#showNotifModal");
    let notifBbody = $('#modalNotifBody');

    showNotifModal.on('show.bs.modal', function (event) {
         getAndPrintAllNotificationsOfUser(notifBbody).then();
    });

    $(document).on('click', '.delete-notif', function (event) {

        let ntfId = $(this).attr('id');
        let id = ntfId.replace('notifDelBtnSubmit', '');

        fetch(`/api/user/notification/delete/${id}`, {
            method: 'DELETE',
        }).then( response => {
            if (response.ok) {
                $('#notifId' + id).detach();
                window.onload = getNumberOfNotificationsOfUser($('#notif_counter'));

            }
        })
    })

    $('#modalNotifCloseButton').on('click', function () {
        $('#modalNotifBody').empty();
    });
    // ------------------- Работа с модальным окном уведомлений -----------------------


});