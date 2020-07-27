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

    $('#notification_bell').click(function(e) { e.preventDefault(); return false; });

    // $('#dropdownMenuButton').click(function (e) {
    //
    // })

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