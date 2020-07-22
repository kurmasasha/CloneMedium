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
                let card = topicInCardMyTopic(result);
                successAddTopic(alert_container, 2000);
                $('#topics_container').prepend(card);
            } else {
                failAddTopic($('#alerts_container'), result, 4000)
            }
        })
}

async function updateTopic(topic_id, title, content, completed, img) {

    let ids = $("#topic_id").val()
    let tit = $("#topic_title").val()
    let cont = $("#topic_content").val()
    let compl = $("#content")

    const topic = {
        "id": topic_id,
        "title": title,
        "content": content,
        "completed": completed,
    };

    let formDataStp = new FormData()
    const blob = new Blob([JSON.stringify(topic)], {
        type: 'application/json'
    });

    console.log(blob)
    formDataStp.append("json", blob)
    let file_topic = $("#file").val()
    formDataStp.append("file", img)

    let alert_container = $('#alerts_container');

    let OK = false;
    return await fetch('/api/user/topic/update', {
        method: 'POST',
        enctype: 'multipart/form-data',
        headers: {
            'Accept': 'application/json',
        },
        mode: 'cors',
        body: formDataStp
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
                let card = topicInCardMyTopic(result);
                successUpdateTopic(alert_container, 2000);
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

function successUpdateTopic(container, time) {
    container.append('<div class="alert alert-success m-3" role="alert">Ваша статья, успешно изменена</div>');
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


