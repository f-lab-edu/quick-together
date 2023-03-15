 //Meeting-create-form 제출시
  document.querySelector('#create-meeting').addEventListener('click', () => {
    const meeting = {
      title: document.querySelector('#meeting-title').value,
      description: document.querySelector('#meeting-description').value,
      suggested_time: {
          start_date_time: document.querySelector('#meeting-start-date-time').value,
          end_date_time: document.querySelector('#meeting-end-date-time').value
        }
    };
    const projectId = window.location.pathname.split('/')[2];

    const xhr = new XMLHttpRequest();
    xhr.open('POST', `${API_HOST}/projects/${projectId}/meetings`);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.withCredentials= true;
    xhr.onreadystatechange = () => {
        if(xhr.readyState!== XMLHttpRequest.DONE) return;
        if(xhr.status === 200) {
            const meetingId = JSON.parse(xhr.response).id;
            window.location.href = `${UI_HOST}/projects/${projectId}/meetings/${meetingId}`;
        } else{
            console.log(xhr.response);
        }
    }
    xhr.send(JSON.stringify(meeting));
  });


