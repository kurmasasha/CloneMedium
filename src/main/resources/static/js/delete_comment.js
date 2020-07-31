$('.delete_comment_button').on('click', function( e) {
    const id = e.target.id;
    $('#commentCard-' + e.target.value).remove();
    $.post("/api/user/comment/delete/" + e.target.value);
});
