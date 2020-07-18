async function getTopicById(id) {
    fetch(`/api/user/topic/${id}`)
        .then(result => result.json())
        .then(topic => {
            document.getElementById("topicAuthor").append(topic.authors[0].firstName + " " + topic.authors[0].lastName);
            document.getElementById("topicTitle").append(topic.title);
            document.getElementById("topicContent").append(topic.content);
            document.getElementById("topicImg").setAttribute("src",'/topic-img/' + topic.img);

            let like = `<i class="fa fa-thumbs-o-up" id="iconLikeOfTopic-${topic.id}"  data-id="${topic.id}"></i>` +
                `<span class="text-info" id="likeCounter" data-id="${topic.id}">${topic.likes}</span>`;
            document.getElementById("like_block").innerHTML = like;

            let dislike = `<i class="fa fa-thumbs-o-down fa1" id="iconDislikeOfTopic-${topic.id}"  data-id="${topic.id}"></i>` +
                `<span class="text-danger" id="dislikeCounter" data-id="${topic.id}">${topic.dislikes}</span>`;
            document.getElementById("dislike_block").innerHTML = dislike;

            let hastags = $('#hashtags');
            $.each(topic.hashtags, function (index, value) {
                hastags.append('<a id="ht_' + value.id + '" href="#"><h6>' + value.name + '</h6></a>');
            })
        })
}

let topicId = document.getElementById("topicId").dataset.topicId;
getTopicById(topicId)


