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


fetch(`http://${API_IP}:${API_PORT}/projects/` + projectId, {
  method: 'GET',
  headers: {
    'Content-Type': 'application/json'
    //'withCredentials': 'true'
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
    console.log(project.createDateTime);

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

  });


