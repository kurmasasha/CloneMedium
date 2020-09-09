const commentApiUrl = '/api/free-user/allCommentsOfTopic/'

$(async function () {
    let url = document.location.href;
    let array = url.split('/');
    let topicNum = array[array.length-1];

    let json = await fetch(commentApiUrl + topicNum).then((res) => {
        return res.json()
    })
    alert(json[0].text)
})