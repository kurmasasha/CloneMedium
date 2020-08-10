/**
 * Вывод всех пользователей в контейнер
 *
 */

function enableBtn(url) {
    $.ajax({
        url: url,

        contentType: "application/json",
        dataType: 'json',
        complete: function (data) {
            location.reload()
        }
    });
}


async function getAndPrintAllUsers(container) {
    // получаем id авторизированного админа.
    let currentId;
    fetch('/api/admin/currentUser')
        .then(current => current.json())
        .then(response => {
            currentId = response.id;
        })

    fetch(`/api/admin/allUsers`)
        .then(result => result.json())
        .then(response => {
            response.forEach(function (user, index) {

                url = "/api/admin/enable/";
                let row2;
                // генерим кнопки деактивации для всех, кроме авторизированного админа.
                if(currentId!==user.id) {
                    // если юзер активный , генерим кнопку деактивации.
                    if (user.lockStatus) {
                        row2 =
                            `<td><button type="submit" class="btn btn-danger text-white" onclick='enableBtn(url+ ${user.id})'>деактивировать</button></td>` +
                            '</tr>';
                    }
                    // если юзер деактив, генерим кнопку активации.
                    else {
                        row2 =
                            `<td><button type="submit" class="btn btn-success text-white" onclick='enableBtn(url+ ${user.id})'>активировать</a></td>` +
                            '</tr>';
                    }
                }

                let row =
                    '<tr>' +
                    '<th scope="row">' + ++index + '</th>' +
                    '<td>' + user.firstName + '</td>' +
                    '<td>' + user.lastName + '</td>' +
                    '<td>' + user.username + '</td>' +
                    '<td>' + user.role.name + '</td>' +

                    `<td><a href="form_edit_user/${user.id}" class="btn btn-info text-white">редактировать</a></td>`+
                    row2;

                container.append(row);

            })
        })
}