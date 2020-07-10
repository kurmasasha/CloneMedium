async function getAndPrintAllTopicsOfUser(container) {
    fetch(`/api/user/MyTopics`)
        .then(result => result.json())
        .then(arrayTopics => {
            arrayTopics.forEach(function (topic) {
                let card = topicInCard(topic);
                container.append(card);
            })
        })
}