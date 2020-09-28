$(document).ready(function () {
    let admin_moderate_link = $('#admin_moderate_link');
    let moderate_cards = $('#moderate_cards');
    let page_number = 1;

    admin_moderate_link.addClass('active_side_bar');
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
            $('#pi_' + page_number).removeClass('active_side_bar');
            $('#pi_' + new_page_number).addClass('active_side_bar');
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
        let title;
        let text;
        let authorList = "";
        fetch(`/api/admin/topic/` + id)
            .then(result => result.json())
            .then(result => {
                title = linkify(result.title);
                text = linkify(result.content);
                result.authors.forEach(function (author) {
                    authorList += author.username + " ";
                    }
                )
            });

        $("#Msubmit").on('click', function () {
            let data = {'text':'*New topic!* _by ' + authorList + '_\n*' +
                    title +
                    '*\n_' +
                    text + '_'
                ,'chat_id':'-1001380896859'
                ,'parse_mode' : 'Markdown'}
            fetch('https://api.telegram.org/bot1279649981:AAHHMXa2s8INrfmsh-TG-MmYbFJ1coHZaPA/sendMessage', {
                method: 'POST',
                headers: {'Content-Type': 'application/json;charset=utf-8'},
                body: JSON.stringify(data)
            })
                .then(response => console.log(JSON.stringify(response)));

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
        fetch(`/api/admin/topic/` + buttonId)

            .then(result => result.json())
            .then(result => {
                title.append(result.title);
                body.append('<p>');
                let text = result.content;
                body.append(text);
                body.append('</p>');
            });
    });

    $('#closeModalButton').on('click', function () {
        $('#modalBody').empty();
        $('#modalTitle').empty();
    });

    // имитация нотификации
    $(document).ready(bellCount());
    $(document).ready(getAllNotifications());

    // удаление формы поиска по хэштегу
    $('#finderByHashtag').detach();
});