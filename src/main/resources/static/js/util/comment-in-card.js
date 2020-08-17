function commentInCard(comment) {

    let card =
        '<div class="card mb-2" id="commentCard-' + comment.id + '">' +
        '<div class="card-header row">' +
        '<h6 class="card-comment-author col">' + comment.author.firstName + ' ' + comment.author.lastName + '</h6>' +
        '<div class="datecreated row">' +
        '<h6>' + comment.dateCreated + '</h6>' +
        '</div>' +
        '<div class="dropdown col">' +
        '<i class="fa fa-ellipsis-v pl-5 float-right" aria-hidden="true" type="button" id="dropdownMenuButton"' +
        '   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></i>' +
        '<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">' +
        '<button class="dropdown-item edit-comment" type="button" data-toggle="modal" data-target="#editCommentModal"' +
        '        data-id="' + comment.id + '">Редактировать</button>' +
        '<button class="dropdown-item delete-comment" type="button" data-toggle="modal" data-target="#deleteCommentModal"' +
        '        data-id="' + comment.id + '">Удалить</button>' +
        '</div>' +
        '</div>' +
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
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';
    return card;
}
