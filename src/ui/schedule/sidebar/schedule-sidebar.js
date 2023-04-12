import template from './schedule-sidebar-element.mjs';
document.getElementById("sidebar-schedule").innerHTML += template;

document.getElementById('button-plans')
    .addEventListener('click', () => { window.location.href = '/schedule-plans.html' });

document.getElementById('button-available-plans')
    .addEventListener('click', () => { window.location.href = '/schedule-available-plans.html' });

document.getElementById('button-schedule-setting')
    .addEventListener('click', () => { window.location.href = '/schedule-setting.html' });

checkLoginStatus().then(logined => {
    if (logined.memberId === undefined) {
        console.log('로그인 X');
    } else {
        document.getElementById("login").innerHTML = `Member : ${logined.memberName}`;
    }
});

function getUserId() {
    return logined.memberId;
}