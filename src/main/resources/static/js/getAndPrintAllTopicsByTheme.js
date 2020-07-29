async function getAndPrintAllTopicsByTheme(themeId, container) {
    fetch('/api/free-user/get-all-topics-by-theme/' + themeId + '')
        .then(result => result.json())
        .then(arrayTopics => {
            arrayTopics.forEach(function (topic) {
                let article = topicInCard(topic);
                container.append(article);
            })
        })
}