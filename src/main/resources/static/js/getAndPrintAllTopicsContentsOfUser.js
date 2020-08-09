$(document).ready(function () {
    let topicCards = document.getElementsByClassName("topic_content");
    for (let i = 0, len = topicCards.length; i < len; i++) {
        let id = topicCards[i].id;
        let topicContent = topicCards[i].getAttribute("data-topic-content");
        $(document.getElementById(id)).append(linkify(topicContent));
    }
});