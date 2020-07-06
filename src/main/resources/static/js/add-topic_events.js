/**
 * Вызов модального окна
 */
$('#modal_add-topic_button').on('click', function() {
    $('#topic_title').val('');
    $('#topic_content').val('');
    $('#checkboxCompletedTopic').prop('checked', false);
    $('h5.modal-title').text('Создать статью');
    $('#topic_id').val(0);
});

/**
 * Вызов модального окна редактирования топика
 */
$(document).on('click', '.editTopicBtn', function() {
    let fullBtnId = this.id;
    let btnId = fullBtnId.replace('modal_edit-topic_button_', '');
    let title = $('#title_' + btnId).text();
    $('#topic_title').val(title);
    let text = $('#text_' + btnId).text();
    $('#topic_content').val(text);
    $('#topic_id').val(btnId);
    $('h5.modal-title').text('Редактировать');
    $('#checkboxCompletedTopic').prop('checked', false);
});

/**
 * Нажатие на кнопку добавления топика
 */
$('#add_topic_button').on('click', async function() {


    let title = $('#topic_title').val();
    let content = $('#topic_content').val();
    let completed = $('#checkboxCompletedTopic').prop('checked');
    let topic_id = $('#topic_id').val();
    let authors = $("#topic_coauthor").val();
    let alert_container = $('#alerts_container');
    if($("#topic_id").val() == 0)
    {
        await  AddTopic(title, content, completed, authors, alert_container);
    }
    else
    {
        await UpdateTopic(topic_id, title, content, completed, authors, alert_container);
    }
});
async function AddTopic(title, content, completed, authors, alert_container) {
    if (title === '' || content === '') {
        noValidForm(alert_container, 2000);
    } else {
        let response = addTopic(title, content, completed, authors);
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
}
async function UpdateTopic(topic_id, title, content, completed, authors, alert_container) {
    if (title === '' || content === '') {
        noValidForm(alert_container, 2000);
    } else {
        let response = updateTopic(topic_id, title, content, completed, authors);
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
}

$(document).ready(function () {
    $('.select2').select2({
        minimumInputLength: 3,
        ajax: {
            url: '/api/user/userslike',
            dataType: 'json',
            // Additional AJAX parameters go here; see the end of this chapter for the full code of this example
            processResults: function (data) {
                // Transforms the top-level key of the response object from 'items' to 'results'
                return {
                    results: data
                }
            }
        }
    });
});
$(".select2").on("select2:select", function (evt) {
    var element = evt.params.data.element;
    var $element = $(element);

    if ($(this).find(":selected").length > 1) {
        var $second = $(this).find(":selected").eq(-1);
        $second.after($element);
    } else {
        $element.detach();
        $(this).prepend($element);
    }

    $(this).trigger("change");
});