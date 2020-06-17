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
            arrayTopics.forEach(function (topic) {
                let tags = '';
                $.each(topic.hashtags, function (index, tag) {
                    tags += '<a href="#">' + tag.name + '</a>';
                    if (index < (topic.hashtags.length-1)) {
                        tags += ' / ';
                    }
                })
                let authors = '';
                $.each(topic.authors, function (index, author) {
                    authors += '<a href="#">' + author.username + '</a>';
                    if (index < (topic.authors.length-1)) {
                        authors += ' / ';
                    }
                })
                let author_label = 'Автор: ';
                if (topic.authors.length > 1) {
                    author_label = 'Авторы: ';
                }
                let card =
                    '<div class="card mb-2">' +
                    '<h5 class="card-header">' + topic.title + '</h5>' +
                    '<div class="card-body">' +
                    '<h6 class="card-title">' + author_label + authors + '</h6>' +
                    '<h6 class="card-title">' + tags + '</h6>' +
                    '<p class="card-text">' + topic.content + '</p>' +
                    '</div>' +
                    '</div>';
                container.append(card);
            })
        })
}



// async function getAllTopicsByHashtag(id, hashtag, container) {
//     fetch(`http://localhost:5050/api/admin/get-all-topics-by-hashtag/${hashtag}`, {
//         headers: {
//             uid: id
//         }
//     })
//         .then(result => result.json())
//         .then(arrayTopics => {
//             container.empty();
//             container.append('<hr>');
//             arrayTopics.forEach(function (topic) {
//                 let authors = '';
//                 topic.authors.forEach(function(author) {
//                     authors += '<a href="/admin/oneUser/' + author.username + '">'+ author.username + '</a> ';
//                 })
//                 let output = '<div class="post-preview"> <a href="/topic/' + topic.id + '">'
//                     + '<h4 class="post-title">' + topic.title + '</h4> </a> '
//                     + '<p class="post-meta">Posted by ' + authors + 'on ' + topic.dateCreated + '</p>' + '</div> <hr>';
//                 container.append(output);
//             })
//         })
// }