async function getAndPrintAllTopics(container) {
    fetch(`http://localhost:5050/api/free-user/totalTopicsList/`)
        .then(result => result.json())
        .then(arrayTopics => {
            arrayTopics.forEach(function (topic) {
                let card = topicInCard(topic);
                container.append(card);
            })
        })
}

