async function getAndPrintAllNotificationsOfUser(container) {
    fetch(`http://localhost:5050/api/user/MyNotifs`)
        .then(result => result.json())
        .then(arrayNotifs => {
            arrayNotifs.forEach(function (notification) {

                let notif = `<div id="notifId${notification.id}"></div>`
                             + '<div style="display: flex;">' + '<p>' + '<strong>'
                             + notification.text
                             + '</strong>' + '</p>'
                             + `<div class="text-right">`
                             + `<button type="button" class="btn-sm btn-warning" id='notifDelBtnSubmit${notification.id}'>Просмотрено</button>`
                             + `</div>`
                             + '</div>' + '<br>';

                container.append(notif);

                $(`#notifDelBtnSubmit${notification.id}`).on('click', function (event) {
                    event.preventDefault()
                    console.log("click notifDelBtnSubmit")
                    fetch(`/api/user/notification/delete/${notification.id}`, {
                        method: 'DELETE',
                    })



                        //$(`#showNotifModal`).reload()
                        //.then( getAndPrintAllNotificationsOfUser( $('#modalNotifBody') ) )
                        //.then( this.getAndPrintAllNotificationsOfUser( container ) )
                })
            })
        })
}


