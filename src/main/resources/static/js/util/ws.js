/**
 * Open the web socket connection and subscribe the "/notify" channel.
 */
let socket = new SockJS('/ws');
    let stompClient = Stomp.over(socket);
    let con = $('#notif_counter');
    let counter = 0;
    function container(container){
    con = container;
    }
    function bell(con, counter){
        if(counter > 0){
            con.empty();
            con.append(counter);
        }
    }
    stompClient.connect({}, function (frame) {
        fetch(`/api/user/MyNotifsNbr`)
            .then(response => response.json())
            .then(result => {
                result.forEach(element =>
                    stompClient.send('/user/queue/notify', {}, JSON.stringify(element)))
            })
    stompClient.subscribe('/user/queue/notify', function (notification) {
        // Call the notify function when receive a notification
        let data = JSON.parse(notification.body);
        if (notification) {
            bell(con,counter+=1);
            if(data.readBy === false){
                Toast.add({
                    header: data.title,
                    text: data.text,
                    color: '#0d7271',
                    autohide: true
                });
                let currentNotification = {
                    "id": data.id,
                    "title": data.title,
                    "text": data.text,
                    "readBy": true,
                }
                $.ajax({
                    url: "/api/user/notification/update",
                    type: "POST",
                    headers: {
                        "Content-Type" : "application/json"
                    },
                    data: JSON.stringify(currentNotification)
                });
            }
        }
    });
});
// код JavaScript
// функция-конструктор Toast (для создания объектов Toast)
var Toast = function (element, config) {
// приватные переменные класса Toast
    var
        _this = this,
        _element = element,
        _config = {
            autohide: true,
            delay: 5000
        };
// установление _config
    for (var prop in config) {
        _config[prop] = config[prop];
    }
// get-свойство element
    Object.defineProperty(this, 'element', {
        get: function () {
            return _element;
        }
    });
// get-свойство config
    Object.defineProperty(this, 'config', {
        get: function () {
            return _config;
        }
    });
// обработки события click (скрытие сообщения при нажатии на кнопку "Закрыть")
    _element.addEventListener('click', function (e) {
        if (e.target.classList.contains('mytoast__close')) {
            _this.hide();
        }
    });
}
// методы show и hide, описанные в прототипе объекта Toast
Toast.prototype = {
    show: function () {
        var _this = this;
        this.element.classList.add('mytoast_show');
        if (this.config.autohide) {
            setTimeout(function () {
                _this.hide();
            }, this.config.delay)
        }
    },
    hide: function () {
        this.element.classList.remove('mytoast_show');
    }
};
// статическая функция для Toast (используется для создания сообщения)
Toast.create = function (text, color) {
    var
        fragment = document.createDocumentFragment(),
        toast = document.createElement('div'),
        toastClose = document.createElement('button');
    toast.classList.add('mytoast');
    toast.style.backgroundColor = 'rgba(' + parseInt(color.substr(1, 2), 16) + ',' + parseInt(color.substr(3, 2), 16) + ',' + parseInt(color.substr(5, 2), 16) + ',0.5)';
    toast.textContent = text;
    toastClose.classList.add('mytoast__close');
    toastClose.setAttribute('type', 'button');
    toastClose.textContent = '×';
    toast.appendChild(toastClose);
    fragment.appendChild(toast);
    return fragment;
};
// статическая функция для Toast (используется для добавления сообщения на страницу)
Toast.add = function (params) {
    var config = {
        header: 'Название заголовка',
        text: 'Текст сообщения...',
        color: '#ffffff',
        autohide: true,
        delay: 5000
    };
    if (params !== undefined) {
        for (var item in params) {
            config[item] = params[item];
        }
    }
    if (!document.querySelector('.mytoasts')) {
        var container = document.createElement('div');
        container.classList.add('mytoasts');
        container.style.cssText = 'position: fixed; top: 10%; right: 15px; width: 250px;';
        document.body.appendChild(container);
    }
    document.querySelector('.mytoasts').appendChild(Toast.create(config.text, config.color));
    var toasts = document.querySelectorAll('.mytoast');
    var toast = new Toast(toasts[toasts.length - 1], { autohide: config.autohide, delay: config.delay });
    toast.show();
    return toast;
}