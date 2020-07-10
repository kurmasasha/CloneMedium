/**
 * Поиск топиков по связанному с ними хэштегу
 * @param hashtag - текстовое представление хэштега
 * @param container - контейнер на странице, в который будут выведены результаты
 */

async function getTopicsOfUserByHashtag(hashtag, container) {
    fetch(`/api/free-user/get-topics-of-user-by-hashtag/${hashtag}`, {
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