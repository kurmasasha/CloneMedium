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
        authors += '<a href="#">' + author.firstName + " " + author.lastName + '</a>';
        if (index < (topic.authors.length - 1)) {
            authors += ' / ';
        }
    })
    let author_label = 'Автор: ';
    if (topic.authors.length > 1) {
        author_label = 'Авторы: ';
    }
    let date = new Date(topic.dateCreated);

    let moderated = 'notmoderated';
    if (topic.moderate) {
        moderated = 'moderated';
    }
    // TODO Зачем здесь переменная like, если она нигде далее не используется?
    let like = '<a class="text-info" id="likes" data-id= ' + topic.id + '>' + topic.likes + '</a>';
    let card =
        '<div class="card mb-2 mt-0">' +
        '<div class="card-header d-flex justify-content-between ' + moderated + '">' +
        '<a href="/topic/' + topic.id + '">' +
        '<h5>' + topic.title + '</h5>' +
        '</a>' +
        '<h6>' + topic.dateCreated + '</h6>' +
        '</div>' +
        '<div class="card-body">' +
        '<h6 class="card-title">' + author_label + authors + '</h6>' +
        '<h6 class="card-title">' + tags + '</h6>' +
        '<p class="card-text">' + linkify(topic.content) + '</p>' +
        `<i class="fa fa-thumbs-o-up" id="iconLikeOfTopic-${topic.id}"  data-id="${topic.id}"></i>` +
        `<span class="text-info" id="likeCounter" data-id="${topic.id}">${topic.likes}</span>` +
    '</div>' +
        '</div>';
    return card;
}

