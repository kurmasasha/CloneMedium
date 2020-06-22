async function getNumberOfNotificationsOfUser(container) {
    fetch(`http://localhost:5050/api/user/MyNotifs`)
        .then(result => result.json())

        .then(arrayNotifs => {

            if (arrayNotifs.length > 0) {
                $('#notif_counter').empty();
                $('#notif_bell').css("color", "red");
                container.append(arrayNotifs.length);
            }

        })
}