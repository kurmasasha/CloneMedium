$(document).ready(async function () {
    $('#topics_container').delegate('.fa-thumbs-o-up', 'click', function () {
        let id = $(this).attr('data-id');
        let likeCounter = $(this).siblings(".text-info");
        liked(id, likeCounter);
    });

    async function liked(id, likeCounter) {
        await fetch(`http://localhost:5050/api/topic/addLike/${id}`)
            .then(function(response) {
                    if (response.ok) {
                        response.json().then(function(topic) {
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

