$('#replyButton').on('click', function () {
    $('#' + $(this).attr('data-panelId')).toggle();
});

$('#addReplyButton').on('click', function () {
    $('#authorizationModal').modal('show');

    let comment = document.getElementById('inputReply').value;
    let path = location.pathname.split('/');
    let topicId = path[path.length - 1];
    let isMainComment = false;
    let mainCommentId = $(this).attr('data-id');

    if (comment !== '') {
        let data = {
            comment: comment,
            topicId: topicId,
            isMainComment: isMainComment,
            mainCommentId: mainCommentId
        }
        document.getElementById('inputReply').value = '';

        fetch('/api/user/comment/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(data)

        })
            .then(result => result.json())
            .then(comment => {
                let commentAnswerCard = commentInCard(comment);
                $('#comments_answer_container').prepend(commentAnswerCard);
            });
    }

    $('#' + $(this).attr('data-panelId')).toggle();
});
