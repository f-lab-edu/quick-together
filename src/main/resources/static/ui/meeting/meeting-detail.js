//Meeting-detail-form 조회시
    let meetingDetail = document.querySelector('#meeting-detail');
    const projectId = window.location.pathname.split('/')[2];
    const meetingId = window.location.pathname.split('/')[4];
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `${API_HOST}/projects/${projectId}/meetings/${meetingId}`);
    xhr.withCredentials = true;
    xhr.onreadystatechange = () => {
        if(xhr.readyState !== XMLHttpRequest.DONE) return;
        if(xhr.status === 200) {
            const meeting = JSON.parse(xhr.response);


        } else {
            console.log(xhr.response);
        }
    }
    xhr.send();
