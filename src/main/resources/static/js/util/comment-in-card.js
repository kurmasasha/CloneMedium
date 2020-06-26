function commentInCard(comment) {

    let date = new Date(Date.parse(comment.dateCreated));
    let card =
        '<div class="card mb-2">' +
            '<div class="card-header d-flex justify-content-between">' +
        '<h6 class="card-title">' + comment.author.firstName + ' ' + comment.author.lastName + '</h6>' +
                '<h6>' + timeConverter(date) + '</h6>' +
            '</div>' +
            '<div class="card-body">' +
                '<p class="card-text">' + comment.text + '</p>' +
            '</div>' +
        '</div>';
    return card;
}

