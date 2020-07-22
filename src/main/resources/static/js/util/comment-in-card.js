function commentInCard(comment) {

    let card =
        '<div class="card mb-2">' +
            '<div class="card-header d-flex justify-content-between">' +
        '<h6 class="card-author-onComment">' + comment.author.firstName + ' ' + comment.author.lastName + '</h6>' +
                '<h6>' + comment.dateCreated + '</h6>' +
            '</div>' +
            '<div class="card-body">' +
                '<p class="card-text">' + comment.text + '</p>' +
            '</div>' +
        '</div>';
    return card;
}

