/**
 * Вызов модального окна
 */
/*$('#modal_add-topic_button').on('click', function () {
    $('#topic_title').val('');
    $('#topic_content').val('');
    $('#checkboxCompletedTopic').prop('checked', false);
    $('#topic_img').val('')
});*/
/**
 * Нажатие на кнопку добавления топика
 */
/*$('#add_topic_button').on('click', async function (event) {
    event.preventDefault()
    let title = $('#topic_title').val();
    let content = $('#topic_content').val();
    let completed = $('#checkboxCompletedTopic').prop('checked');
    let img = $('#topic_img').prop('files')[0];

    await addTopic(title, content, completed, img)
});*/

async function addTopic(title, content, completed, img) {

    const formData = new FormData();
    formData.append('title', title)
    formData.append('content', content)
    formData.append('completed', completed)
    formData.append('file', img)

    let alert_container = $('#alerts_container');

    let OK = false;
    return await fetch('/api/user/topic/add', {
        method: 'POST',
        enctype: 'multipart/form-data',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                OK = true;
                return response.json();
            }
            return response.text()
        })
        .then(result => {
            if (OK) {
                let card = topicInCard(result);
                successAddTopic(alert_container, 2000);
                $('#topics_container').prepend(card);
            } else {
                failAddTopic($('#alerts_container'), result, 4000)
            }
        })
}

async function updateTopic(topic_id, title, content, completed, img) {

    const formData = new FormData();
    formData.append('topic_id', topic_id)
    formData.append('title', title)
    formData.append('content', content)
    formData.append('completed', completed)
    formData.append('file', img)

    let alert_container = $('#alerts_container');

    let OK = false;
    return await fetch('/api/user/topic/update', {
        method: 'POST',
        enctype: 'multipart/form-data',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                OK = true;
                return response.json();
            }
            return response.text()
        })
        .then(result => {
            if (OK) {
                let card = topicInCard(result);
                successAddTopic(alert_container, 2000);
                document.getElementById("topic_" + topic_id).remove();
                $('#topics_container').prepend(card);
            } else {
                failAddTopic($('#alerts_container'), result, 4000)
            }
        })
}

function successAddTopic(container, time) {
    container.append('<div class="alert alert-success m-3" role="alert">Ваша статья, успешно добавлена</div>');
    setTimeout(function () {
        container.empty();
        $("#topic_coauthor").val([]).change();
        $("#topic_img").val('');
        $('#modalWindowCreateTopic').modal('hide');
    }, time)
}
function failAddTopic(container, error, time) {
    container.append(`<div class="alert alert-danger m-3" role="alert">${error}</div>`);
    setTimeout(function () {
        container.empty();
        $("#topic_coauthor").val([]).change();
    }, time)
}