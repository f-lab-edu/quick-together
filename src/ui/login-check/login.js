var allmemberName = null;
var allmemberId = null;
async function checkLoginStatus() {
    return await fetch(`http://${API_IP}:${API_PORT}/login`, {
        credentials: 'include'  // HttpOnly 쿠키를 함께 전송하도록 설정
    })
    .then(response => {
        //throw new Error('Network response was not ok.');
        return response.json();
    })
    .then(data => {

        window.memberId  = data.memberId;
        window.memberName  = data.memberName;
        allmemberName = data.memberName;
        allmemberId = data.memberId;

        console.log('loginjs : ' +data.memberId);
        return data;
    })
    .catch(error => {
        console.error('Error:', error);
        return false;
    });
}