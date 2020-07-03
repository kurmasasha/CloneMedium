$(document).ready(function(){
    $('#admin_users_link').addClass('active');
    getAndPrintAllUsers($('#users_table'))
        .then();

    // имитация нотификации
    window.onload = getNumberOfNotificationsOfUser($('#notif_counter'));
    setInterval( function () { getNumberOfNotificationsOfUser($('#notif_counter')).then(); }, 7000);

    // удаление формы поиска по хэштегу
    $('#finderByHashtag').detach();
});