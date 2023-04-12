function leaveProject() {

  console.log(window.memberId);
  console.log(window.memberName);

  fetch(`http://${API_IP}:${API_PORT}/projects/` + projectId + '/members', {
    method: 'DELETE',
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json',
    },
  })
    .then(response => {
      if (!response.ok) {
        response.json().then(error => {
          alert(error.message);
        });
      } else {
        alert('탈퇴하였습니다.');
        window.location.href = 'index.html';
      }
    })
    .catch(error => console.error(error));
}
