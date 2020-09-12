const APP_VIEW = document.getElementById('app-view');
const APP_NAVBAR = document.getElementById('app-navbar');
window.onload = function() {
    loadLogin();
   document.getElementById('toLogin').addEventListener('click', loadLogin);
   document.getElementById('toRegister').addEventListener('click', loadRegister);
   document.getElementById('toHome').addEventListener('click', loadHome);
   document.getElementById('toLogout').addEventListener('click', logout);
}

function loadLogin() {
    console.log('in loadLogin()');
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'login.view', true); // third parameter (default true) indicates we want to make this req async
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureLoginView();
        }
    }
}

function loadRegister() {
    console.log('in loadRegister()');
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'register.view'); // third parameter of this method is optional (defaults to true)
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureRegisterView();
        }
    }
}

function loadSubmitReimb(){
    console.log('in loadSubmitReimb()');
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'submit_reimb.view'); // third parameter of this method is optional (defaults to true)
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureSubmitReimbView();
        }
    }
}

function loadViewEmpReimb(){
    console.log('in loadViewReimb()');
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'view_emp_reimb.view'); // third parameter of this method is optional (defaults to true)
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureViewEmpReimbView();
        }
    }
}

//----------------CONFIGURE VIEWS--------------------

function configureLoginView() {
    console.log('in configureLoginView()');
    document.getElementById('login-message').setAttribute('hidden', true);
    document.getElementById('login-button-container').addEventListener('mouseover', validateLoginForm);
    document.getElementById('login').addEventListener('click', login);
}

function configureHomeView() {
    let authUser = JSON.parse(localStorage.getItem('authUser'));
    document.getElementById('loggedInUsername').innerText = authUser.username;
    var homeUser = JSON.parse(localStorage.getItem('authUser'));
    var role = homeUser.role;
    console.log('the current role is: ');
    console.log(role);
    if(role == 3){
        //Employee
        document.getElementById('submit-reimb').addEventListener('click', loadSubmitReimb);
        document.getElementById('view-reimb').addEventListener('click', loadViewEmpReimb);
    }
    if(role == 2){
        //Fin Manager
        document.getElementById('view-all-reimb').addEventListener('click', loadViewAllReimb);
    }
    if(role == 1){
        //Admin
    }
}

function configureRegisterView() {
    console.log('in configureRegisterView()');
    document.getElementById('reg-message').setAttribute('hidden', true);
    document.getElementById('reg-username').addEventListener('blur', isUsernameAvailable);
    document.getElementById('email').addEventListener('blur', isEmailAvailable);
    document.getElementById('register').setAttribute('disabled', true);
    document.getElementById('reg-button-container').addEventListener('mouseover', validateRegisterForm);
    document.getElementById('register').addEventListener('click', register);
}

function configureSubmitReimbView() {
    
}

function configureViewEmpReimbView() {
    console.log('Getting Reimbursements for employee...');
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'reimbursements'); // third parameter of this method is optional (defaults to true)
    xhr.send()
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // APP_VIEW.innerHTML = xhr.responseText;
            var myTableDiv = document.getElementById("view-reimb-table");
            var table = document.createElement('TABLE');
            var tableBody = document.createElement('TBODY');
            table.appendChild(tableBody);
            var reimbs = JSON.parse(xhr.responseText);
            console.log(reimbs);
            for (var i = 0; i < xhr.responseText.length -1; i++){
                var tr = document.createElement('TR');
                tableBody.appendChild(tr);
                var td = document.createElement('TD');
                var td2 = document.createElement('TD');
                var td3 = document.createElement('TD');
                var td4 = document.createElement('TD');
                var td5 = document.createElement('TD');
                var td6 = document.createElement('TD');
                var td7 = document.createElement('TD');
                var td8 = document.createElement('TD');
                console.log(reimbs[i].id);
                td.appendChild(document.createTextNode('<input type="button" value="Approve" onClick="Javascript:approveRow(this)">'));
                tr.appendChild(td);
                td2.appendChild(document.createTextNode(reimbs[i].id));
                tr.appendChild(td2);
                td3.appendChild(document.createTextNode(reimbs[i].amount));
                tr.appendChild(td3);
                td4.appendChild(document.createTextNode(reimbs[i].submitted));
                tr.appendChild(td4);
                td5.appendChild(document.createTextNode(reimbs[i].description));
                tr.appendChild(td5);
                td6.appendChild(document.createTextNode(reimbs[i].reimbursementType));
                tr.appendChild(td6);
                td7.appendChild(document.createTextNode(reimbs[i].resolverId));
                tr.appendChild(td7);
                td8.appendChild(document.createTextNode(reimbs[i].reimbursementStatus));
                tr.appendChild(td8);
            }
            myTableDiv.appendChild(table);
        }
    }
}


//------------------OPERATIONS-----------------------

function login() {
    console.log('in login()');
    let un = document.getElementById('login-username').value;
    let pw = document.getElementById('login-password').value;
    let credentials = {
        username: un,
        password: pw
    }
    let credentialsJSON = JSON.stringify(credentials);
    let xhr = new XMLHttpRequest();
    xhr.open('POST', 'auth');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(credentialsJSON);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById('login-message').setAttribute('hidden', true);
            localStorage.setItem('authUser', xhr.responseText);
            
            
            loadHome();
        } else if (xhr.readyState == 4 && xhr.status == 401) {
            document.getElementById('login-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('login-message').innerText = err.message;
        }
    }
}

function loadHome() {
    console.log('in loadHome()');
    if (!localStorage.getItem('authUser')) {
        console.log('No user logged in, navigating to login screen');
        loadLogin();
        return;
    }
    let xhr = new XMLHttpRequest();
    // var homeUser = JSON.parse(localStorage.getItem('authUser'));
    // var role = homeUser.role;
    // console.log('the current role is: ');
    // console.log(role);
    xhr.open('GET', 'home.view');
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureHomeView();
        }
    }
}



function register() {
    console.log('in register()');
    let fn = document.getElementById('fn').value;
    let ln = document.getElementById('ln').value;
    let email = document.getElementById('email').value;
    let un = document.getElementById('reg-username').value;
    let pw = document.getElementById('reg-password').value;
    let newUser = {
        firstName: fn,
        lastName: ln,
        email: email,
        username: un,
        password: pw
    }
    let newUserJSON = JSON.stringify(newUser);
    let xhr = new XMLHttpRequest();
    xhr.open('POST', 'users');
    xhr.send(newUserJSON);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 201) {
            loadLogin();
        } else if (xhr.readyState == 4 && xhr.status != 201) {
            document.getElementById('reg-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('reg-message').innerText = err.message;
        }
    }
}

function logout() {
    
    console.log('in logout()');

    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'auth');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 204) {
            console.log('logout successful!');
            localStorage.removeItem('authUser');
            loadLogin();
        }
    }
}



function addTable() {
    var myTableDiv = document.getElementById("dynamic-reimb-table");
    var table = document.createElement('TABLE');
    var tableBody = document.createElement('TBODY');
    table.appendChild(tableBody);
    for (var i=0; i<3; i++){
        var tr = document.createElement('TR');
        tableBody.appendChild(tr);
        for (var j=0; j<4; j++){
            var td = document.createElement('TD');
            td.width='75';
            td.appendChild(document.createTextNode("Cell " + i + "," + j));
            tr.appendChild(td);
        }
     }
     myTableDiv.appendChild(table);
}


















//---------------------FORM VALIDATION-------------------------

function validateLoginForm() {

    console.log('in validateLoginForm()');

    let msg = document.getElementById('login-message').innerText;

    if (msg == 'User authentication failed!') {
        return;
    }

    let un = document.getElementById('login-username').value;
    let pw = document.getElementById('login-password').value;

    if (!un || !pw) {
        document.getElementById('login-message').removeAttribute('hidden');
        document.getElementById('login-message').innerText = 'You must provided values for all fields in the form!';
        document.getElementById('login').setAttribute('disabled', true);
    } else {
        document.getElementById('login').removeAttribute('disabled');
        document.getElementById('login-message').setAttribute('hidden', true);
    }

}

function validateRegisterForm() {

    console.log('in validateRegisterForm()');

    let fn = document.getElementById('fn').value;
    let ln = document.getElementById('ln').value;
    let email = document.getElementById('email').value;
    let un = document.getElementById('reg-username').value;
    let pw = document.getElementById('reg-password').value;

    if (!fn || !ln || !email || !un || !pw) {
        document.getElementById('reg-message').removeAttribute('hidden');
        document.getElementById('reg-message').innerText = 'You must provided values for all fields in the form!'
        document.getElementById('register').setAttribute('disabled', true);
    } else {
        document.getElementById('register').removeAttribute('disabled');
        document.getElementById('reg-message').setAttribute('hidden', true);
    }
}