$(document).ready(function () {
    getTopic();
});

function getTopic() {
    $.ajax({
        url: "/user/topic/{id}" + $("#userId").val(),
        dataType: "json",
        success: function (topic) {
            $('#topic').html(
                `<h3>${topic.title}</h3>
                 <p>${topic.content}</p>`
            )
        },
        error(e) {
            console.log(e)
        }
    })
}