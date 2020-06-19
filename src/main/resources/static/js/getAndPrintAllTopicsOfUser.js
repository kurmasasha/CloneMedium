async function getAndPrintAllTopicsOfUser(container) {
    fetch(`http://localhost:5050/api/user/MyTopics`)
        .then(result => result.json())
        .then(arrayTopics => {
            arrayTopics.forEach(function (topic) {
                let card = topicInCard(topic);
                container.append(card);
            })
        })
}