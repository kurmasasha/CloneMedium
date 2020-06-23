async function getNumberOfNotificationsOfUser(container) {
    fetch(`http://localhost:5050/api/notifications/user-count`)
        .then(result => result.text())
        .then(count => {
            container.empty();
            if (count > 0) {
                container.append(count);
            }
        })
}