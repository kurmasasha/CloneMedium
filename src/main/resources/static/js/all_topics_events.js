/**
 * Обработчики событий на странице all_topics_page.html (/allTopics)
 */

$(document).ready(function() {
    $('#all_page_link').addClass('active');
    getAndPrintAllTopics($('#topics_container'))
        .then();

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
            getAllTopicsByHashtag('all', hashtag, $('#topics_container'))
                .then();
        } else {
            let contentContainer = $('#topics_container');
            contentContainer.empty();
            getAndPrintAllTopics(contentContainer)
                .then();
        }
    });
})