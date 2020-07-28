$(document).ready(function(){
    $('.menu a').filter(function(){
        return this.href==location.href}).parent().addClass('active').siblings().removeClass('active');

    $('.menu a').click(function(){
        $(this).parent().addClass('active').siblings().removeClass('active')
    });

    /**
     *  обновляется "колокольчик" - счётчик уведомлений
     */
    window.onload = getNumberOfNotificationsOfUser($('#notif_counter'));
    setInterval( function () { getNumberOfNotificationsOfUser($('#notif_counter')).then(); }, 5000);

    /**
     *  обновляется выпадающий список уведомлений
     */
    $('#dropdownMenuButton').on('click', function () {
        getAndPrintAllNotificationsOfUserDropList($('#dropdownNotifList')).then();
    })

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

    $('#notification_bell').click(function(e) { e.preventDefault(); return false; });
    $('#notifTitleInModal').click(function(e) { e.preventDefault(); return false; });
    $('#modalNotifBody').click(function(e) { e.preventDefault(); return false; });

    $(document).on('click', '.dropdown-item', function (event) {
        let ntfDrpDwnId = $(this).attr('id');
        let notifId = ntfDrpDwnId.replace('notifIdDl', '');

        getNotificationById(notifId)

        $('#showNotifModal').modal('show');
    })

    $(document).on('click', '.delete-notif', function (event) {

        let ntfId = $(this).attr('id');
        let id = ntfId.replace('notifDelBtnSubmit', '');

        fetch(`/api/user/notification/delete/${id}`, {
            method: 'DELETE',
        }).then( response => {
            if (response.ok) {
                $('#notifId' + id).detach();
                $('#notifIdDl' + id).detach();
                window.onload = getNumberOfNotificationsOfUser($('#notif_counter'));
            }
        })

        $('#showNotifModal').modal('hide');

    })

    let dropdownBtnCounter = 0;
    let drpDwnBtn = document.querySelector(`#dropdownMenuButton`);
    drpDwnBtn.addEventListener('click', evt => {
        dropdownBtnCounter++;
        if (dropdownBtnCounter === 1) {
            dropdownBtnCounter = 0;
            $('#dropdownNotifList').empty();
        }
    });


    $('#modalNotifCloseButton').on('click', function () {
        $('#modalNotifBody').empty();
        $('.delete-notif').detach();
    });
    // ------------------- Работа с модальным окном уведомлений -----------------------

    document.body.addEventListener("click", function (evt) {
         $('#modalNotifBody').empty();
         $('.delete-notif').detach();
         $('#dropdownNotifList').empty();
    });

});