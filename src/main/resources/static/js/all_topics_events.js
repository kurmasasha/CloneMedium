/**
 * Обработчики событий на странице all_topics_page.html (/allTopics)
 */

$(document).ready(function() {
    let topicContainer = $('#topics_container');
    $('#all_page_link').addClass('active');
    getAndPrintModeratedTopics(topicContainer)
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
        console.log(hashtag);
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

    /**
     * Нажатие на кнопку поиска по теме
     */
    $('#theme_button').on('click', function () {
        fetch(`/api/free-user/getTopicsByThemes`, {
            method: 'post',
            headers: {
                "Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
            },
            body: $('#themes_form').serialize()
        })
            .then(result => result.json())
            .then(arrayTopics => {
                topicContainer.empty();
                arrayTopics.forEach(function (topic) {
                    let card = topicInCard(topic);
                    topicContainer.append(card);
                })
            })
    });

    // имитация нотификации
    window.onload = getNumberOfNotificationsOfUser($('#notif_counter'));
    setInterval( function () { getNumberOfNotificationsOfUser($('#notif_counter')).then(); }, 7000);

})

