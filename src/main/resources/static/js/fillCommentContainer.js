const commentApiUrl = '/api/free-user/allCommentsOfTopic/'

let templateComment = document.getElementById('template-comment').content;
let commentContainer = document.getElementById('comments_container');

let commentCard = templateComment.querySelector('.comment-card')
let commentAuthor = templateComment.querySelector('.card-comment-author')
//let dropDown = templateComment.querySelector('.dropdown') todo установить авторизованный доступ
let editCommentButton = templateComment.querySelector('.edit-comment')
let deleteCommentButton = templateComment.querySelector('.delete-comment')
let dataCreated = templateComment.querySelector('.data-created')
let textComment = templateComment.querySelector('.card-text')
//let like todo check (th:classappend="${#lists.contains(comment.likedUsers, user)} ? 'rated' : ''")
//let dislike todo check (th:classappend="${#lists.contains(comment.dislikedUsers, user)} ? 'rated' : ''")
let likeButton = templateComment.querySelector('.like-button');
let dislikeButton = templateComment.querySelector('.dislike-button');
let likeNum = templateComment.querySelector('.likes-num');
let dislikeNum = templateComment.querySelector('.dislikes-num');
let responseButton = templateComment.querySelector('.response-button');
let replyPanel = templateComment.querySelector('.reply-panel');
let addReplyButton = templateComment.querySelector('#addReplyButton');

let res = [];
let json = [];

$(fillCommentContainer())

async function fillCommentContainer() {
    commentContainer.innerHTML = '';
    res = [];

    let url = document.location.href.split('/');
    let topicNum = url[url.length - 1];

    json = await fetch(commentApiUrl + topicNum).then((res) => {
        return res.json()
    })

    commentTree();

    res.map((comment) => {
        printComment(comment)
    })
}

async function printComment(comment) {
    commentCard.setAttribute('style', "margin-left: " + comment.deeper + "px")
    commentCard.setAttribute('id', 'commentCard-' + comment.id);
    commentAuthor.textContent = comment.author.firstName + ' ' + comment.author.lastName;
    editCommentButton.setAttribute('data-id', comment.id);
    deleteCommentButton.setAttribute('data-id', comment.id);
    dataCreated.textContent = comment.dateCreated;
    textComment.setAttribute('id', 'commentCardText-' + comment.id);
    textComment.textContent = comment.text;
    likeButton.setAttribute('data-id', comment.id);
    dislikeButton.setAttribute('data-id', comment.id);
    likeNum.setAttribute('id', 'likesId-' + comment.id)
    likeNum.textContent = comment.likes;
    dislikeNum.setAttribute('id', 'dislikesId-' + comment.id);
    dislikeNum.textContent = comment.dislikes;
    responseButton.setAttribute('data-panelId', 'panel' + comment.id);
    replyPanel.setAttribute('id', 'panel' + comment.id);
    addReplyButton.setAttribute('data-id', comment.id);
    addReplyButton.setAttribute('data-panelId', 'panel' + comment.id);

    let clone = document.importNode(templateComment, true)
    commentContainer.appendChild(clone)
}

function commentTree() {
    json.forEach((comment) => {
        if(comment.isMainComment && (res.indexOf(comment)== -1)){
            res.push(comment);
            findChild(comment);
        }
    })
}

function findChild(parentComment) {
    json.forEach((comment)=>{
        if((comment.mainCommentId == parentComment.id) && (res.indexOf(comment)== -1)){
            res.push(comment);
            findChild(comment);
            commentTree();
        }
    })
}