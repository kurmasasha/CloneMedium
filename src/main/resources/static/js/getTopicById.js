async function getTopicById(id) {
    fetch(`http://localhost:5050/api/user/topic/${id}`)
        .then(result => result.json())
        .then(topic => {
            document.getElementById("topicAuthor").append(topic.authors[0].firstName + " " + topic.authors[0].lastName);
            document.getElementById("topicTitle").append(topic.title);
            document.getElementById("topicContent").append(topic.content);
            let hastags = $('#hashtags');
            $.each(topic.hashtags, function (index, value) {
                hastags.append('<a id="ht_' + value.id + '" href="#"><h6>' + value.name + '</h6></a>');
            })
        })
}

let topicId = document.getElementById("topicId").dataset.topicId;
getTopicById(topicId)


