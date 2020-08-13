$(function() {

    $('.replybutton').on('click', function() {
        $('.replybox').hide();
        let commentboxId = $(this).attr('data-commentbox');
        $('#'+commentboxId).toggle();
    });

    $('.cancelbutton').on('click', function() {
        $('.replybox').hide();
    });

});