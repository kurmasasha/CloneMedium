async function getAndPrintModeratedTopics(container) {
    fetch(`/api/free-user/moderatedTopicsList/`)
        .then(result => result.json())
        .then(arrayTopics => {
            arrayTopics.forEach(function (topic) {
                let card = topicInCard(topic);
                container.append(card);
            })
        })
}

