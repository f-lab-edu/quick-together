import template from './login-header-text.mjs';
document.getElementById("header-login").innerHTML += template;

const loginButton = document.getElementById('login-input');
loginButton.addEventListener('click', function () {
    window.location.href = 'login.html';
});

checkLoginStatus().then(logined => {
    if (logined.memberId === undefined) {
        console.log('로그인 X');
        alert('로그인을 해주세요');
        window.location.href = 'index.html';
    } else {
        document.getElementById("login").innerHTML = `Member : ${logined.memberName}`;
        document.getElementById("logout").innerHTML = `<input id="logout-input" type="submit" value="Logout">`;

        const Logout = document.getElementById('logout-input');
        Logout.addEventListener('click', function () {
        logout();
        });
        
    }
});

function logout(){

    fetch(`http://${API_IP}:${API_PORT}/logout`, {
        method: 'DELETE',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => {
            if (response.status == 401) {
                alert('로그인을 해주세요.');
            } else if (!response.ok) {
                response.json().then(error => {
                    alert(error.message);
                });
            } else {
                alert('로그아웃 되었습니다.');
                window.location.href = 'index.html';
            }
        })
        .catch(error => console.error(error));

}