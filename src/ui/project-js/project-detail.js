const urlParams = new URLSearchParams(window.location.search);
const projectId = urlParams.get('id');
console.log(projectId);

function dateFormat(datetime) {
  const date = new Date(datetime);
  const formattedDate = date.toLocaleString('ko-KR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
    hour12: true
  });
  return formattedDate;
}


fetch(`http://${API_IP}:${API_PORT}/projects/` + projectId + '/detail', {
  method: 'GET',
  credentials: 'include',
  headers: {
    'Content-Type': 'application/json'
  },
})
  .then(response => {
    if (response.status == 401) { // HTTP 상태 코드가 200 이상 299 미만이 아닐 경우
      alert('로그인을 해주세요.');
    }
    return response.json()
  })
  .then(project => {

    const projectName = document.getElementById('blog-post-title');
    projectName.textContent = project.projectName;

    const founderName = document.getElementById('founderName');
    founderName.textContent = project.founderName;

    const summary = document.getElementById('postSummary');
    summary.textContent = project.projectDescriptionInfo.projectSummary;

    const description = document.getElementById('postDescription');
    //description.textContent = project.projectDescriptionInfo.projectDescription;
    description.innerHTML = project.projectDescriptionInfo.projectDescription;


    const startDateTime = dateFormat(project.startDateTime);
    const periodDateTime = dateFormat(project.periodDateTime);
    const createDateTime = dateFormat(project.createDateTime);

    const createTime = document.getElementById('blog-post-meta');
    createTime.textContent = createDateTime;

    //프로젝트 스킬스택 출력
    const projectSkillStacks = document.getElementById('projectSkillStacks');
    let tempSkillStacks = '';

    project.projectSkillStacks.forEach(stack => {
      tempSkillStacks += stack + ' ';

    });

    projectSkillStacks.textContent = tempSkillStacks;


    //프로젝트 구인 포지션 출력
    const projectRecruitPostions = document.getElementById('projectRecruitPostions');
    let tempRecruitPostions = '';

    project.recruitPositions.forEach(postion => {
      tempRecruitPostions += postion + ' ';

    });


    projectRecruitPostions.textContent = tempRecruitPostions;



    const participants = document.getElementById('participantsLocation');

    let addPaticipant = `
    `;


    // 구성원 출력
    const members = project.participants;
    members.forEach(member => {
      addPaticipant += `
    <div style="display: flex">
    `;

      let memberNameStr = '';
      let positionNameStr = '';
      let tempPostion = '';

      const memberName = member.memberName;
      memberNameStr += `
      <div style="flex : 1">
        <p id="participantName" class="mb-0">${memberName}</p>
      </div>`;


      member.positions.forEach(postion => {
        tempPostion += postion + ' ';
      });
      positionNameStr = `
      <div style="flex : 2; margin-left: 15">
        <p id="participantPostion" class="mb-0">${tempPostion}</p>
      </div>`;


      addPaticipant += memberNameStr;
      addPaticipant += positionNameStr;
      addPaticipant += `
    </div >
    `;

    });

    participants.innerHTML += addPaticipant;



    //post 출력
    const projectPosts = project.posts;
    const container = document.querySelector('.chatbox');

    fetch(`http://${API_IP}:${API_PORT}/login`, {
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
        projectPosts.forEach(post => {
          let createPostDateTime = dateFormat(post.createDateTime);
          let div = document.createElement('div');
    
          if (allmemberName == post.memberName) {
            div.style.backgroundColor = '#c4e8ed'; 
          }
    
          div.className = 'message';
          div.innerHTML = `
              <span class="name">${post.memberName}:</span>
              <span class="text">${post.content}</span>
              <span class="time">${createPostDateTime}</span>
              `;
          container.appendChild(div);
        });
        return data;
    })
    .catch(error => {
        console.error('Error:', error);
        return false;
    });




    container.scrollTop = container.scrollHeight;

  });