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



async function getAndPrintAllNotificationsOfUserDropList(container) {

    fetch(`http://localhost:5050/api/user/MyNotifs`)
        .then(result => result.json())
        .then(arrayNotifs => {
            arrayNotifs.forEach(function (notification) {

                let notifDL = `<a id="notifIdDl${notification.id}" class="dropdown-item">` + `${notification.title}</a>`

            //         `<div id="notifId${notification.id}">`
            //         + '<div style="display: flex;">' + '<p>' + '<strong>'
            //         + notification.text
            //         + '</strong>' + '</p>'
            //         + `<div>`
            //         + `<button type="button" class="btn-sm btn-warning delete-notif" id='notifDelBtnSubmit${notification.id}'>Просмотрено</button>`
            //         + `</div>`
            //         + '</div>' + '</div>';
            //
            // <a class="dropdown-item" href="#">Action</a>

                container.append(notifDL);
            })
        })
}

async function getNotificationById(id) {
    fetch(`http://localhost:5050/api/user/notification/${id}`)
        .then(result => result.json())
        .then(notification => {

            let notif = `<div id="notifId${notification.id}">`
                + '<div style="display: flex;">' + '<p>' + '<strong>'
                + notification.text
                + '</strong>' + '</p>'
                + `<div>`
                + `<button type="button" class="btn-sm btn-warning delete-notif" id='notifDelBtnSubmit${notification.id}'>Просмотрено</button>`
                + `</div>`
                + '</div>' + '</div>';

            let notifBbody = $('#modalNotifBody');

            notifBbody.append(notif);

        })
}

