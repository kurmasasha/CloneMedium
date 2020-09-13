$(document).on('click', '.response-button', function () {
    $('#' + $(this).attr('data-panelId')).toggle();
})

$(document).on('click', '#addReplyButton', async function () {
    $('#authorizationModal').modal('show');

    let comment = document.getElementById('inputReply').value;
    let path = location.pathname.split('/');
    let topicId = path[path.length - 1];
    let mainCommentId = $(this).attr('data-id');

    if (comment !== '') {
        let data = {
            text: comment,
            topicId: topicId,
            isMainComment: false,
            mainCommentId: mainCommentId
        }

        await fetch('/api/user/comment/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(data)
        })

    }

    $('#' + $(this).attr('data-panelId')).toggle();

    await fillCommentContainer();
});
