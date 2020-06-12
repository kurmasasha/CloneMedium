$(document).ready(function(){
    $('#admin_users_link').addClass('active');
    getAndPrintAllUsers($('#users_table'))
        .then(); // чтобы предупреждение не мазолило глаза, но по сути это лишнее, т.к. все-равно возвращается промис :)
});