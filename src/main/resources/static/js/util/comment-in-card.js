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
            '<div class="card-footer">' +
                '<span class="like">' +
                    '<i class="fa fa-thumbs-o-up" data-id="' + comment.id + '"></i>' +
                    '<span class="likes-num likeCount" id="likesId-'+ comment.id +'">' + comment.likes  + '</span>' +
                '</span>' +
                '<span class="dislike">' +
                    '<i class="fa fa-thumbs-o-down"  data-id="' + comment.id + '"></i>' +
                    '<span class="dislikes-num likeCount" id="dislikesId-'+ comment.id +'">' + comment.dislikes + '</span>' +
                '</span>' +
            '</div>' +
        '</div>';
    return card;
}

