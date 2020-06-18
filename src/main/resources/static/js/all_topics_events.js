/**
 * Обработчики событий на странице all_topics_page.html (/allTopics)
 */

$(document).ready(function() {
    $('#all_page_link').addClass('active');
    getAndPrintAllTopics($('#topics_container'))
        .then(); // чтобы предупреждение не мазолило глаза, но по сути это лишнее, т.к. все-равно возвращается промис :)

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
                .then(); // чтобы предупреждение не мазолило глаза, но по сути это лишнее, т.к. все-равно возвращается промис :)
        } else {
            let contentContainer = $('#topics_container');
            contentContainer.empty();
            getAndPrintAllTopics(contentContainer)
                .then(); // чтобы предупреждение не мазолило глаза, но по сути это лишнее, т.к. все-равно возвращается промис :)
        }
    });

    /**
     * Нажатие на кнопку добавления топика
     */
    $('#add_topic_button').on('click', async function() {
        let title = $('#topic_title').val();
        let content = $('#topic_content').val();
        let alert_container = $('#alerts_container');
        if (title === '' || content === '') {
            noValidForm(alert_container, 2000);
        } else {
            let response = addTopic(title, content);
            if ((await response).ok) {
                response
                    .then(result => result.json())
                    .then(topic => {
                        let card = topicInCard(topic);
                        successAddTopic(alert_container, 2000)
                        $('#topics_container').prepend(card);
                    })
            } else {
                failAddTopic(alert_container, 2000)
            }
        }
    });

})