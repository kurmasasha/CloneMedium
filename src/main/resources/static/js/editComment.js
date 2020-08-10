$(document).ready(function () {

    $('#comments_container').on("click", ".fa", async function () {
        if ($(this).hasClass("fa-pencil-square-o")) {
            let id = $(this).attr("data-id");
            let comment = await fetch("/api/user/comment/" + id).then(function (response) {
                return response.json();
            });
            $("#editCommentModalText").val(comment.text);

            $("#editCommentModalButton").on("click", function () {
                let newText = $("#editCommentModalText").val();
                if (newText !== '') {
                    comment.text = newText;
                    return fetch('/api/user/comment/update', {
                        method: 'POST',
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


        }
    })
});