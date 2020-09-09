document.getElementById('add_comment_button').onclick = async function (e) {

    //Открывает модальное окно для неавторизованных пользователей
    $('#authorizationModal').modal('show');

    let commentBody = document.getElementById('textareaResize');
    let comment = commentBody.value.replace(/\n/g, '<br />');
    let path = location.pathname.split('/');
    let topicId = path[path.length - 1];

    if (($("#authorizationModal").data('bs.modal') || {})._isShown || comment !== '') {
        e.preventDefault();
    }

    if (!(($("#authorizationModal").data('bs.modal') || {})._isShown) && comment !== '') {
        let data = {
            comment: comment,
            topicId: topicId,
            isMainComment: true,
            mainCommentId: null
        }

        document.getElementById('textareaResize').value = '';
        document.getElementById('textareaResize').style.height = "auto";
        $('#counter').html(2000);

        await fetch('/api/user/comment/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(data)

        })

        await fillCommentFields()
    }
}

