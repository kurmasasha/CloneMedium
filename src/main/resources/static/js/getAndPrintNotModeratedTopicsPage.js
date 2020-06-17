/**
 * Вывод всех топиков в контейнер
 *
 */
async function getAndPrintNotModeratedTopicsPage(page, container) {
    fetch(`http://localhost:5050/api/admin/notModeratedTopicsPage/${page}`)
        .then(result => result.json())
        .then(response => {
            response.forEach(function (topic) {
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
                                '<a href="#" class="btn btn-dark">Модерация</a>' +
                            '</div>' +
                    '</div>';
                container.append(card);
            })
        })
}