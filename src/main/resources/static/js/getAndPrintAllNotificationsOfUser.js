async function getAndPrintAllNotificationsOfUser(container) {

    fetch(`http://localhost:5050/api/user/MyNotifs`)
        .then(result => result.json())
        .then(arrayNotifs => {
            arrayNotifs.forEach(function (notification) {
                let notif = `<div id="notifId${notification.id}">`
                    + '<div style="display: flex;">' + '<p>' + '<strong>'
                    + notification.text
                    + '</strong>' + '</p>'
                    + `<div>`
                    + `<button type="button" class="btn-sm btn-warning delete-notif" id='notifDelBtnSubmit${notification.id}'>Просмотрено</button>`
                    + `</div>`
                    + '</div>' + '</div>';
                container.append(notif);
            })
        })
}


