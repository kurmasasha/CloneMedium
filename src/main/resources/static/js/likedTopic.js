$(document).ready(async function () {
    $('#single_topic_container').delegate('.fa-thumbs-o-up', 'click', function () {
        let id = $(this).attr('data-id');
        let likeCounter = document.getElementById('likeCounter');
        let dislikeCounter = document.getElementById('dislikeCounter');
        liked(id, likeCounter, dislikeCounter);
    });

    $('#single_topic_container').delegate('.fa-thumbs-o-down', 'click', function () {
        let id = $(this).attr('data-id');
        let likeCounter = document.getElementById('likeCounter');
        let dislikeCounter = document.getElementById('dislikeCounter');
        disliked(id, likeCounter, dislikeCounter);
    });

    async function liked(id, likeCounter, dislikeCounter) {
        await fetch(`/api/topic/addLike/${id}`)
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

    async function disliked(id, likeCounter, dislikeCounter) {
        await fetch(`/api/topic/addDislike/${id}`)
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

