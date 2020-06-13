let btnCreateTopic = document.getElementById("btnCreateTopic");
btnCreateTopic.onclick = function () {
    let topic = {
        title: document.getElementById('titleTopic'),
        content: document.getElementById('contentTopic')
    }
    addTopic(topic)
        .then(response => {
            console.log(response)
        })
        .then(result => {
            console.log(result)
        })
}


async function addTopic(topic) {
    console.log('start function createTopic')
    await fetch('/api/user/topic/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(topic)
    });
}

