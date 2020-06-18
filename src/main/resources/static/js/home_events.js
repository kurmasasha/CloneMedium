/**
 * Обработчики событий на странице home.html (/home)
 */
$(document).ready(function(){
    $('#main_page_link').addClass('active');
    let uid = $('#userId').val();
    getAndPrintAllTopicsOfUser(uid, $('#topics_container'))
        .then(); // чтобы предупреждение не мазолило глаза, но по сути это лишнее, т.к. все-равно возвращается промис :)

    /**
     * Нажатие на кнопку поиска по хэштегу
     */
    $('#hashtag_home_button').on('click', function() {
        let hashtag = $('#hashtag_input').val();
        hashtag = hashtag.trim();
        if (hashtag.startsWith('#')) {
            hashtag = hashtag.slice(1);
        }
        if (hashtag !== '') {
            getAllTopicsByHashtag(uid, hashtag, $('#topics_container'))
                .then(); // чтобы предупреждение не мазолило глаза, но по сути это лишнее, т.к. все-равно возвращается промис :)
        } else {
            let contentContainer = $('#topics_container');
            contentContainer.empty();
            getAndPrintAllTopicsOfUser(uid, contentContainer)
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
                        let tags = '';
                        $.each(topic.hashtags, function (index, tag) {
                            tags += '<a href="#">' + tag.name + '</a>';
                            if (index < (topic.hashtags.length - 1)) {
                                tags += ' / ';
                            }
                        })
                        let authors = '';
                        $.each(topic.authors, function (index, author) {
                            authors += '<a href="#">' + author.username + '</a>';
                            if (index < (topic.authors.length - 1)) {
                                authors += ' / ';
                            }
                        })
                        let author_label = 'Автор: ';
                        if (topic.authors.length > 1) {
                            author_label = 'Авторы: ';
                        }
                        let date = new Date(Date.parse(topic.dateCreated));
                        let card =
                            '<div class="card mb-2">' +
                                '<div class="card-header d-flex justify-content-between">' +
                                    '<a href="/topic/' + topic.id + '">' +
                                        '<h5>' + topic.title + '</h5>' +
                                    '</a>' +
                                    '<h6>' + date.getDay() + '.' + date.getMonth() + '.' + date.getFullYear() + '</h6>' +
                                '</div>' +
                                '<div class="card-body">' +
                                    '<h6 class="card-title">' + author_label + authors + '</h6>' +
                                    '<h6 class="card-title">' + tags + '</h6>' +
                                    '<p class="card-text">' + topic.content + '</p>' +
                                '</div>' +
                            '</div>';
                        successAddTopic($('#alerts_container'), 2000)
                        $('#topics_container').prepend(card);
                    })
            } else {
                failAddTopic($('#alerts_container'), 2000)
            }
        }
    });
});