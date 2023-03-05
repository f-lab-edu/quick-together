function signUp() {
    const memberName = document.getElementById("memberName").value
    const password = document.getElementById("password").value
    const passwordCheck = document.getElementById("re_pass").value
    const name = document.getElementById("name").value
    const email = document.getElementById("email").value

    if (document.getElementById("agree-term").checked == false) {
        return alert('정책에 동의하지 않으시면 가입할 수 없습니다. ');
    }

    if (password !== passwordCheck) {
        return alert('비밀번호가 일치하지 않습니다. 다시한번 확인해주세요.');
    }

    const memberRequest = {
        memberName: memberName,
        password: password,
        name: name,
        email: email
    }

    const data = JSON.stringify(memberRequest)
    console.log(data)
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8080/members');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.withCredentials = true;
    xhr.send(data)

    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) return;
        if (xhr.status === 201) {
            login(memberName, password);
            window.location.href = "http://localhost:5500/available-plans/available-plan-form.html"
        } else {
            alert(JSON.parse(xhr.response).message)
        }


    }


}
//
function login(memberName, password) {
    const loginForm = {
        memberName: memberName,
        password: password
    }

    const data = JSON.stringify(loginForm)
    const uri = "http://localhost:8080/login"
    let xhr = new XMLHttpRequest();
    xhr.open('POST', uri);
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.withCredentials = true;
    xhr.send(data)
    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) return;

        if (xhr.status === 201) {
            console.log("로그인이 완료되었다.")
        } else {
            console.log(xhr.response, xhr.status)
        }


    }
}


