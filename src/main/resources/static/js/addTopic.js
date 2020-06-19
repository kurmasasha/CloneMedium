async function addTopic(title, content) {
    let topic_object = {
        title: title,
        content: content,
    }
    return  fetch('/api/user/topic/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(topic_object)
    });
}

function successAddTopic(container, time) {
    container.append('<div class="alert alert-success m-3" role="alert">Ваша статья, успешно добавлена</div>');
    setTimeout(function () {
        container.empty();
        $('#modalWindowCreateTopic').modal('hide');
    }, time)
}

function noValidForm(container, time) {
    container.append('<div class="alert alert-danger m-3" role="alert">Поля не должны быть пустыми</div>');
    setTimeout(function () {
        container.empty();
    }, time)
}

function failAddTopic(container, time) {
    container.append('<div class="alert alert-danger m-3" role="alert">Что то пошло не так! Попробуйте снова</div>');
    setTimeout(function () {
        container.empty();
    }, time)
}


