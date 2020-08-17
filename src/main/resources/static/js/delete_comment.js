$('#comments_container').on('click','.delete-comment', function () {
        let id = $(this).attr('data-id');
        $('#deleteCommentConfirm').on('click', async function () {
            await fetch(`/api/user/comment/delete/${id}`, {method: "delete"})
                .then(function (response) {
                        if (response.ok) {
                            $("#deleteCommentModal").modal("hide");
                            $('#commentCard-' + id).remove();

                        } else if (response.status > 500) {
                            alert("Что то пошло не так, мы уже рашаем проблему");
                        }
                    }
                )
        });
});





