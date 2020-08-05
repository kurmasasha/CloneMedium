$(document).ready( async function () {
    $('#comments_container').on('click',".fa", async function (e) {
        if($(this).hasClass("fa-trash")){
            let id = $(this).attr('data-id');
             await fetch(`/api/user/comment/delete/${id}`, {method: "delete"})
                .then(function (response) {
                        if (response.ok) {
                            $('#commentCard-' + id).remove();
                        } else if (response.status > 500) {
                            alert("Что то пошло не так, мы уже рашаем проблему");
                        }
                    }
                )
        }
    })
})
