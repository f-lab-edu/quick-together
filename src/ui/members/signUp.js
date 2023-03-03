function signUp() {
    const memberName = document.getElementById("memberName").value
    const password = document.getElementById("password").value
    const name = document.getElementById("name").value
    const email = document.getElementById("email").value

    const memberRequest = {
        memberName: memberName,
        password: password,
        name: name,
        email: email
    }

    const data = JSON.stringify(memberRequest)
    console.log(data)
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8080/members')
    xhr.setRequestHeader('Content-type', 'application/json')
    xhr.send(data)

    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) return;
        if (xhr.status === 201) {
            window.location.href = "https://www.naver.com"
        } else {
            console.log(xhr.status, xhr.response)
        }


    }


}


