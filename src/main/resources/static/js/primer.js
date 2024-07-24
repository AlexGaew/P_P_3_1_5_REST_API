document.addEventListener('DOMContentLoaded', function() {
    var modal = document.getElementById("modal");
    var closeButton = document.getElementById("closeButton");
    var editButton = document.getElementById("editButton");
    var saveButton = document.getElementById("saveButton");
    var userSelect = document.getElementById("userSelect");
    var userIdInput = document.getElementById("userId");
    var usernameInput = document.getElementById("username");
    var emailInput = document.getElementById("email");

    editButton.onclick = function() {
        var userId = userSelect.value;

        // AJAX-запрос для получения данных пользователя
        fetch('/admin/' + userId)
            .then(response => response.json())
            .then(user => {
                userIdInput.value = user.id;
                usernameInput.value = user.username;
                emailInput.value = user.email;
                modal.style.display = "block";
            })
            .catch(error => console.error('Ошибка при получении данных пользователя', error));
    }

    closeButton.onclick = function() {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    saveButton.onclick = function() {
        var userId = userIdInput.value;
        var username = usernameInput.value;
        var email = emailInput.value;

        fetch('/users/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ id: userId, username: username, email: email })
        })
            .then(response => {
                if (response.ok) {
                    alert('Данные пользователя успешно обновлены');
                    modal.style.display = "none";
                } else {
                    console.error('Ошибка при обновлении данных пользователя');
                }
            })
            .catch(error => console.error('Ошибка при обновлении данных пользователя', error));
    }
});
