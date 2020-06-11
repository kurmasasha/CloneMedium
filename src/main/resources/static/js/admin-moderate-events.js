$(document).ready(function(){
    $('#admin_miderate_link').addClass('active');
    getAndPrintNotModeratedTopicsPage(1, $('#moderate_cards'));
    //размер страницы жестко задан
    let pageSize = 5;
    getNotModeratedTopicsCount().then(count => {
        let page_buttons = $('#page_buttons');
        page_buttons.empty();
        page_buttons.append('<li class="page-item disabled">' +
                                '<button class="page-link" type="button" tabindex="-1" aria-disabled="true">Previous</button>' +
                            '</li>' +
                            '<li class="page-item active">' +
                                '<button class="page-link" type="button">1</button>' +
                            '</li>');

        for (let i = 2; i <= Math.ceil(count/pageSize); i++) {
            page_buttons.append('<li class="page-item">' +
                                    '<button class="page-link" type="button">' + i + '</button>' +
                                '</li>');
        }
        page_buttons.append('<li class="page-item">' +
                                '<button class="page-link" type="button">Next</button>' +
                            '</li>');
    });
});