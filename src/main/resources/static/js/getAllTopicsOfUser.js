$(document).ready(function () {
    getAllTopicsOfUser();
});

function getAllTopicsOfUser() {
    $.ajax({

        url: '/api/user/allTopics/' + $("#userId").val(),
        method: "GET",
        dataType: "json",
        success: function (data) {
            var tableBody = $('#tableAllTopicsOfUser tbody');
            tableBody.empty();
            $(data).each(function (i, topic) {
                let ts = new Date().getTime();

                tableBody.append(`
                    <tr> 
                    
                    <td>${topic.id}</td>
                    
                    <td> <span> ${topic.title} </span> </td>                  
                                        
                    <td>${topic.time}</td>
                     
                    </tr>`);
            })
        },
        error: function (error) {
            alert(error);
        }
    })
}

//  onclick="getContentOfTopic(${topic.id})"
//
// <td>${stringAuthors}</td>


// Реализовать страницу домашнюю страницу /home + контроллер на которую попадает пользователь после логина.
//
// На странице доджен быть верхний навбар с вкладкой Главная и кнопкой Выход.
//
// В основном поле должны выводиться статьи которые принадлежат текущему пользователю.
//
// Отображать только заголовок Запрос на сервер через js и рест-контроллер. использовать Bootstrap 4.

// Добавить событие клика на каждую статью. При нажатии откраывается сама статься по урлу /topic/{id}