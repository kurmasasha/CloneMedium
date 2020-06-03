$(document).ready(function () {
    bsBlogTemplateTopics();
});

function bsBlogTemplateTopics() {

    $.ajax({

        url: '/api/user/allTopics/' + $("#userId").val(),
        method: "GET",
        dataType: "json",
        success: function (data) {

            var divBody = $('#bsBlogTemplate div');

            divBody.empty();

            $(data).each(function (i, topic) {

                divBody.append(`

                    <a href="/topic/${topic.id}">
                    
                    <h4 class="post-title"> ${topic.title} </h4>
                    
                    </a>
                    
                     <p class="post-meta">Posted by <a href="/admin/getUser/{id}">Author's Name</a> on ${topic.time} </p>                 
                    
                    
                    `);
            })
        },
        error: function (error) {
            alert(error);
        }
    })
}