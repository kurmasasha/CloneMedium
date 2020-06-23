/**
 * Обработчики событий на странице all_topics_page.html (/allTopics)
 */

$(document).ready(function() {
    $('#all_page_link').addClass('active');
    getAndPrintModeratedTopics($('#topics_container'))
        .then();

    /**
     *  обновляем "колокольчик" - счётчик уведомлений
     */
    let notification_container = $('#notifications_counter');
    getNumberOfNotificationsOfUser(notification_container);
    setInterval(getNumberOfNotificationsOfUser, 5000, notification_container);

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
})