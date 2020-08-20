
// Получение количества нотификаций
async function getNumberOfNotificationsOfUser(container) {
    fetch(`/api/user/MyNotifsNbr`)
        .then(response => response.json())
        .then(result => {
            if (result.length > 0) {
                $('#notif_counter').empty();
                container.append(result.length);
            }
        })
}