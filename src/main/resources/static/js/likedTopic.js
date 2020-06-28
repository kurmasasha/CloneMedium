$(document).ready(async function () {
    $('#topics_container').delegate('.fa-heart', 'click', function () {
        console.log("click fa heart")
        let id = $(this).attr('data-id');
        let likeCounter = $(this).siblings(".text-info");
        increaseLike(id, likeCounter);
    });

    async function increaseLike(id, likeCounter) {
        await fetch(`http://localhost:5050/api/topic/addLike/${id}`)
            .then(function(response) {
                    if (response.ok) {
                        response.json().then(function(topic) {
                            console.log(topic)
                            let iconLikeOfTopic = $(`#iconLikeOfTopic-${id}`);
                            if (iconLikeOfTopic.hasClass('red')) {
                                iconLikeOfTopic.removeClass('red')
                            } else {
                                iconLikeOfTopic.addClass('red')
                            }
                            likeCounter.html(topic.likes)
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

