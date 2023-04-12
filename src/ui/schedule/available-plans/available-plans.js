function createPlan(event) {
    let planArea = document.getElementsByClassName("plan-area")[0]
    let element = document.createElement('div')


    //관리하기 위해서 id 생성
    const id = 'plan-card-' + new Date().getTime();
    element.setAttribute('id', id)
    element.setAttribute('class', 'plan-card')
    const dayOfWeek = event.target.value
    element.innerHTML = `
            <div class="day-of-week" name="day-of-week">${dayOfWeek}</div>
            <input type="button" class="time-picker button-close" onclick="closePlan('${id}')" value="x">
            <div class="range-picker">
                <input type="time" name="start-time" class="time-picker" required/>~
                <input type="time" name="end-time" class="time-picker" required/>
            </div> 
            `
    planArea.appendChild(element)

    //스크롤 이동
    const addedDiv = document.getElementById(id)
    const addedScrollPosition = addedDiv.offsetTop

    window.scrollTo({
        top: addedScrollPosition,
        behavior: 'smooth'
    })
}
//plan card 닫기
function closePlan(id) {
    let plan = document.getElementById(id)
    if (plan != null) {
        plan.remove()
    }

}

function registAblePlan() {

    //timezone 가져오기
    const timezoneList = document.getElementsByName('timezone')
    const timezone = getRadioCheckedValue(timezoneList);

    //RelativeTimeBlock 가져오기
    const dayOfWeekList = document.getElementsByName("day-of-week")
    const startTimeList = document.getElementsByName('start-time')
    const endTimeList = document.getElementsByName('end-time')

    const dayMap = new Map([
        ['월', 'MONDAY'],
        ['화', 'TUESDAY'],
        ['수', 'WEDNESDAY'],
        ['목', 'THURSDAY'],
        ['금', 'FRIDAY'],
        ['토', 'SATURDAY'],
        ['일', 'SUNDAY']
    ]);

    const availablePlans = [];
    for (let i = 0; i < dayOfWeekList.length; i++) {
        const dayOfWeek = dayMap.get(dayOfWeekList[i].innerText)
        const startTime = startTimeList[i].value
        const endTime = endTimeList[i].value

        availablePlans.push({
            day_of_week: dayOfWeek,
            start_time: startTime,
            end_time: endTime
        })
    }

    const availablePlanRequestDto = {
        time_zone: timezone,
        available_plans: availablePlans
    }

    const data = JSON.stringify(availablePlanRequestDto)

    let xhr = new XMLHttpRequest();
    xhr.open('PUT', `${API_HOST}/available-plans/`)
    xhr.setRequestHeader('Content-type', 'application/json')
    xhr.withCredentials = true;


    xhr.onreadystatechange = () => {
        if (xhr.readyState !== XMLHttpRequest.DONE) return;
        if (xhr.status === 201) {
            window.location.href = `/schedule/plans/plan-form-on-boarding.html`;
        } else {
            console.log(xhr.response, xhr.status)
        }
    }
    xhr.send(data);
}

function getRadioCheckedValue(timezoneList) {
    let checkedValue = null;
    for (let i = 0; i < timezoneList.length; i++) {
        if (timezoneList[i].checked == true) {
            checkedValue = timezoneList[i].value
            break
        }
    }
    return checkedValue;
}