function toggleEnter(buttonId) {
    const button = document.getElementById(`enter-button-${buttonId}`);

    fetch(`http://${API_IP}:${API_PORT}/projects/` + buttonId + '/enters', {
        method: 'POST',
        credentials: 'include',
    })
        .then(response => {
            if (response.status == 401) { // HTTP 상태 코드가 200 이상 299 미만이 아닐 경우
                alert('로그인을 해주세요.');
            } else if (!response.ok) {
                response.json().then(error => {
                    alert(error.message);
                });
            } else {
                alert('입장 신청되었습니다');
            }
        })
        .catch(error => {
            console.error(error);
        });

}

function getEnterStatus(buttonId, loginId) {

    fetch(`http://${API_IP}:${API_PORT}/projects/` + `${buttonId}` + '/members/' + `${loginId}`, {
        method: 'GET',
        credentials: 'include',
    })
        .then(response => {
            if (!response.ok) { // HTTP 상태 코드가 200 이상 299 미만이 아닐 경우
            } else {
                var button = document.getElementById(`enter-button-${buttonId}`);
                var detailButton = document.getElementById(`detail-button-${buttonId}`);
                detailButton.style.display = "none";
                
                const enterText = button.querySelector('.enter-text');
                button.innerHTML = '프로젝트로 이동';
                button.onclick = function () {
                    window.location.href = `project-detail.html?id=${buttonId}`;
                };
            }


            return response.json();
        })
        .then(data => {
        })
        .catch(error => console.error(error));

}