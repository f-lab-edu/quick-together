

document.getElementById('submit').addEventListener('click', () => {
    const sequence_priority = getCheckedValue('sequence_priority');

    const minute_unit = getCheckedValue('minute_unit');
    const marginal_minutes = getTextValue('marginal_minutes');
    const limit = getTextValue('limit');


    const planSettingRequest = {
        sequence_priority,
        minute_unit,
        marginal_minutes,
        limit
    };


    const data = JSON.stringify(planSettingRequest);
    console.log(data);
    let xhr = new XMLHttpRequest();
    xhr.open('POST', `${API_HOST}/planner/setting`);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.withCredentials = true;

    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) return;
        if (xhr.status === 201) {
            //if this page is planner-setting-on-boarding page, redirect to completion.html else planner-setting.html
            window.location.href = '/memebers/';
        } else {
            console.log(xhr.response, xhr.status)
        }
    }
    xhr.send(data);


});

function getCheckedValue(name) {
    const el = document.getElementsByName(name);
    const arrayHavingElement = Array.from(el)
        .find(item => item.checked)
        .value;
}

function getTextValue(id) {
    return document.getElementById(id)
        .value;
}
