$(document).ready(async function () {

    $('#comments_container').on('click', '.fa', function () {
        //Если не авторизован пользователь, значит есть модальное окно на странице и его надо показать
        if($('#authorizationModal').length) {
            $('#authorizationModal').modal('show');
        } else {
            let id = $(this).attr('data-id');
            let likeCounter = $('#likesId-'+id);
            let dislikeCounter = $('#dislikesId-'+id);

            //поставить лайк или дизлайк
            if ($(this).hasClass("fa-thumbs-o-up")) {
                liked(`/api/comment/addLike/${id}`,likeCounter, dislikeCounter);
            } else {
                liked(`/api/comment/addDislike/${id}`,likeCounter, dislikeCounter);
            }

            //подсветить или убрать подсветку
            $(this).parent().siblings().removeClass("rated");
            if ($(this).parent().hasClass("rated")) {
                $(this).parent().removeClass("rated");
            } else {
                $(this).parent().addClass("rated");
            }
        }
    });

    async function liked(url, likeCounter, dislikeCounter) {
        try {
            let response = await fetch(url);
            if (response.ok) {
                let comment = await response.json();
                likeCounter.html(comment.likes);
                dislikeCounter.html(comment.dislikes);


            } else if (response.status > 400) {
                alert("Что то пошло не так, мы уже рашаем проблему");
            }
        } catch (e) {
            console.log(e);
        }
    }
})