async function getAndPrintAllTopicsOfUser(userId, container) {
    fetch(`http://localhost:5050/api/admin/TopicsByUser/${userId}`)
        .then(result => result.json())
        .then(arrayTopics => {
            arrayTopics.forEach(function (topic) {
                let card = topicInCard(topic);
                container.append(card);
            })
        })
}