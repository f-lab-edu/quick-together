// 초대장 리스트 출력
fetch(`http://${API_IP}:${API_PORT}/invites`, {
    method: 'GET',
    credentials: 'include',
    headers: {
        'Content-Type': 'application/json',
    },
})
    .then(response => {
        if (response.status == 401) {
            alert('로그인을 해주세요.');
        } else if (!response.ok) {
            response.json().then(error => {
                alert(error.message);
            });
        } else {
            return response.json();
        }
    })
    .then(invites => {

        const inviteTag = document.querySelector('#invite-print');
        let inviteStr = '';

        invites.data.forEach(invite => {
            //console.log(invite.projectName + ' 프로젝트에 초대되었습니다.');
            inviteStr += `
                    <div id="invitation-${invite.projectId}" class="invitation" >
                        <h1>초대장</h1>
                        <h2>보낸사람</h2>
                        <p>${invite.requestMemberName}</p>
                        <h2>메시지</h2>
                        <p>${invite.projectName} : 해당 프로젝트에 초대 되었습니다.</p>
                        <span onclick="accept(${invite.projectId})" id="invite-button" value="${invite.projectId}">수락 하기</span>
                    </div>`;

        });
        inviteTag.innerHTML += inviteStr;
    })
    .catch(error => console.error(error));

    // 초대 수락
function accept(projectId) {

    fetch(`http://${API_IP}:${API_PORT}/projects/` + projectId + '/members/invites', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (response.status == 401) {
                alert('로그인을 해주세요.');
            } else if (!response.ok) {
                response.json().then(error => {
                    alert(error.message);
                });
            } else {

                alert('초대를 수락하였습니다.')
                const targetInvite = document.querySelector(`#invitation-${projectId}`);
                targetInvite.remove();

            }
        })
        .catch(error => console.error(error));

}