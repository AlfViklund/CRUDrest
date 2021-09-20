$(document).ready(function(){
    updateUsersTable()
    currentUserTable()
})

document.addEventListener('click', function (event) {
    event.preventDefault()
    if ($(event.target).hasClass('logout')) {
        logout()
    }
    if ($(event.target).hasClass('newUser')) {
        newUser()
    }
    if ($(event.target).hasClass('edit-button')) {
        let href = $(event.target).attr("href")
        modal(href, 'edit')
    }
    if ($(event.target).hasClass('delete-button')) {
        let href = $(event.target).attr("href")
        modal(href, 'delete')
    }
    if ($(event.target).hasClass('submitButton')) {
        let method = $(event.target).attr("href")
        sendReq(method)
    }
})

function newUser() {
    let user = {
        name: $("#firstName").val().trim(),
        lastName: $("#lastName").val().trim(),
        age: $("#age").val().trim(),
        email: $("#email").val().trim(),
        password: $("#password").val().trim(),
        role: $("#roleNewUser").val().trim()
    }
    fetch("/api/users", {
        method: 'POST',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(user)
    }).then(() => updateUsersTable())
    $('.form-control').each(function () {
        $(this).val('')
    })

}

function logout(){
    document.location.replace("/logout")
}

function updateUsersTable() {
    let tbody = $("#users_tbody")
    tbody.children().remove()
    fetch("/api/users")
        .then((response) => {
            response.json()
                .then(data => createTable(data, tbody))
        })
}

function createTable(users, tbody) {
    users.forEach(function (item, i, arr) {
        let roles = ""
        for(let i = 0; i < item.roles.length; i++) {
            roles += item.roles[i].role.substring(5) + " "
        }
        let row = ` <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.lastName}</td>
                <td>${item.age}</td>
                <td>${item.email}</td>
                <td>${roles}</td>
                <td><a type="button" class="btn btn-info edit-button" data-toggle="modal"
                    href="/api/users/${item.id}">Edit</a>
                </td>
                <td><a type="button" class="btn btn-danger delete-button" data-toggle="modal"
                    href="/api/users/${item.id}">Delete</a>
                </td>
                </tr>`
        tbody.append(row)
    })
}

function currentUserTable() {
    let tbody = $("#currUserTbody")
    tbody.children().remove()
    fetch("/api/current")
        .then((response) => {
            response.json()
                .then(data =>  {
                    let roles = ""
                    for(let i = 0; i < data.roles.length; i++) {
                        roles += data.roles[i].role.substring(5) + " "
                    }
                    tbody.append(`
                        <tr>
                            <td>${data.id}</td>
                            <td>${data.name}</td>
                            <td>${data.lastName}</td>
                            <td>${data.age}</td>
                            <td>${data.email}</td>
                            <td>${roles}</td>
                        </tr>`)
                })
        })
}

function modal(href, mode) {
    let method = "PATCH"
    let text = 'Edit'
    let buttonStyle = 'btn-primary'
    let disabled = null
    if (mode == 'delete') {
        method = "DELETE"
        text = 'Delete'
        disabled = `disabled`
        buttonStyle = 'btn-danger'
    }

    $.get(href,function (user) {
        $("#uniModal").modal()
        let option = (`<option value="ROLE_USER" text="USER" selected=true>USER</option>`)
        option += (`<option value="ROLE_ADMIN" text="ADMIN">ADMIN</option>`)

        let modal = $(".modal-content")
        modal.children().remove()
        modal.append(`
                    <div class="modal-header">
                    <h5 class="modal-title" id="editModal">${text} user</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body" align="center">

                    <form>
                        <div class="form-group">
                            <label class="font-weight-bold" for="idModal">ID</label>
                            <input class="form-control mb-6 col-7" type="text" value="${user.id}" name="id" id="idModal" disabled/>
                        </div>
                        <div class="form-group">
                            <label class="font-weight-bold" for="firstNameModal">First name</label>
                            <input class="form-control mb-6 col-7" type="text" value="${user.name}" name="name" id="firstNameModal" ${disabled}/>
                        </div>
                        <div class="form-group">
                            <label class="font-weight-bold" for="lastNameModal">Last name</label>
                            <input class="form-control mb-6 col-7" type="text" value="${user.lastName}" name="lastName" id="lastNameModal" ${disabled}/>
                        </div>
                        <div class="form-group">
                            <label class="font-weight-bold" for="lastNameModal">Age</label>
                            <input class="form-control mb-6 col-7" type="text" value="${user.age}" name="age" id="ageModal" ${disabled}/>
                        </div>
                        <div class="form-group">
                            <label class="font-weight-bold" for="lastNameModal">Email</label>
                            <input class="form-control mb-6 col-7" type="text" value="${user.email}" name="email" id="emailModal" ${disabled}/>
                        </div>
                        <div class="form-group">
                            <label class="font-weight-bold" for="passwordModal">Password</label>
                            <input class="form-control mb-6 col-7" type="password" value="${user.password}" name="password" id="passwordModal" ${disabled}/>
                        </div>

                        <label class="font-weight-bold" for="roleModal">Role</label>
                        <select class="form-control mb-6 col-7" size="${user.roles.length}" id="roleModal" name="role" ${disabled}>
                        ${option}
                        </select>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <input type="submit" class="btn ${buttonStyle} submitButton" value="${text}" href="${method}"/>
                        </div>
                    </form>
                </div>`)
    })
}
function sendReq(method) {
    let user = {
        id: $("#idModal").val(),
        name: $("#firstNameModal").val().trim(),
        lastName: $("#lastNameModal").val().trim(),
        age: $("#ageModal").val().trim(),
        email: $("#emailModal").val().trim(),
        password: $("#passwordModal").val().trim(),
        role: $("#roleModal").val().trim()
    }
    fetch("/api/users", {
        method: method,
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(user)
    }).then(() => updateUsersTable())
    $("#uniModal").modal('hide')
}