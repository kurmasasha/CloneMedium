$(function () {
    $('#delete').on('click', event => deleteTopic(event));
})

function deleteTopicForm(id) {

    $.ajax({
        url: '/api/user/topic/' + id,
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function (topic) {
            $('#deleteTopicId').val(topic.id);
            $('#deleteTopicTitle').val(topic.title);
            $('#deleteTopicContent').val(topic.content);

            $('#delete-modal').modal('show');
        }
    })
}

function deleteTopic(e) {
    e.preventDefault();

    let id = $('#deleteTopicId').val() ;
    console.log("id: " + id)

    $.ajax({
        url: '/api/user/topic/delete/' + id,
        type: 'DELETE',
        success: function () {
            $('#delete-modal').modal('hide');
            location.reload();
        }
    })
}