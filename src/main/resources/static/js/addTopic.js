async function addTopic(title, content, completed, img) {
    const formData = new FormData();
    formData.append('title', title)
    formData.append('content', content)
    formData.append('completed', completed)
    formData.append('file', img)

    return await fetch('/api/user/topic/add', {
        method: 'POST',
        enctype: 'multipart/form-data',
        body: formData
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


