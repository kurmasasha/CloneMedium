/**
 * Обработчики событий на странице __home.html (/home)
 */

/**
 * Нажатие на кнопку поиска по хэштегу
 */
$('#hashtag_home_button').on('click', function() {
    let uid = $(this).data('userId');
    let hashtag = $('#hashtag_input').val();
    hashtag = hashtag.trim();
    if (hashtag.startsWith('#')) {
        hashtag = hashtag.slice(1);
    }
    if (hashtag !== '') {
        getAllTopicsByHashtag(uid, hashtag, $('#mainBlogContent'));
    } else {
        let contentContainer = $('#mainBlogContent');
        contentContainer.empty();
        contentContainer.append('<hr>');
        getAllTopicsOfUser(uid);
    }
});