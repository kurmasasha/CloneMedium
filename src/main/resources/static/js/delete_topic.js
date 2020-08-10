document.getElementById('delete_topic_btn').onclick = function () {
    const id = $(this).attr('data-id');

    let info_container = $('#info_container');

    return fetch('/api/user/topic/delete/' + id, {
        method: 'DELETE',
    })
        .then(response => {
            if (response.ok) {
                successDeleteTopic(info_container, 2000);
            } else {
                errorDeleteTopic(info_container, response.text(), 2000);
            }
        })
}

function successDeleteTopic(container, time) {
    container.append('<div class="alert alert-success m-3" role="alert">Ваша статья, успешно удалена</div>');
    setTimeout(function () {
        container.empty();
        $('#deleteTopicModal').modal('hide');
        location.replace("http://localhost:5050");
    }, time)
}

function errorDeleteTopic(container, error, time) {
    container.append(`<div class="alert alert-danger m-3" role="alert">${error}</div>`);
    setTimeout(function () {
        container.empty();
    }, time)
}

