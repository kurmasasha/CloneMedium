/**
 * Обработчики событий на странице home.html (/home)
 */

/**
 * Нажатие на кнопку поиска по хэштегу
 */
$('#hashtag_home_button').on('click', function() {
    $.post('http://localhost:5050/api/user/topic/find-by-uid-hashtag',
        {   uid : $(this).data('userId'),
            tag : $('#hashtag_input').val()})
        .done(function (data) {
            let container = $('#mainBlogContent');
            container.empty();
            container.append('<hr>');
            $.each(data, function (index, value) {
                let output = '<div class="post-preview"> <a href="/topic/' + value.id + '">'

                    + '<h4 class="post-title">' + value.title + '</h4> </a> '

                    + '<p class="post-meta">Posted by <a href="/admin/oneUser/' + value.authors[0].username + '">'

                    + value.authors[0].username + '</a>' + ' on ' + value.dateCreated + '</p>' + '</div> <hr>';
                container.append(output);
            })
        })
});