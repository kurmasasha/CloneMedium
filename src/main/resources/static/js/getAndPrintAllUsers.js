/**
 * Вывод всех пользователей в контейнер
 *
 */
async function getAndPrintAllUsers(container) {
    fetch(`http://localhost:5050/api/admin/allUsers`)
        .then(result => result.json())
        .then(response => {
            response.forEach(function (user, index) {
                let row =
                    '<tr>' +
                        '<th scope="row">' + ++index + '</th>' +
                        '<td>' + user.firstName + '</td>' +
                        '<td>' + user.lastName + '</td>' +
                        '<td>' + user.username + '</td>' +
                        '<td>' + user.role.name + '</td>' +
                        '<td>' + user.email + '</td>' +
                    '</tr>';
                container.append(row);
            })
        })
}