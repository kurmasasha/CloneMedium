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

    let card =
        '<div class="card mb-2 mt-0">' +
        '<div class="card-header d-flex justify-content-between ' + moderated + '">' +
        '<a href="/topic/' + topic.id + '">' +
        '<h5 id="title_'+ topic.id +'">' + topic.title + '</h5>' +
        '</a>' +
        '<h6>' + timeConverter(date) + '</h6>' +
        '<button id="modal_edit-topic_button_' + topic.id + '"class="btn btn-success btn-sm rounded-1 editTopicBtn" type="button" data-topic-id="' + topic.id + '" data-toggle="modal" data-target="#modalWindowCreateTopic"  data-placement="top" title="Edit"><i class="fa fa-edit text-white"></i></button>' +
        '</div>' +
        '<div class="card-body row">' +
        '<div class="col-md-4">' +
        `<img src="/topic-img/${topic.img}" class="card-img topic-img">` +
        '</div>' +
        '<div class="col-md-6">' +
        '<h6 class="card-title">' + author_label + authors + '</h6>' +
        '<h6 class="card-title">' + tags + '</h6>' +
        '<p class="card-text" id="text_'+ topic.id +'">' + linkify(topic.content) + '</p>' +
        `<i class="fa fa-thumbs-o-up" id="iconLikeOfTopic-${topic.id}"  data-id="${topic.id}"></i>` +
        `<span class="text-info" id="likeCounter" data-id="${topic.id}">${topic.likes}</span>` +
        '</div>' +
        '</div>' +
        '</div>';
    return card;
}

