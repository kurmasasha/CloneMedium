$(document).ready(function () {

    //уведомления
    $(document).ready(bellCount());
    $(document).ready(getAllNotifications());

    //заполнение таблицы комментов
    $(document).ready(getTopComments());


    // удаление формы поиска по хэштегу
    $('#finderByHashtag').detach();

    function getTopComments() {
        $('#comment-table').empty();
        fetch(`/api/admin/top/comment`)
            .then(responce => responce.json())
            .then(result => {
                console.log(result)
                result.forEach(comment =>
                    $('#comment-table').append($('<tr>').append(
                        $('<td>').append($('<span>')).text(comment.id),
                        $('<td>').append($('<span>')).text(comment.author.firstName),
                        $('<td>').append($('<span>')).text(comment.text).attr({
                            "class": "comment-item info"
                        }),
                        $('<td>').append($('<span>')).text(comment.likes)
                    ))
                )
                let textElem = $('.comment-item')
                for(let i = 0; i < textElem.length; i++) {
                    if (textElem[i].innerText.length > 20) {
                        textElem[i].innerText = textElem[i].innerText.slice(0, 20) + "...";
                    }
                }
            })

    }
})
