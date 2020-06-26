function topicInCard(topic) {
    let tags = '';
    $.each(topic.hashtags, function (index, tag) {
        tags += '<a href="#">' + tag.name + '</a>';
        if (index < (topic.hashtags.length - 1)) {
            tags += ' / ';
        }
    })
    let authors = '';
    $.each(topic.authors, function (index, author) {
        authors += '<a href="#">' + author.username + '</a>';
        if (index < (topic.authors.length - 1)) {
            authors += ' / ';
        }
    })
    let author_label = 'Автор: ';
    if (topic.authors.length > 1) {
        author_label = 'Авторы: ';
    }
    let date = new Date(Date.parse(topic.dateCreated));
    let card =
        '<div class="card mb-2">' +
            '<div class="card-header d-flex justify-content-between">' +
                '<a href="/topic/' + topic.id + '">' +
                    '<h5>' + topic.title + '</h5>' +
                '</a>' +
                // '<h6>' + date.getDay() + '.' + date.getMonth() + '.' + date.getFullYear() + '</h6>' +
                '<h6>' + timeConverter(date) + '</h6>' +
            '</div>' +
            '<div class="card-body">' +
                '<h6 class="card-title">' + author_label + authors + '</h6>' +
                '<h6 class="card-title">' + tags + '</h6>' +
                '<p class="card-text">' + topic.content + '</p>' +
            '</div>' +
        '</div>';
    return card;
}

