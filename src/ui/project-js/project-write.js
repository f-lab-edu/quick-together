const someDiv = document.querySelector('#skillStacks');

let skills = [
    "JAVA",
    "KOTLIN",
    "SPRING",
    "JPA",
    "JAVA_SCRIPT",
    "NODE_JS",
    "EXPRESS_JS",
    "NEST_JS",
    "REACT_JS",
    "VUE_JS",
    "SVELTE_JS",
    "ANGULAR_JS",
    "PYTHON",
    "DJANGO",
    "FLASK",
    "TENSOR_FLOW"
  ];

let addSkills = '';

skills.forEach(function(skill) {
    addSkills += `
    <input type="checkbox" id="checkbox1" name="skill-stack" value="${skill}">
    <label for="checkbox1">${skill}</label>
    `;
  });

someDiv.innerHTML = addSkills;

//<input type="checkbox" id="checkbox1" name="skill" value="HTML">
//<label for="checkbox1">HTML</label>




const form = document.querySelector('#myForm');
let project = '';

form.addEventListener('submit', function (event) {
    event.preventDefault();


    let projectName = document.getElementById('projectName');
    let projectSummary = document.getElementById('projectSummary');
    let projectDescription = $('#myEditor').summernote('code');

    const checkedSkills = document.querySelectorAll('input[name="skill-stack"]:checked');
    const checkedSkillValues = [];
  
    checkedSkills.forEach(function(skill) {
        checkedSkillValues.push(skill.value);
    });
    console.log(checkedSkillValues);

    const meetingMethod = document.querySelector('input[name="meeting-method"]:checked');

    var startDateStr = $('#start-date').datepicker('getDate').toISOString().slice(0, 10) + 'T12:00:00';
    var endDateStr = $('#end-date').datepicker('getDate').toISOString().slice(0, 10) + 'T12:00:00';

    project = {
        'memberId': window.memberId,
        'projectName': projectName.value,
        'projectSummary': projectSummary.value,
        'projectDescription': projectDescription,
        'skillStacks': checkedSkillValues,
        'meetingMethod': meetingMethod.value,
        'startDateTime': startDateStr,
        'periodDateTime': endDateStr,
    };

    console.log(project);


    fetch(`http://${API_IP}:${API_PORT}/projects`, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(project)
    })
        .then(response => {
            if (response.status == 401) {
                alert('로그인을 해주세요.');
            } else if (!response.ok) {
                response.json().then(error => {
                    alert(error.message);
                });
            } else {
                alert('프로젝트가 생성되었습니다.');
                window.location.href = 'index.html';
            }
        })
        .catch(error => console.error(error));

});