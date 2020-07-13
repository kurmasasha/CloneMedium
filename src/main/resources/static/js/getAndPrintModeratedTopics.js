async function getAndPrintModeratedTopics(container) {
    let URL;
    let uri = decodeURI(window.location.pathname);
    if (uri.includes("/topic/find/tag/")){   
        URL='http://localhost:5050/api/free-user/get-all-topics-by-hashtag/' + uri.replace('/topic/find/tag/', '');

    } else {
        URL=`http://localhost:5050/api/free-user/moderatedTopicsList/`;

    }
    fetch(URL)
        .then(result => result.json())
        .then(arrayTopics => {
            arrayTopics.forEach(function (topic) {
                let card = topicInCard(topic);
                container.append(card);
            })
        })
}