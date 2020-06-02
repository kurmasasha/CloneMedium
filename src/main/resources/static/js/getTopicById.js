let topicId = $('#topicId').data('topicId')
getTopicById(topicId)

function getTopicById(id) {
    $.ajax({
        url: `http://localhost:5050/api/user/topic/${id}`,
        success: function (topic) {
            $('#topicAuthor').text(`Author - ${topic.authors[0].firstName}  ${topic.authors[0].lastName}`)
            $('#topicTitle').text(topic.title)
            $('#topicContent').text(topic.content)
            console.log(topic)
        },
        error: function (error) {
            console.log(error)
        }
    })
}


