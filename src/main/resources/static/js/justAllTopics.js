
let title = document.getElementById("allTopics");


async function justAllTopics() {

    fetch(`http://localhost:5050/api/user/totalTopicsList/`)

        .then(result => result.json())

        .then(arrayTopics => {

            arrayTopics.forEach(function (topic) {

                let output = '<div class="post-preview"> <a href="/topic/' + topic.id + '">'

                    + '<h4 class="blog-post-title">' + topic.title + '</h4> </a> '

                    + '<p class="blog-post-meta">Posted by <a href="/admin/oneUser/' + topic.authors[0].username + '">'

                    + topic.authors[0].username + '</a>' + ' on ' + topic.dateCreated + '</p>' + '</div> <hr>'

                $('#allTopics').append(output)

            })
        })
}

justAllTopics()

