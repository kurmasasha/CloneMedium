$('#comments_container').on('click','.edit-comment', async function () {
    let id = $(this).attr('data-id');
    console.log(id);
    let comment = await fetch("/api/user/comment/" + id).then(function (response) {
        return response.json();
    });
    $("#editCommentModalText").val(comment.text);

    $("#editCommentConfirm").on("click", function () {
        let newText = $("#editCommentModalText").val();
        if (newText !== '') {
            comment.text = newText;
            return fetch('/api/user/comment/update', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(comment)
            }).then(function (response) {
                if (response.ok) {
                    $("#editCommentModal").modal("hide");
                    $("#commentCardText-" + id).text(newText);
                }
            })
        } else {
            alert("Комментарий не должен быть пустым");
        }
    })
});

