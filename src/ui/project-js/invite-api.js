    // 멤버 정보를 가져와 select 태그에 추가하는 함수
    function getMembers() {


        fetch(`http://${API_IP}:${API_PORT}/members`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
            /*body: JSON.stringify(invite)*/
        })
            .then(response => {
                if (response.status == 401) {
                    alert('로그인을 해주세요.');
                } else if (!response.ok) {
                    alert('잠시 후에 재시도 해주세요.');
                } else {
                    //alert('초대하였습니다.');
                }
                return response.json();
            })
            .then(dataArray => {
                const select = $("#members");
                select.empty();

                dataArray.data.forEach(function (member) {
                    const option = $("<option>").text(member.memberName).val(member.memberId);
                    select.append(option);
                });
            })
            .catch(error => console.error(error));


    }

    // 팝업을 열기 위한 함수
    function openPopup() {
        $(".popup").css("display", "block");
    }

    // 팝업을 닫기 위한 함수
    function closePopup() {
        $(".popup").css("display", "none");
    }

    // 멤버 초대 버튼에 클릭 이벤트를 추가함
    $("#inviteBtn").click(function () {
        // 멤버 정보를 가져와 select 태그에 추가함
        getMembers();
        // 팝업을 열음
        openPopup();
    });

    // 팝업에서 닫기 버튼에 클릭 이벤트를 추가함
    $(".close").click(function () {
        // 팝업을 닫음
        closePopup();
    });

    // 팝업에서 초대 버튼에 클릭 이벤트를 추가함
    $("#invite").click(function () {
        // 선택한 멤버의 ID를 가져옴
        //const memberId = $("#members").val();
        //console.log(memberId + " invite");

        var inviteTextBox = document.getElementById("inviteTextBox");
        var invitedMemberName = inviteTextBox.value;
        //console.log(invitedMemberName + " invite2");

        invite = {
            'invitedMemberName': invitedMemberName,
        };

        fetch(`http://${API_IP}:${API_PORT}/projects/` + projectId + '/invitesMemberName', {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(invite)
        })
            .then(response => {
                if (!response.ok) {
                    response.json().then(error => {
                        alert(error.message);
                    });
                    closePopup();
                } else {
                    alert('초대하였습니다.');
                    closePopup();
                }
            })
            .catch(error => console.error(error));

    });

/*function inviteMember() {
    invite = {
        'invitedMemberId': invitedMemberId,
    };

    fetch(`http://${API_IP}:${API_PORT}/projects/` + projectId + '/invites', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(invite)
    })
        .then(response => {
            if (response.status == 401) {
                alert('로그인을 해주세요.');
            } else if (!response.ok) {
                response.json().then(error => {
                    alert(error.message);
                });
            } else {
                alert('초대하였습니다.');
            }
        })
        .catch(error => console.error(error));

}*/
