$(document).ready(function () {
    let admin_moderate_link = $('#admin_moderate_link');
    let moderate_cards = $('#moderate_cards');
    let page_number = 1;

    admin_moderate_link.addClass('active');
    getAndPrintNotModeratedTopicsPage(1, moderate_cards)
        .then();
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
    $(document).on('click', '.page-btn', function () {
        let new_page_number = this.id.replace("p_", '');
        if (new_page_number > page_number || new_page_number < page_number) {
            moderate_cards.empty();
            getAndPrintNotModeratedTopicsPage(new_page_number, moderate_cards)
                .then();
            $('#pi_' + page_number).removeClass('active');
            $('#pi_' + new_page_number).addClass('active');
            page_number = new_page_number;

        }
    });

    const delModal = $("#deleteModal");
    /**
     * обработчик кнопки delete
     */
    delModal.on('show.bs.modal', function (event) {
        let id = $(event.relatedTarget).data('id');
        $("#Dsubmit").on('click', function () {
            fetch('/api/admin/topic/delete/' + id, {
                method: 'DELETE',
            })
                .then(function () {
                    location.replace("http://localhost:5050/admin/moderate");
                })
        })
    });

    /**
     * обработчик кнопки moderate
     */

    const modModal = $("#moderateModal");

    modModal.on('show.bs.modal', function (event) {
        let id = $(event.relatedTarget).data('id');
        $("#Msubmit").on('click', function () {
            fetch('/api/admin/topic/moderate/' + id, {
                method: 'POST',
            })
                .then(function () {
                    location.replace("http://localhost:5050/admin/moderate");
                })
        })
    });


    const showModal = $("#showModal");
    /**
     * обработчик клика на заголовок
     */
    showModal.on('show.bs.modal', function (event) {
        let buttonId = $(event.relatedTarget).data('text');
        let body = $('#modalBody');
        let title = $('#modalTitle');
        fetch(`http://localhost:5050/api/admin/topic/` + buttonId)

            .then(result => result.json())
            .then(result => {
                title.append(result.title);
                body.append('<p>');
                let text = linkify(result.content);
                body.append(text);
                body.append('</p>');
            });
    });

    $('#closeModalButton').on('click', function () {
        $('#modalBody').empty();
        $('#modalTitle').empty();
    });

    // сокрытие элемента поиска по хэштэгам: он здесь не нужен
    let divFinderByHashtag = document.getElementById('finderByHashtag');
    divFinderByHashtag.style.visibility = 'hidden';

});