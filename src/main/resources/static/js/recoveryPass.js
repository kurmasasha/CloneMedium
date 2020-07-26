$("#recoverPassword").on("click", async function () {
    let mail = $("#email").val();
    let message = document.getElementById('messageForUser');
    message.innerHTML = "";
    let options = {
        method: 'POST',
        headers: {
            'Content-Type': 'text/html;charset=UTF-8'
        }
    };
    let URL = '/api/free-user/recoveryPassword/getCode/?email=' + mail;
    await fetch(URL, options)
        .then(
            response => {
                if (response.ok) {
                    let form = document.getElementById('Forgot-Password-Form');
                    form.innerHTML = "Вам на почту отправлено письмо с инструкцией для восстановления пароля"
                } else {
                    message.style.color = "red";
                    message.innerHTML = "Пользователя с таким e-mail не существует!";
                }
            }
        )
});