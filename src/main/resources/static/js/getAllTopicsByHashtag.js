/**
 * Поиск топиков по связанному с ними хэштегу
 * @param id - id потльзователя
 * @param hashtag - текстовое представление хэштега
 * @param container - контейнер на странице, в который будут выведены результаты
 */

async function getAllTopicsByHashtag(id, hashtag, container) {
    fetch(`http://localhost:5050/api/admin/get-all-topics-by-hashtag/${hashtag}`, {
        headers: {
            uid: id
        }
    })
        .then(result => result.json())
        .then(arrayTopics => {
            container.empty();
            container.append('<hr>');
            arrayTopics.forEach(function (topic) {
                let authors = '';
                topic.authors.forEach(function(author) {
                    authors += '<a href="/admin/oneUser/' + author.username + '">'+ author.username + '</a> ';
                })
                let output = '<div class="post-preview"> <a href="/topic/' + topic.id + '">'
                    + '<h4 class="post-title">' + topic.title + '</h4> </a> '
                    + '<p class="post-meta">Posted by ' + authors + 'on ' + topic.dateCreated + '</p>' + '</div> <hr>';
                container.append(output);
            })
        })
}