async function getAndPrintAllTopicsOfUser(container) {
    fetch(`/api/user/MyTopics`)
        .then(result => result.json())
        .then(arrayTopics => {
            arrayTopics.forEach(function (topic) {
                let inCard = topicInCardMyTopic(topic);
                container.append(inCard);
            })
        })
}