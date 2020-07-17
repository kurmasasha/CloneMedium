
function topicInCard(topic) {
    let tags = '';
    $.each(topic.hashtags, function (index, tag) {
        tags += '<a  href ="/topic/find/tag/'+ tag.name + '"> '+ tag.name +' </a>';
        if (index < (topic.hashtags.length - 1)) {
            tags += ' / ';
        }
    })
    let authors = '';
    $.each(topic.authors, function (index, author) {
        authors += '<a href="/topic/find/author/' + author.id + '">' + author.firstName + " " + author.lastName + '</a>';
        if (index < (topic.authors.length - 1)) {
            authors += ' / ';
        }
    })
    let author_label = 'Автор: ';
    if (topic.authors.length > 1) {
        author_label = 'Авторы: ';
    }

    let moderated = 'notmoderated';
    if (topic.moderate) {
        moderated = 'moderated';
    }

    let card =
        '<div class="card mb-2 mt-0">' +
        '<div class="card-header d-flex justify-content-between ' + moderated + '">' +
        '<a href="/topic/' + topic.id + '">' +
        '<h5>' + topic.title + '</h5>' +
        '</a>' +
        '<div id="datecreated">'+
        '<h6{color: red}>' + topic.dateCreated + '</h6>' +
        '</div>' +
        '</div>' +
        '<div class="card-body row">' +
        '<div class="col-md-4">' +
        `<img src="/topic-img/${topic.img}" class="card-img topic-img">` +
        '</div>' +
        '<div class="col-md-6">' +
        '<h6 class="card-title">' + author_label + authors + '</h6>' +
        '<h6 class="card-title">' + tags + '</h6>' +
        '<p class="card-text">' + linkify(topic.content) + '</p>' +
        `<i class="fa fa-thumbs-o-up" id="iconLikeOfTopic-${topic.id}"  data-id="${topic.id}"></i>` +
        `<span class="text-info" id="likeCounter" data-id="${topic.id}">${topic.likes}</span>` +
        '</div>' +
        '</div>' +
        '</div>';
    return card;
}

