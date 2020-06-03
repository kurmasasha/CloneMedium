async function getTopicById(id) {
    let response = await fetch(`https://api.github.com/users/${id}`);
        if (response.ok) {
            let json = await response.json();
        } else {
            let json = {
                error: "error"
            }
        }
}
