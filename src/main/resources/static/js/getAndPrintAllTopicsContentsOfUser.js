$(document).ready(function () {
    let topicCards = document.getElementsByClassName("card-text-onCard");
    for (let i = 0, len = topicCards.length; i < len; i++) {
        let id = topicCards[i].id;
        let topicContent = topicCards[i].getAttribute("data-card-topic-content");
        $(document.getElementById(id)).append(linkify(topicContent));
    }
});