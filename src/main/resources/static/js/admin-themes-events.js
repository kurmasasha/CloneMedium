$(document).ready(function(){
    $('#admin_themes_link').addClass('active_side_bar');

    /**
     * обработчик нажатия на кнопку удаления темы
     */
    $(document).on('click', '.theme_delete_button', async function () {
        let id = $(this).attr('id').replace('theme_delete_', '');
        let response = await fetch(`/api/admin/deleteTheme`, {
            method: 'delete',
            headers: {
                "Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
            },
            body: 'id=' + id
        })
        if (response.ok) {
            $('#theme_row_' + id).detach();
        }
    });

    /**
     * обработчик нажатия на кнопку добавления темы
     */
    $(document).on('click', '#admin_theme_add_button', async function () {
        let name = $('#theme_input').val();
        let response = await fetch(`http://localhost:5050/api/admin/addTheme`, {
            method: 'post',
            headers: {
                "Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
            },
            body: 'name=' + name
        })
        if (response.ok) {
            let theme = await response.json()
            $('#themes_table').append(
                '<tr id="theme_row_' + theme.id + '">' +
                    '<th>' + theme.id + '</th>' +
                    '<th>' + theme.name + '</th>' +
                    '<th>' +
                        '<button id="theme_delete_' + theme.id + '" type="button" class="btn btn-dark theme_delete_button">' +
                            'Удалить' +
                        '</button>' +
                    '</th>' +
                '</tr>');
        }
    });

    // имитация нотификации
    $(document).ready(bellCount());
    $(document).ready(getAllNotifications());


    // удаление формы поиска по хэштегу
    $('#finderByHashtag').detach();
});