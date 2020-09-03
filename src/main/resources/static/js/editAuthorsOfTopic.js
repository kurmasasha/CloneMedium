function removeById(idTopic, idAuthor) {
    $.ajax('/api/topic/' + idTopic + '/author/' + idAuthor, {
        method: 'DELETE',
        success: function () {
            $('#table' + idTopic).find('#tr' + idAuthor).remove();
        },
        error: function () {
            $('#authors' + idTopic).prepend('<div class="alert alert-danger" id="warning" role="alert">' +
                'Нельзя удалить всех авторов!</div>')
            setTimeout(() => $('#warning').remove(), 700);
        }
    })
}
function  addByEmail(idTopic) {
    let email = $('#addNewAuthor' + idTopic).val();
    $.ajax('/api/topic/' + idTopic + '/author', {
        data: {username: email},
        method: 'POST',
        success: function (data) {
            $('#table' + idTopic).append('<tr id="tr' + data + '">' +
                 '<td >' + email + '</td>' +
                 '<td><a onclick="removeById('+ idTopic +',' + data + ')">' +
                 '<i class="fa fa-trash" aria-hidden="true"></i> </a> </td>' +
                 '</tr>');
            $('#addNewAuthor' + idTopic).val("");
        },
        error: function () {
            $('#authors' + idTopic).prepend('<div class="alert alert-danger" id="warning" role="alert">' +
                'Автор не найден!</div>')
            setTimeout(() => $('#warning').remove(), 700);
            $('#addNewAuthor' + idTopic).val("");
        }
    })
}



