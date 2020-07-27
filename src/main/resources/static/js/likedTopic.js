$(document).ready(async function () {
    $('#single_topic_container').delegate('.fa', 'click', function () {

        $('#authorizationModal').modal('show');

        if(!($("#authorizationModal").data('bs.modal') || {})._isShown) {
            let id = $(this).attr('data-id');
            let likeCounter = document.getElementById('likeCounter');
            let dislikeCounter = document.getElementById('dislikeCounter');

            if($(this).hasClass("fa-thumbs-o-up")){
                liked_or_disliked(id, likeCounter, dislikeCounter, "addLike");
            }else{
                liked_or_disliked(id, likeCounter, dislikeCounter, "addDislike");
            }

            $(this).parent().siblings().removeClass("active");
            if($(this).parent().hasClass("active")) {
                $(this).parent().removeClass("active");
            }else{
                $(this).parent().addClass("active");
            }
        }
    });

    async function liked_or_disliked(id, likeCounter, dislikeCounter, path_to_like_or_dislike) {
        await fetch(`/api/topic/${path_to_like_or_dislike}/${id}`)
            .then(function(response) {
                    if (response.ok) {
                        response.json().then(function(topic) {
                            likeCounter.innerHTML = topic.likes;
                            dislikeCounter.innerHTML = topic.dislikes;
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
});

