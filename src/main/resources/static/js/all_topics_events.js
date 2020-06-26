/**
 * Обработчики событий на странице all_topics_page.html (/allTopics)
 */

$(document).ready(function() {
    let topicContainer = $('#topics_container');
    $('#all_page_link').addClass('active');
    getAndPrintModeratedTopics(topicContainer)
        .then();

    /**
     * Нажатие на кнопку увеличения лайка
     */
    topicContainer.delegate('.fa-thumbs-o-up', 'click', function () {
        let id = $(this).attr('data-id');
        let addLike = $(this).siblings(".text-info");
        increaseLike(id, addLike);

    });

    /**
     * Нажатие на кнопку поиска по хэштегу
     */
    $('#hashtag_home_button').on('click', function () {
        let hashtag = $('#hashtag_input').val();
        hashtag = hashtag.trim();
        if (hashtag.startsWith('#')) {
            hashtag = hashtag.slice(1);
        }
        if (hashtag !== '') {
            getAllTopicsByHashtag(hashtag, $('#topics_container'))
                .then();
        } else {
            let contentContainer = $('#topics_container');
            contentContainer.empty();
            getAndPrintModeratedTopics(contentContainer)
                .then();
        }
    });
});