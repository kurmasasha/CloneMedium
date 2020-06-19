async function getAndPrintModeratedTopics(container) {
    fetch(`http://localhost:5050/api/free-user/moderatedTopicsList/`)
        .then(result => result.json())
        .then(arrayTopics => {
            arrayTopics.forEach(function (topic) {
                let card = topicInCard(topic);
                container.append(card);
            })
        })
}

