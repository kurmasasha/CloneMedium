document.getElementById('add_comment_button').onclick = function (e) {

    //Открывает модальное окно для неавторизованных пользователей
    $('#authorizationModal').modal('show');

    let commentBody = document.getElementById('textareaResize');
    let comment = commentBody.value.replace(/\n/g, '<br />');
    let path = location.pathname.split('/');
    let topicId = path[path.length - 1];

    if(($("#authorizationModal").data('bs.modal') || {})._isShown || comment !== '') {
        e.preventDefault();
    }

    if(!(($("#authorizationModal").data('bs.modal') || {})._isShown) && comment !== '') {
        let data = {
            comment: comment,
            topicId: topicId
        }

        document.getElementById('textareaResize').value = '';
        document.getElementById('textareaResize').style.height = "auto";
        $('#counter').html(2000);

        return fetch('/api/user/comment/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(data)

        })
            .then(result => result.json())
            .then(comment => {
                let commentCard = commentInCard(comment);
                $('#comments_container').prepend(commentCard);
            });
    }
}
