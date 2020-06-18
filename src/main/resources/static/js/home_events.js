/**
 * Обработчики событий на странице home.html (/home)
 */
$(document).ready(function(){
    $('#main_page_link').addClass('active');
    let uid = $('#userId').val();
    getAndPrintAllTopicsOfUser(uid, $('#topics_container'))
        .then(); // чтобы предупреждение не мазолило глаза, но по сути это лишнее, т.к. все-равно возвращается промис :)

    /**
     * Нажатие на кнопку поиска по хэштегу
     */
    $('#hashtag_home_button').on('click', function() {
        let hashtag = $('#hashtag_input').val();
        hashtag = hashtag.trim();
        if (hashtag.startsWith('#')) {
            hashtag = hashtag.slice(1);
        }
        if (hashtag !== '') {
            getAllTopicsByHashtag(uid, hashtag, $('#topics_container'))
                .then(); // чтобы предупреждение не мазолило глаза, но по сути это лишнее, т.к. все-равно возвращается промис :)
        } else {
            let contentContainer = $('#topics_container');
            contentContainer.empty();
            getAndPrintAllTopicsOfUser(uid, contentContainer)
                .then(); // чтобы предупреждение не мазолило глаза, но по сути это лишнее, т.к. все-равно возвращается промис :)
        }
    });
});