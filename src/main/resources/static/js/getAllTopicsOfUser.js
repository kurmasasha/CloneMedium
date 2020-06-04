
let container = document.getElementById("topicsContainer");

let title = document.getElementById("mainBlogContent");

let userId = container.dataset.userId;

console.log(userId);

async function getAllTopicsOfUser(userId) {

    fetch(`http://localhost:5050/api/user/allTopics/${userId}`)

        .then(result => result.json())

        .then(arrayTopics => {

            arrayTopics.forEach(function (topic) {

              let output = '<div class="post-preview"> <a href="/topic/' + topic.id + '">'

                            + '<h4 class="post-title">' + topic.title + '</h4> </a> '

                            + '<p class="post-meta">Posted by <a href="/admin/oneUser/' + userId + '">'

                            + topic.authors[0].username + '</a>' + ' on ' + topic.time + '</p>' + '</div> <hr>'

              $('#mainBlogContent').append(output)

            })
        })
}

getAllTopicsOfUser(userId)


// Реализовать страницу домашнюю страницу /home + контроллер на которую попадает пользователь после логина.
//
// На странице доджен быть верхний навбар с вкладкой Главная и кнопкой Выход.
//
// В основном поле должны выводиться статьи которые принадлежат текущему пользователю.
//
// Отображать только заголовок Запрос на сервер через js и рест-контроллер. использовать Bootstrap 4.

// Добавить событие клика на каждую статью. При нажатии откраывается сама статься по урлу /topic/{id}