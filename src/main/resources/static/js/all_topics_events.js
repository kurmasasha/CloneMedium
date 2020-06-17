/**
 * Обработчики событий на странице all_topics_page.html (/allTopics)
 */

/**
 * Нажатие на кнопку поиска по хэштегу
 */
$('#hashtag_all_button').on('click', function() {
    let hashtag = $('#hashtag_input').val();
    hashtag = hashtag.trim();
    if (hashtag.startsWith('#')) {
        hashtag = hashtag.slice(1);
    }
    if (hashtag !== '') {
        getAllTopicsByHashtag('all', hashtag, $('#allTopics'))
            .then(); // чтобы предупреждение не мазолило глаза, но по сути это лишнее, т.к. все-равно возвращается промис :)
    } else {
        let contentContainer = $('#mainBlogContent');
        contentContainer.empty();
        contentContainer.append('<hr>');
        justAllTopics()
            .then(); // чтобы предупреждение не мазолило глаза, но по сути это лишнее, т.к. все-равно возвращается промис :)
    }
});