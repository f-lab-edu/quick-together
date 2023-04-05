import { API_HOST, UI_HOST } from "../host-info.js";

export function signUp() {
    const memberName = document.getElementById("memberName").value
    const password = document.getElementById("password").value
    const passwordCheck = document.getElementById("re-pass").value
    const name = document.getElementById("name").value
    const email = document.getElementById("email").value

    validate(memberName, '^[a-z0-9]{4,20}$', "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.");
    validate(password, '^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$', "비밀번호는 영문, 숫자, 특수문자를 포함하여 8~16자리여야 합니다.");
    validate(name, '^[가-힣a-zA-Z0-9]{2,10}$', "닉네임은 특수문자를 포함하지 않은 2~10자리여야 합니다.");
    validate(email, '^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$', "이메일 형식이 올바르지 않습니다.");

    if (password !== passwordCheck) {
        return alert('비밀번호가 일치하지 않습니다. 다시한번 확인해주세요.');
    }
    if (document.getElementById("agree-term").checked === false) {
        return alert('정책에 동의하지 않으시면 가입할 수 없습니다. ');
    }

    const memberRequest = {
        memberName: memberName,
        password: password,
        name: name,
        email: email,
    }
    const data = JSON.stringify(memberRequest)
    const xhr = new XMLHttpRequest();
    xhr.open('POST', `${API_HOST}/members`);
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.withCredentials = true;
    xhr.send(data)

    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) return;
        if (xhr.status === 201) {
            window.location.href = `../available-plans/available-plan-form.html`;
        } else {
            alert(JSON.parse(xhr.response).message)
        }
    }

}

function validate(value, regex, message) {
    if (!new RegExp(regex).test(value)) {
        value.placeholder = message;
        return alert(message);
    }
}



