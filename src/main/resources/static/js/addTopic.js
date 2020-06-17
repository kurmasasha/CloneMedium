const btnCreateTopic = document.getElementById("btnCreateTopic")
const btnCloseFormCreateTopic = document.getElementById('btnCloseFormCreateTopic')
const form = document.forms.namedItem('formAddTopic')
const inputFormTitle = form.elements.namedItem('title')
const inputFormContent = form.elements.namedItem('content')


// при закрытии модального окна, сброс заполненных полей
btnCloseFormCreateTopic.onclick = function() {
    console.log()
    inputFormTitle.value = ''
    inputFormContent.value = ''
}

btnCreateTopic.onclick = async function () {
    console.log("click btnCreateTopic")
    let topic = {
        title: inputFormTitle.value,
        content: inputFormContent.value,
    }

    console.log(topic)

    let container = document.getElementById('containerAlertsCreatedTopic')

    await fetch('/api/user/topic/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(topic)
    })
        .then(response => {
            console.log(response)
            if (response.ok) {
                successAddTopic(container, 2000)
            } else {
                noValidForm(container, 3000)
            }
        })
        .catch(error => {
            failAddTopic(container, 2000)
        })
}

function successAddTopic(elem, time) {
    elem.innerHTML = '<div class="alert alert-success m-3" role="alert">Ваша статья, успешно добавлена</div>'
    btnCreateTopic.setAttribute('disabled', 'disabled')
    setTimeout(function () {
        elem.innerHTML = ''
        btnCreateTopic.removeAttribute('disabled')
        btnCloseFormCreateTopic.click()
    }, time)
}

function noValidForm(elem, time) {
    elem.innerHTML = '<div class="alert alert-danger m-3" role="alert">Поля не должны быть пустыми</div>'
    setTimeout(function () {
        elem.innerHTML = ''
    }, time)
}

function failAddTopic(elem, time) {
    elem.innerHTML = '<div class="alert alert-danger m-3" role="alert">Что то пошло не так! Попробуйте снова</div>'
    setTimeout(function () {
        elem.innerHTML = ''
    }, time)
}


