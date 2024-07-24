// // Получить элементы
// var modal = document.getElementById("registerModal");
// var btn = document.getElementById("editBtn");
// var span = document.getElementsByClassName("close")[0];
//
// // Открыть модальное окно при нажатии на кнопку
// btn.onclick = function() {
//     modal.style.display = "block";
// }
//
// // Закрыть модальное окно при нажатии на крестик
// span.onclick = function() {
//     modal.style.display = "none";
// }
//
// // Закрыть модальное окно при клике вне его
// window.onclick = function(event) {
//     if (event.target == modal) {
//         modal.style.display = "none";
//     }
// }
// document.addEventListener('DOMContentLoaded', function() {
//     var modal = document.getElementById("modal");
//     var closeButton = document.getElementById("closeButton");
//     var editButton = document.querySelectorAll(".editButton");
//     var userIdInput = document.getElementById("userId");
//
//         editButton.forEach(function(button) {
//         button.onclick = function() {
//             var userId = button.getAttribute("data-id");
//             userIdInput.value = userId;
//             modal.style.display = "block";
//         }
//     });
//
//     closeButton.onclick = function() {
//         modal.style.display = "none";
//     }
//
//     window.onclick = function(event) {
//         if (event.target == modal) {
//             modal.style.display = "none";
//         }
//     }
// });

// document.addEventListener('DOMContentLoaded', function() {
//     var modal = document.getElementById("modal");
//     var closeButton = document.getElementById("closeButton");
//     var editButtons = document.querySelectorAll(".editButton");
//     var userIdInput = document.getElementById("userId");
//
//     editButtons.forEach(function(button) {
//         button.onclick = function() {
//             var userId = button.getAttribute("data-id");
//             userIdInput.value = userId;
//             modal.style.display = "block";
//         }
//     });
//
//     closeButton.onclick = function() {
//         modal.style.display = "none";
//     }
//
//     window.onclick = function(event) {
//         if (event.target == modal) {
//             modal.style.display = "none";
//         }
//     }
// });

// document.addEventListener('DOMContentLoaded', function() {
//     var modal = document.getElementById("modal");
//     var closeButton = document.getElementById("closeButton");
//     var editButtons = document.querySelectorAll(".editButton");
//     var userIdInput = document.getElementById("userId");
//
//     editButtons.forEach(function(button) {
//         button.onclick = function() {
//             var userId = button.getAttribute("data-id");
//             userIdInput.value = userId;
//             modal.style.display = "block";
//         }
//     });
//
//     closeButton.onclick = function() {
//         modal.style.display = "none";
//     }
//
//     window.onclick = function(event) {
//         if (event.target == modal) {
//             modal.style.display = "none";
//         }
//     }
// });
document.addEventListener('DOMContentLoaded', function () {
    var editModal = document.getElementById('userEditDialog');
    var rolesSelect = document.getElementById('user-role');

    editModal.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget;
        var id = button.getAttribute('data-userId');
        var userName = button.getAttribute('data-username');
        var userSurName = button.getAttribute('data-surName');
        var age = button.getAttribute('data-age');
        var email = button.getAttribute('data-email');
        var password = button.getAttribute('data-password');
        var roles = button.getAttribute('data-roles').split(',');

        var modal = editModal;
        modal.querySelector('#userId').value = id;
        modal.querySelector('#user-name').value = userName;
        modal.querySelector('#user-lastName').value = userSurName;
        modal.querySelector('#user-age').value = age;
        modal.querySelector('#user-email').value = email;
        modal.querySelector('#user-password').value = password;

        var rolesSelect = modal.querySelector('#user-role');
        for (var i = 0; i < rolesSelect.options.length; i++) {
            rolesSelect.options[i].selected = roles.includes(rolesSelect.options[i].value);
            console.log(rolesSelect.options[i].value)
        }

    });



});

//     rolesSelect.addEventListener('change', function () {
//
//         var selectedRoles = Array.from(this.selectedOptions).map(option => option.value);
//        console.log(selectedRoles)
//         var userRoleSetInput = document.getElementById('userRoleSet');
//
//         userRoleSetInput.value = JSON.stringify(selectedRoles);
//         console.log(userRoleSetInput.value)
//     });

// editButton.onclick = function() {
//     var userId = userIdInput.value;
//     var username = usernameInput.value;
//     var email = emailInput.value;
//
//     fetch('/users/update', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify({ id: userId, username: username, email: email })
//     })
//         .then(response => {
//             if (response.ok) {
//                 alert('Данные пользователя успешно обновлены');
//                 editModal.style.display = "none";
//             } else {
//                 console.error('Ошибка при обновлении данных пользователя');
//             }
//         })
//         .catch(error => console.error('Ошибка при обновлении данных пользователя', error));
// };
//
// })
// ;

// document.addEventListener('DOMContentLoaded', function() {
//     var editModal = document.getElementById("editModal");
//     var rolesSelect = document.getElementById('editRoles');
//
//     var closeButton = document.getElementById("closeButton");
//     var editButton = document.getElementById("editButton");
//     var saveButton = document.getElementById("saveButton");
//     var userSelect = document.getElementById("userSelect");
//     var userIdInput = document.getElementById("data-id");
//
//     var usernameInput = document.getElementById("username");
//     var emailInput = document.getElementById("email");
//
//     editButton.onclick = function() {
//         var userId = userSelect.value;
//
//         // AJAX-запрос для получения данных пользователя
//         fetch('/admin/' + userId)
//             .then(response => response.json())
//             .then(user => {
//                 userIdInput.value = user.id;
//                 usernameInput.value = user.username;
//                 emailInput.value = user.email;
//                 editModal.style.display = "block";
//             })
//             .catch(error => console.error('Ошибка при получении данных пользователя', error));
//     }
//
//     closeButton.onclick = function() {
//         editModal.style.display = "none";
//     }
//
//     window.onclick = function(event) {
//         if (event.target == editModal) {
//             editModal.style.display = "none";
//         }
//     }
//
//     saveButton.onclick = function() {
//         var userId = userIdInput.value;
//         var username = usernameInput.value;
//         var email = emailInput.value;
//
//         fetch('/users/update', {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify({ id: userId, username: username, email: email })
//         })
//             .then(response => {
//                 if (response.ok) {
//                     alert('Данные пользователя успешно обновлены');
//                     editModal.style.display = "none";
//                 } else {
//                     console.error('Ошибка при обновлении данных пользователя');
//                 }
//             })
//             .catch(error => console.error('Ошибка при обновлении данных пользователя', error));
//     }
// });

