/**
 * Вывод всех пользователей в контейнер
 *
 */
async function getAndPrintAllUsers(container) {
    fetch(`/api/admin/allUsers`)
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
                        `<td><a href="form_edit_user/${user.id}" class="btn btn-info text-white">редактировать</a></td>` +
                    '</tr>';
                container.append(row);
            })
        })
}