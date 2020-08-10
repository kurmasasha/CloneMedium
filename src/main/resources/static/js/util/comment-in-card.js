function commentInCard(comment) {

    let card =
        '<div class="card mb-2" id="commentCard-' + comment.id + '">' +
        '<div class="card-header d-flex justify-content-between">' +
        '<h6 class="card-author-onComment">' + comment.author.firstName + ' ' + comment.author.lastName + '</h6>' +
        '<h6>' + comment.dateCreated + '</h6>' +
        '</div>' +
        '<div class="card-body">' +
        '<p class="card-text" id="commentCardText-' + comment.id + '">' + comment.text + '</p>' +
        '</div>' +
        '<div class="card-footer">' +
        '<div class="d-flex bd-highlight mb-2">' +
        '<div class="mr-auto p-2 bd-highlight">' +
        '<span class="like">' +
        '<i class="fa fa-thumbs-o-up" data-id="' + comment.id + '"></i>' +
        '<span class="likes-num likeCount" id="likesId-' + comment.id + '">' + comment.likes + '</span>' +
        '</span>' +
        '<span class="dislike">' +
        '<i class="fa fa-thumbs-o-down"  data-id="' + comment.id + '"></i>' +
        '<span class="dislikes-num likeCount" id="dislikesId-' + comment.id + '">' + comment.dislikes + '</span>' +
        '</span>' +
        '<span>' +
        '<i class="fa fa-pencil-square-o" data-toggle="modal" data-target="#editCommentModal" ' +
        'data-id="' + comment.id + '"></i>' +
        '</span>' +
        '</div>' +
        '<div class="delete_comment_button">' +
        '<div class="p-2 bd-highlight">' +
        '<a  class="fa fa-trash" data-id="' + comment.id + '"></a>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';
    return card;
}
