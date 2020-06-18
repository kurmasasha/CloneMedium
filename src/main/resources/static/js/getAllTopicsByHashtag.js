/**
 * Поиск топиков по связанному с ними хэштегу
 * @param id - id потльзователя
 * @param hashtag - текстовое представление хэштега
 * @param container - контейнер на странице, в который будут выведены результаты
 */

async function getAllTopicsByHashtag(id, hashtag, container) {
    fetch(`http://localhost:5050/api/free-user/get-all-topics-by-hashtag/${hashtag}`, {
        headers: {
            uid: id
        }
    })
        .then(result => result.json())
        .then(arrayTopics => {
            container.empty();
            arrayTopics.forEach(function (topic) {
                let card = topicInCard(topic);
                container.append(card);
            })
        })
}