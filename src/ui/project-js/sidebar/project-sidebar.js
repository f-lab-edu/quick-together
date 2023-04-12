import template from './project-sidebar-element.mjs';
document.getElementById("sidebar-project").innerHTML += template;
//    <li><button id="button-project-info" class="sidebar-project">프로젝트 정보</button></li>
//     <li><button id="button-meeting-upcoming" class="sidebar-project">다가오는 미팅</button></li>
//     <li><button id="button-meeting-list" class="sidebar-project">다가오는 미팅</button></li>
//     <li><button id="button-meeting-create" class="sidebar-project">미팅 만들기</button></li>

document.getElementById('button-project-info')
    .addEventListener('click', () => { window.location.href = '/project-detail.html'; }
    );

document.getElementById('button-meeting-create')
    .addEventListener('click', () => { window.location.href = '/meeting-form.html'; }
    );

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