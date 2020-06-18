// let form = document.forms.namedItem('formEditUser')
// let btnEditUser = document.getElementById('btnEditUser')
// let alertEditUser = document.getElementById('alertEditUser')
//
// btnEditUser.onclick = async function () {
//     let user = {
//         firstName: form.elements.namedItem('firstName').value,
//         lastName: form.elements.namedItem('lastName').value,
//         username: form.elements.namedItem('username').value,
//         password: form.elements.namedItem('password').value
//     }
//     if (user.firstName !== '' || user.lastName !== '' || user.username !== '' || user.password !== '') {
//         await editUser(user)
//             .then(response => {
//                 if (response.ok) {
//                     successEditUser(alertEditUser, 2000)
//                 }
//             })
//     } else {
//         noValidForm(alertEditUser, 2000)
//     }
//
// }

// async function editUser(user) {
//     return await fetch('/api/admin/update', {
//         method: 'PUT',
//         headers: {
//             'Content-Type': 'application/json;charset=utf-8'
//         },
//         body: JSON.stringify(user)
//     })
// }
//
// function successEditUser(container, time) {
//     container.append('<div class="alert alert-success m-3" role="alert">Ваша статья, успешно добавлена</div>');
//     setTimeout(function () {
//         container.empty();
//         $('#modalWindowCreateTopic').modal('hide');
//     }, time)
// }
//
// function noValidForm(container, time) {
//     container.append('<div class="alert alert-danger m-3" role="alert">Поля не должны быть пустыми</div>');
//     setTimeout(function () {
//         container.empty();
//     }, time)
// }
//
// function failEditUser(container, time) {
//     container.append('<div class="alert alert-danger m-3" role="alert">Что то пошло не так! Попробуйте снова</div>');
//     setTimeout(function () {
//         container.empty();
//     }, time)
// }








