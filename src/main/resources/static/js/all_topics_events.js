/**
 * Обработчики событий на странице all_topics_page.html (/allTopics)
 */

/**
 * Нажатие на кнопку поиска по хэштегу
 */
$('#hashtag_all_button').on('click', function() {
    $.post('http://localhost:5050/api/user/topic/find-by-hashtag',
        {tag : $('#hashtag_input').val()})
        .done(function (data) {
            let container = $('#allTopics');
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