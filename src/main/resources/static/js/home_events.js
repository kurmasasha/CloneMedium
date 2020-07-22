/**
 * Обработчики событий на странице home.html (/home)
 */
$(document).ready(function(){
    getAndPrintAllTopicsOfUser($('#topics_container'))
        .then();


    //--------------------------------------------------------------------------------
    /**
     *   Работа с модальным окном уведомлений
     */

    let notifDropList = $('#dropdownNotifList');

    $('#notification_bell').click(function(e) { e.preventDefault(); return false; });

    getAndPrintAllNotificationsOfUserDropList(notifDropList);

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
    })

    $('#modalNotifCloseButton').on('click', function () {
        $('#modalNotifBody').empty();
    });
    // ------------------- Работа с модальным окном уведомлений -----------------------

});