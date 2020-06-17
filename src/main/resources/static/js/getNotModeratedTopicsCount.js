/**
 * Вывод числа не модерированных топиков
 *
 */
async function getNotModeratedTopicsCount() {
    return fetch(`http://localhost:5050/api/admin/notModeratedTopicsCount`)
        .then(result => result.text());
}