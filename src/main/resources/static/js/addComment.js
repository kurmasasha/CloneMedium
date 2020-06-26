document.getElementById('add_comment_button').onclick = function () {
    let commentBody = document.getElementById('textareaResize');
    let comment = commentBody.value;
    let path = location.pathname.split('/');
    let topicId = path[path.length - 1];

    let data = {
        comment: comment,
        topicId: topicId
    }

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
