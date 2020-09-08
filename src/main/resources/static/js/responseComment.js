$('#replyButton').on('click', function () {
    $('#' + $(this).attr('data-commentbox')).toggle();
});

$('#add_comment_answer').on('click', function () {
    $('#' + $(this).attr('data-commentbox')).toggle();
});
