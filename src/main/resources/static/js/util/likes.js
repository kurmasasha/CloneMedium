function increaseLike(id, addLike) {
    fetch(`http://localhost:5050/api/admin/topic/addLike/${id}`)
        .then(
            function(response) {
                if (response.status !== 200) {
                    alert('Вы уже лайкали этот топик!');
                    return;
                }

                response.json().then(function(data) {
                    addLike.empty().append(parseInt(data.toString()));
                });
            }
        )
        .catch(function(err) {
            console.log('Fetch Error :-S', err);
        });
}
