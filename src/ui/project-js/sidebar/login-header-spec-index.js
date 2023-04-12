import template from './login-header-text.mjs';
document.getElementById("header-login").innerHTML += template;

const loginButton = document.getElementById('login-input');
loginButton.addEventListener('click', function () {
    window.location.href = 'login.html';
});
