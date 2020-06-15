
//let title = document.getElementById("mainBlogContent");


async function justAllTopics() {

    fetch(`http://localhost:5050/api/user/totalModeratedTopicsList/`)

        .then(result => result.json())

        .then(arrayTopics => {

            arrayTopics.forEach(function (topic) {

                let output = '<div class="post-preview"> <a href="/topic/' + topic.id + '">'

                    + '<h4 class="blog-post-title">' + topic.id + ' ' + topic.title + '</h4> </a> '

                    + '<p class="blog-post-meta">Posted by <a href="/admin/oneUser/' + topic.authors[0].username + '">'

                    + topic.authors[0].username + '</a>' + ' on ' + topic.dateCreated + '</p>' + '</div> <hr>'

                $('#mainBlogContent').append(output)

            })
        })
}

justAllTopics()

