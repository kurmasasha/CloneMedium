$(document).ready(function(){
    $('#admin_users_link').addClass('active');
    getAndPrintAllUsers($('#users_table'))
        .then();

    // сокрытие элемента поиска по хэштэгам из шапки: он здесь не нужен
    let divFinderByHashtag = document.getElementById('finderByHashtag');
    divFinderByHashtag.style.visibility = 'hidden';

});