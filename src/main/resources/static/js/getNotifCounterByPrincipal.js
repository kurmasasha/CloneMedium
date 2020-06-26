
// имитация нотификации; скрипт-заглушка

async function getNumberOfNotificationsOfUser(container) {
    fetch(`http://localhost:5050/api/user/MyNotifsNbr`)
        .then(result => result.text())
        .then(rndNotifNbr => {

                 if (rndNotifNbr > 0) {
                     $('#notif_counter').empty();
                     $('#notif_bell').css("color", "red");
                     container.append(rndNotifNbr);
                 }
        })
}