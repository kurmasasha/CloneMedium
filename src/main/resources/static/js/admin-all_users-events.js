$(document).ready(function(){
    $('#admin_users_link').addClass('active');
    getAndPrintAllUsers($('#users_table'))
        .then();


    // имитация нотификации
    window.onload = getNumberOfNotificationsOfUser($('#notif_counter'));
    setInterval( function () { getNumberOfNotificationsOfUser($('#notif_counter')).then(); }, 7000);

    // сокрытие элемента поиска по хэштэгам из шапки: он здесь не нужен
    let divFinderByHashtag = document.getElementById('finderByHashtag');
    divFinderByHashtag.style.visibility = 'hidden';


});