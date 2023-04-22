
//입장 신청 리스트
fetch(`http://${API_IP}:${API_PORT}/projects/` + projectId + '/members/enters', {
    method: 'GET',
    credentials: 'include',
    headers: {
        'Content-Type': 'application/json',
    },
})
    .then(response => {
        if (response.status == 401) {
            console.log('로그인을 해주세요');
        } else if (!response.ok) {
            response.json().then(error => {
                alert(error.message);
            });
        } else {
            return response.json();
        }
    })
    .then(enters => {

        const enterTag = document.querySelector('#enter-print');
        let enterStr = '';

        enters.data.forEach(enter => {
            //console.log(enter.projectName + ' 프로젝트에 초대되었습니다.');
            enterStr += `
                    <div id="enter-${enter.projectId}" class="enter" >
                        <span>${enter.enterMemberName}</span>
                        <button id="enter-button" style="border-radius: 5px;" onclick="acceptEnter(${enter.projectId}, ${enter.enterMemberId})" value="${enter.enterMemberName}">수락 하기</button>
                    </div>`;

        });
        enterTag.innerHTML += enterStr;
        
    })
    .catch(error => console.error(error));

/*<div id="enter-${enter.projectId}" class="enter" >
    <h1>입장 신청</h1>
    <h2>신청 멤버</h2>
    <p>${enter.enterMemberName}</p>
    <h2>메시지</h2>
    <p>해당 프로젝트에 입장 신청하였습니다.</p>
    <span onclick="acceptEnter(${enter.projectId}, ${enter.enterMemberId})" id="enter-button" value="${enter.projectId}">수락 하기</span>
</div>*/

// 입장수락
function acceptEnter(projectId, memberId) {
    enterMessage = {
        'enterMemberId': memberId,
    };
    fetch(`http://${API_IP}:${API_PORT}/projects/` + projectId + '/members/enters', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(enterMessage)
    })
        .then(response => {
            if (response.status == 401) {
                alert('로그인을 해주세요.');
            } else if (!response.ok) {
                response.json().then(error => {
                    alert(error.message);
                });
            } else {

                alert('입장 신청 수락되었습니다.')
                var enterButton = document.querySelector(`#enter-button`);

                const targetEnter = document.querySelector(`#enter-${projectId}`);
                targetEnter.remove();


                const participants = document.getElementById('participantsLocation');

                let addPaticipant = `
                `;
            
                // 구성원 출력
                
                  addPaticipant += `
                <div style="display: flex">
                `;
            
                  let memberNameStr = '';
                  let positionNameStr = '';
                  let tempPostion = '';
            
                  memberNameStr += `
                  <div style="flex : 1">
                    <p id="participantName" class="mb-0">${enterButton.value}</p>
                  </div>`;
            
            
                  /*member.positions.forEach(postion => {
                    tempPostion += postion + ' ';
                  });
                  positionNameStr = `
                  <div style="flex : 2; margin-left: 15">
                    <p id="participantPostion" class="mb-0">${tempPostion}</p>
                  </div>`;*/
            
            
                  addPaticipant += memberNameStr;
                  addPaticipant += positionNameStr;
                  addPaticipant += `
                </div >
                `;
            
            
                participants.innerHTML += addPaticipant;

            }
        })
        .catch(error => console.error(error));

}