/**
 * Вывод числа не модерированных топиков
 *
 */
async function getNotModeratedTopicsCount() {
    return fetch(`/api/admin/notModeratedTopicsCount`)
        .then(result => result.text());
}