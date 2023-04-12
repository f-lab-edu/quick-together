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

function draw(loginId) {
    fetch(`http://${API_IP}:${API_PORT}/projects`, {
        method: 'GET',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => response.json())
        .then(projects => {
            const someDiv = document.querySelector('#some-div');



            let projectsString = `
                            <section class="pt-4">
                            <div class="container px-lg-5">
                            <div class="row gx-lg-5">
                            `;




            for (let i = 0; i < projects.data.length; i++) {

                const startDateTime = dateFormat(projects.data[i].startDateTime);
                const periodDateTime = dateFormat(projects.data[i].periodDateTime);

                let projectTemplate = `

                <div class="col-lg-6 col-xxl-4 mb-5">
                    <div class="card bg-light border-2 h-100">
                        <div class="card-body text-center p-4 p-lg-5 pt-0 pt-lg-0">
                            <div class="feature bg-primary bg-gradient text-white rounded-3 mb-4 mt-n4"><i class="bi bi-cloud-download"></i></div>
                            <div class="container">

                                <h2 style="font-size: 1.5em;" class="projectName fw-bold">${projects.data[i].projectName}</h2>
                                <span style="text-align: right"> <img src="img/teamLeader.png"; style = "width: 25px; height: 25px"; alt="My Image"><i class="bi bi-person-circle"></i> ${projects.data[i].founder}</p> </span>

                            <p class="description mb-3">${projects.data[i].projectDescriptionInfo}</p>
                            <a href="project-preview.html?id=${projects.data[i].projectId}" id="detail-button-${projects.data[i].projectId}" data-id="${projects.data[i].projectId}" class="detail-button btn btn-primary me-3 mb-3">자세히</a>
                            <button style="font-size: 0.8em;" onclick="toggleEnter(${projects.data[i].projectId})" id="enter-button-${projects.data[i].projectId}" class="btn btn-outline-primary me-3 mb-3" data-id="${projects.data[i].projectId}"><i class="bi bi-door-open"></i><span class="enter-text">입장신청</span></button>
                            `;

                projectTemplate += `<button onclick="toggleLike(${projects.data[i].projectId})" id="like-button-${projects.data[i].projectId}" class="like-button btn btn-outline-primary me-3 mb-3" data-id="${projects.data[i].projectId}"><i class="bi bi-hand-thumbs-up"></i> <span class="like-text">좋아요</span><span class="like-icon">❤️</span></button>`;
                projectTemplate += `

                        </div >
                        <p class="mb-1"><img src="img/icon_view.png" style="width: 20px; height: 20px" alt="My Image"><i class="bi bi-eye ">  ${projects.data[i].views}</i>
                            &nbsp; <i class="bi bi-hand-thumbs-up" >
                                <i style="font-size: 0.7em;">❤️</i> 
                                <span id="like-text-${projects.data[i].projectId}">${projects.data[i].likes}</span>
                                </i>
                        </p>

                        <p class="mb-1"><i class="bi bi-calendar"></i><span class="datetime">${startDateTime} ~ ${periodDateTime}</span></p>
                    </div >
                </div >`
                    ;
                //console.log('proejctId : ' + projects.data[i].projectId);
                //getLikedStatus(projects.data[i].projectId);
                //projectsString += projectTemplate;
                someDiv.innerHTML += projectTemplate;
                if (loginId != 0) {
                    getLikedStatus(projects.data[i].projectId);
                    getEnterStatus(projects.data[i].projectId, loginId);
                }
            }



            projectsString += `
                        </div >
                        </div >
                        </section >
                        `;

            someDiv.innerHTML += projectsString;

        });
}