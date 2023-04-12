function getLikedStatus(buttonId) {
    const button = document.getElementById(`like-button-${buttonId}`);

    const id = button.dataset.id;
    //console.log(id);
    fetch(`http://${API_IP}:${API_PORT}/me/` + `${buttonId}` + '/likes', {
        method: 'GET',
        credentials: 'include',
    })
        .then(response => {
            return response.json();
        })
        .then(data => {
            // Process the data here
            if (data.liked) {
                changeLike(buttonId);
            }
        })
        .catch(error => console.error(error));

}


function changeLike(buttonId) {
    const button = document.getElementById(`like-button-${buttonId}`);
    const likeIcon = button.querySelector('.like-icon');
    const likeText = button.querySelector('.like-text');

    if (button.classList.contains('liked')) {
        button.classList.remove('liked');
        likeIcon.innerText = '❤️';
        likeText.innerText = '좋아요';
    } else {
        button.classList.add('liked');
        likeIcon.innerText = '💔';
        likeText.innerText = '좋아요 취소';
    }
}

function toggleLike(buttonId) {
    const button = document.getElementById(`like-button-${buttonId}`);

    // 좋아요 상태를 서버에 전송
    sendLike(buttonId, button.classList.contains('liked'));
}

function sendLike(buttonId, isLiked) {
    // 서버로 좋아요 상태 전송하는 로직 구현

    if (!isLiked) {
        fetch(`http://${API_IP}:${API_PORT}/projects/` + buttonId + '/likes', {
            method: 'POST',
            credentials: 'include',
        })
            .then(response => {
                if (!response.ok) {
                    alert('로그인을 해주세요.');
                } else {
                    changeLike(buttonId);
                    const likeText = document.getElementById(`like-text-${buttonId}`);
                    likeText.textContent = Number(likeText.textContent) + 1;
                }
                return response.json()
            }
            )
            .then(data => {
                // Process the data here
            })
            .catch(error => console.error(error));
    } else {

        fetch(`http://${API_IP}:${API_PORT}/projects/` + buttonId + '/likes', {
            method: 'Delete',
            credentials: 'include',
        })
            .then(response => {
                if (!response.ok) {
                    alert('로그인을 해주세요.');
                } else {
                    changeLike(buttonId);
                    const likeText = document.getElementById(`like-text-${buttonId}`);
                    likeText.textContent = Number(likeText.textContent) - 1;
                }
                return response.json()
            }
            )
            .then(data => {
                // Process the data here
            })
            .catch(error => console.error(error));
    }

}