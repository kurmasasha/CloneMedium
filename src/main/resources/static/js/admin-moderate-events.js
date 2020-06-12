$(document).ready(function(){
    let admin_moderate_link = $('#admin_moderate_link');
    let moderate_cards = $('#moderate_cards');
    let page_number = 1;

    admin_moderate_link.addClass('active');
    getAndPrintNotModeratedTopicsPage(1, moderate_cards)
        .then(); // чтобы предупреждение не мазолило глаза, но по сути это лишнее, т.к. все-равно возвращается промис :)
    //размер страницы жестко задан
    let pageSize = 5;
    getNotModeratedTopicsCount().then(count => {
        let page_buttons = $('#page_buttons');
        page_buttons.empty();
        page_buttons.append('<li id="pi_1" class="page-item active">' +
                                '<button id="p_1" class="page-link page-btn" type="button">1</button>' +
                            '</li>');

        for (let i = 2; i <= Math.ceil(count/pageSize); i++) {
            page_buttons.append('<li id="pi_' + i + '" class="page-item">' +
                                    '<button id="p_' + i + '" class="page-link page-btn" type="button">' + i + '</button>' +
                                '</li>');
        }
    });
    /**
     * обработчик нажатия на кнопки навигации по страницам
     */
    $(document).on('click', '.page-btn', function() {
        let new_page_number = this.id.replace("p_", '');
        if (new_page_number > page_number || new_page_number < page_number) {
            moderate_cards.empty();
            getAndPrintNotModeratedTopicsPage(new_page_number, moderate_cards)
                .then(); // чтобы предупреждение не мазолило глаза, но по сути это лишнее, т.к. все-равно возвращается промис :)
            $('#pi_' + page_number).removeClass('active');
            $('#pi_' + new_page_number).addClass('active');
            page_number = new_page_number;

        }
    });
});