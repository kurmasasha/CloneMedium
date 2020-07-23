$(document).ready(function(){
    $('.menu a').filter(function(){
        return this.href==location.href}).parent().addClass('active').siblings().removeClass('active');

    $('.menu a').click(function(){
        $(this).parent().addClass('active').siblings().removeClass('active')
    });

    /**
     *  обновляется "колокольчик" - счётчик уведомлений
     */
    window.onload = getNumberOfNotificationsOfUser($('#notif_counter'));
    setInterval( function () { getNumberOfNotificationsOfUser($('#notif_counter')).then(); }, 5000);

    /**
     *  обновляется выпадающий список уведомлений
     */
    $('#dropdownMenuButton').on('click', function () {
        getAndPrintAllNotificationsOfUserDropList($('#dropdownNotifList')).then();
    })

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
            getTopicsOfUserByHashtag(hashtag, $('#topics_container'))
                .then();
        } else {
            let contentContainer = $('#topics_container');
            contentContainer.empty();
            getAndPrintAllTopicsOfUser(contentContainer)
                .then();
        }
    });
});