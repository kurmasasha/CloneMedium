$(function () {
    $('#edit').on('click', event => editTopic(event));
})

function editTopicForm(id) {

    $.ajax({
        url: '/api/user/topic/' + id,
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function (topic) {
            $('#editTopicId').val(topic.id);
            $('#editTopicTitle').val(topic.title);
            $('#editTopicContent').val(topic.content);

            $('#edit-modal').modal('show');
        }
    })
}

function editTopic(e) {
    e.preventDefault();

    let topic = {
        id: $('#editTopicId').val(),
        title: $('#editTopicTitle').val(),
        content: $('#editTopicContent').val()
    };

    $.ajax({
        url: '/api/user/topic/update',
        method: 'PUT',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(topic),
        complete: function () {
            $('#edit-modal').modal('hide');
            location.reload();
        }
    })
}