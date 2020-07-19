$(document).ready(async function () {
    $('#comments_container').on('click', '.fa-thumbs-o-up', function () {

        //Если не авторизован пользователь, значит есть модальное окно на странице и его надо показать
        if($('#authorizationModal').length) {
            $('#authorizationModal').modal('show');
        } else {
            let id = $(this).attr('data-id');
            let likeCounter = $(this).siblings(".likes-num");
            liked(id, likeCounter);
        }
    });

    async function liked(id, likeCounter) {
        await fetch(`/api/comment/addLike/${id}`)
            .then(function(response) {
                    if (response.ok) {
                        response.json().then(function(comment) {
                            likeCounter.html(comment.likes)
                        });
                    } else if (response.status > 500) {
                        alert("Что то пошло не так, мы уже рашаем проблему")
                    }
                }
            )
            .catch(function(err) {
                console.log('Fetch Error :-S', err);
            });
    }
})