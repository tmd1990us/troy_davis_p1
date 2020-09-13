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
            document.getElementById('toRegister').setAttribute('hidden', true);
            document.getElementById('toLogin').setAttribute('hidden', true);
            configureLoginView();
        }
    }
}

function loadSuccess() {
    console.log('in loadSuccess()');
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'success.view'); // third parameter (default true) indicates we want to make this req async
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureSuccess();
        }
    }
}
function loadFailure() {
    console.log('in loadFailure()');
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'failure.view', true); // third parameter (default true) indicates we want to make this req async
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
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

function loadViewAllReimb() {
    console.log('in loadViewAllReimb()');
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'view_all_reimb.view'); // third parameter of this method is optional (defaults to true)
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureViewAllReimbView();
        }
    }
}

function loadApproveDenyReimb(obj) {
    var index = obj.parentNode.parentNode.rowIndex; 
    let x = document.getElementById("view-reimb-table").rows[index].cells[0];
    let reimbID = x.innerHTML;
    localStorage.setItem('reimbID',reimbID);
    let amount = document.getElementById("view-reimb-table").rows[index].cells[1].innerHTML;
    let description = document.getElementById("view-reimb-table").rows[index].cells[3].innerHTML;
    let type = document.getElementById("view-reimb-table").rows[index].cells[4].innerHTML;
    let authorId = document.getElementById("view-reimb-table").rows[index].cells[5].innerHTML;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'update_fin_reimb.view');
    let reim = {
        reimbID: reimbID
    }
    xhr.send();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            document.getElementById('reimbID').innerHTML = localStorage.getItem('reimbID');
        }
    }
}

function loadUpdateReimb(obj){
    var index = obj.parentNode.parentNode.rowIndex; 
    let x = document.getElementById("view-reimb-table").rows[index].cells[0];
    let reimbID = x.innerHTML;
    localStorage.setItem('reimbID',reimbID);
    let amount = document.getElementById("view-reimb-table").rows[index].cells[1].innerHTML;
    let description = document.getElementById("view-reimb-table").rows[index].cells[3].innerHTML;
    let type = document.getElementById("view-reimb-table").rows[index].cells[4].innerHTML;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'update_emp_reimb.view');
    let reim = {
        reimbID: reimbID
    }
    xhr.send(reim);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            document.getElementById('reimbID').innerHTML = localStorage.getItem('reimbID');
            document.getElementById('amount').setAttribute('placeholder',amount);
            document.getElementById('description').setAttribute('placeholder',description);
            document.getElementById('type').setAttribute('placeholder',type);
            document.getElementById('update').addEventListener('click', updateReimb);
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


//----------------CONFIGURE VIEWS--------------------

function configureSuccess(){
    const element = document.getElementById('success-element');
    element.classList.add('animate__animated', 'animate__zoomIn', 'animate__slow');
    element.addEventListener('animationend', () => {
        loadViewEmpReimb();
    });
}

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
    document.getElementById('submit').addEventListener('click', submitReimb);
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
            const reimbs = JSON.parse(xhr.responseText);
            console.log(reimbs);
            for (var i = 0; i < reimbs.length; i++){
                var rowCount = myTableDiv.rows.length;
                var row = myTableDiv.insertRow(rowCount);
                row.insertCell(0).innerHTML = reimbs[i].id;
                row.insertCell(1).innerHTML = reimbs[i].amount;
                row.insertCell(2).innerHTML = reimbs[i].submitted;
                row.insertCell(3).innerHTML = reimbs[i].description;
                row.insertCell(4).innerHTML = reimbs[i].reimbursementType;
                row.insertCell(5).innerHTML = reimbs[i].resolverId;
                row.insertCell(6).innerHTML = reimbs[i].reimbursementStatus;
                if (reimbs[i].reimbursementStatus == 'PENDING'){
                    row.insertCell(7).innerHTML= '<input type="button" class="btn btn-primary" value = "Update" onClick="Javacsript:loadUpdateReimb(this)">';
                }
            }
            
        }
    }
}

function configureViewAllReimbView(){
    console.log('Getting All Reimbursements...');
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'reimbursements'); // third parameter of this method is optional (defaults to true)
    xhr.send()
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // APP_VIEW.innerHTML = xhr.responseText;
            var myTableDiv = document.getElementById("view-all-reimb-table");
            var table = document.createElement('TABLE');
            var tableBody = document.createElement('TBODY');
            table.appendChild(tableBody);
            const reimbs = JSON.parse(xhr.responseText);
            console.log(reimbs);
            for (var i = 0; i < reimbs.length; i++){
                var rowCount = myTableDiv.rows.length;
                var row = myTableDiv.insertRow(rowCount);
                row.insertCell(0).innerHTML = reimbs[i].id;
                row.insertCell(1).innerHTML = reimbs[i].amount;
                row.insertCell(2).innerHTML = reimbs[i].submitted;
                row.insertCell(3).innerHTML = reimbs[i].description;
                row.insertCell(4).innerHTML = reimbs[i].reimbursementType;
                row.insertCell(5).innerHTML = reimbs[i].authorId;
                row.insertCell(6).innerHTML = reimbs[i].reimbursementStatus;
                if (reimbs[i].reimbursementStatus == 'PENDING'){
                    row.insertCell(7).innerHTML= '<input type="button" class="btn btn-warning" value = "Approve/Deny" onClick="Javacsript:loadApproveDenyReimb(this)">';
                }
            }
            
        }
    }
}

//------------------OPERATIONS-----------------------

function updateReimb() {
    console.log('in updating reimb');
    let rid = document.getElementById('reimbID').innerHTML;
    let tp = document.getElementById('type').value;
    let ds = document.getElementById('description').value;
    let am = document.getElementById('amount').value;
    let thisReimb = {
        id: rid,
        reimbursementType: tp,
        description: ds,
        amount: am
    }
    let reimbJSON = JSON.stringify(thisReimb);
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', 'reimbursements');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(reimbJSON);
    xhr.onreadystatechange = function () {
        if(xhr.readyState == 4 && xhr.status == 201){
            loadSuccess();
            // loadHome();
        } else if (xhr.readyState == 4 && xhr.status != 201) {
            // loadFailure();
            let err = JSON.parse(xhr.responseText);
            console.log(err.message);
        }
    }
}


function submitReimb(){
    console.log('in submitting reimb')
    let tp = document.getElementById('type').value;
    let ds = document.getElementById('description').value;
    let am = document.getElementById('amount').value;
    let thisReimb = {
        reimbursementType: tp,
        description: ds,
        amount: am
    }
    console.log(thisReimb);
    let reimbJSON = JSON.stringify(thisReimb);
    let xhr = new XMLHttpRequest();
    xhr.open('POST', 'reimbursements');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(reimbJSON);
    xhr.onreadystatechange = function () {
        if(xhr.readyState == 4 && xhr.status == 201){
            loadSuccess();
            // loadHome();
        } else if (xhr.readyState == 4 && xhr.status != 201) {
            // loadFailure();
            let err = JSON.parse(xhr.responseText);
            console.log(err.message);
        }
    }
}

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
            var homeUser = JSON.parse(localStorage.getItem('authUser'));
            var role = homeUser.role;
            localStorage.setItem('role', role);
            loadHome();
        } else if (xhr.readyState == 4 && xhr.status == 401) {
            document.getElementById('login-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('login-message').innerText = err.message;
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
            document.getElementById('toRegister').removeAttribute('hidden');
            document.getElementById('toLogin').removeAttribute('hidden');
            loadLogin();
        }
    }
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