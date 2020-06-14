let btnCreateTopic = document.getElementById("btnCreateTopic")
let form = document.forms.namedItem('formAddTopic')


btnCreateTopic.onclick = async function () {
    console.log("click btnCreateTopic")
    let topic = {
        title: form.elements.namedItem('title').value,
        content: form.elements.namedItem('content').value,
    }

    console.log(topic)

    await fetch('/api/user/topic/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(topic)
    }).then(response => {
            if (response.ok) {
                alert("ваша статья успешно добавлена")
                //$('#modalWindowCreateTopic').modal('hide')
            } else {
                alert("что то пошло не так, попробуйте заново")
            }
        })
}


