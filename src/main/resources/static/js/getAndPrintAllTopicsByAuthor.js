async function getAndPrintAllTopicsByAuthor(authorId, container) {
    fetch('/api/free-user/get-all-topics-by-author/' + authorId + '')
        .then(result => result.json())
        .then(arrayTopics => {
            arrayTopics.forEach(function (topic) {
                let card = topicInCard(topic);
                container.append(card);
            })
        })
}