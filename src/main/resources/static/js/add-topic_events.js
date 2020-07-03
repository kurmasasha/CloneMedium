/**
 * Вызов модального окна
 */
$('#modal_add-topic_button').on('click', function() {
    $('#topic_title').val('');
    $('#topic_content').val('');
    $('#checkboxCompletedTopic').prop('checked', false);
});

/**
 * Нажатие на кнопку добавления топика
 */
$('#add_topic_button').on('click', async function() {
    let title = $('#topic_title').val();
    let content = $('#topic_content').val();
    let completed = $('#checkboxCompletedTopic').prop('checked');

    console.log(completed)

    let alert_container = $('#alerts_container');
    if (title === '' || content === '') {
        noValidForm(alert_container, 2000);
    } else {
        let response = addTopic(title, content, completed);
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