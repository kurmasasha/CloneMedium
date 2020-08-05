
function userTopicInCard(topic) {
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
        '<div class="container-fluid">' +
            '<div class="row">' +
                '<div class="col-2 align-self-center">' +
                    '<a href="/topic/' + topic.id + '">' +
                        '<h5 class="card-title-onCard">' + topic.title + '</h5>' +
                    '</a>' +
                '</div>'+
                '<div class="col-10 align-self-end">' +
                    '<div class="row justify-content-end">' +
                        '<div class="col-4">' +
                            '<button type="button" class="btn btn-outline-dark mr-2" onclick="editTopicForm(' + topic.id + ')">Редактировать</button>' +
                            '<button type="button" class="btn btn-outline-danger" onclick="editTopicForm(' + topic.id + ')">Удалить</button>' +
                        '</div>'+

                        '<div class="col-0 align-self-center">' +
                            '<div id="datecreated">'+
                            '<h6{color: red}>' + topic.dateCreated + '</h6>' +
                            '</div>' +
                        '</div>'+
                    '</div>'+
                '</div>'+
            '</div>' +
        '</div>' +
        '</div>' +
        '<div class="card-body row">' +
        '<div class="col-md-4">' +
        `<img src="/topic-img/${topic.img}" class="card-img topic-img">` +
        '</div>' +
        '<div class="col-md-6">' +
        '<h6 class="card-author-onCard">' + author_label + authors + '</h6>' +
        '<h6 class="card-tag-onCard">' + tags + '</h6>' +
        '<p class="card-text-onCard">' + linkify(topic.content) + '</p>' +
        `<i class="fa fa-thumbs-o-up"></i>` +
        `<span id="likeCounter">${topic.likes}</span>` +
        `<i class="fa fa-thumbs-o-down"></i>` +
        `<span id="likeCounter">${topic.dislikes}</span>` +
        '</div>' +
        '</div>' +
        '</div>';
    return card;
}

