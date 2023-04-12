function login() {
    //DOM에서 값 가져오기
    const memberName = document.getElementById("memberName").value;
    const password = document.getElementById("password").value;
    //JSON데이터 변경
    const loginForm = {
        memberName: memberName,
        password: password
    }
    //JSON직렬화
    const data = JSON.stringify(loginForm)
    const uri = `${API_HOST}/login`
    console.log = uri;
    let xhr = new XMLHttpRequest();
    xhr.open('POST', uri)
    xhr.setRequestHeader('Content-type', 'application/json')
    xhr.withCredentials = true
    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) return;
        if (xhr.status === 200) {
            window.location.href = "/index.html";
        }
        if (xhr.status === 400) {
            console.log(xhr.response);
        }
    }
    xhr.send(data)
}
