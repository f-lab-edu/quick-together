function sendMessage() {

    const content = document.getElementById('messageContent');
    console.log(window.memberId);
    console.log(window.memberName);

    const PostRequest = {
      memberId: memberId,
      memberName: memberName,
      content: content.value,
      projectId: projectId
  };

  var headers = {
    'header1': 'value1',
    'header2': 'value2',

  };

  stompClient.send('/app/chat', {}, JSON.stringify(PostRequest));

}


