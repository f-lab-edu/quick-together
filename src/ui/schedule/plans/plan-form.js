
document.addEventListener('DOMContentLoaded', renderCalendar);

const timezoneRadios = document.querySelectorAll('input[type="radio"][name="timezone"]');

let timeZone;

timezoneRadios.forEach(radio => {
    radio.addEventListener('change', () => {
        if (radio.checked) {
            timeZone = radio.value;
        }
        renderCalendar();
    })
})


function renderCalendar() {

    let calendarEl = document.getElementById('calendar');

    const timezoneList = document.getElementsByName('timezone');
    let timeZone = 'Asia/Seoul';
    for (let i = 0; i < timezoneList.length; i++) {
        if (timezoneList[i].checked) {
            timeZone = timezoneList[i].value;
        }
    }
    let calendar = new FullCalendar.Calendar(calendarEl, {
        timeZone: timeZone,
        //한국어 설정
        locale: 'ko',
        initialView: 'timeGridWeek',
        slotDuration: '00:10:00',
        slotLabelFormat: {
            hour: '2-digit',
            minute: '2-digit',
            omitZeroMinute: false,
            meridiem: 'short'
        },
        selectable: true,
        editable: true,
        allDaySlot: false,
        headerToolbar: {
            left: 'prev,today,next',
            center: 'title',
            right: '',
        },
        events: (fetchInfo, successCallback, failureCallback) => {
            const start = fetchInfo.startStr;
            const end = fetchInfo.endStr;
            const timeZone = fetchInfo.timeZone

            let xhr = new XMLHttpRequest();
            xhr.open('GET', `${API_HOST}/plans?from=${start}&to=${end}&timeZone=${timeZone}`)
            xhr.withCredentials = true;
            xhr.setRequestHeader('Content-type', 'application/json');

            xhr.onreadystatechange = () => {
                if (xhr.readyState !== XMLHttpRequest.DONE) return;
                if (xhr.status === 200) {
                    const events = JSON.parse(xhr.response);
                    successCallback(events);
                } else {
                    failureCallback(xhr.response);
                }
            }

            xhr.send();
        }

    });

    //Plan 생성
    calendar.on('select', function (info) {
        const start = info.startStr;
        const end = info.endStr;

        const startTime = start.substring(11, 16);
        const endTime = end.substring(11, 16);

        const endDateTime = new Date(end);
        const now = new Date();
        if (endDateTime < now) {
            alert("현재시간보다 이전의 시간은 선택할 수 없습니다.");
            return;
        }

        const title = prompt(startTime + '~' + endTime + ' : 일정 이름이 뭔가요?');
        if (title === null) {
            return;
        }

        const event = {
            event_name: title,
            start_date_time: start,
            end_date_time: end,
            time_zone: timeZone
        }

        const eventJson = JSON.stringify(event);
        const xhr = new XMLHttpRequest();
        xhr.open('POST', API_HOST + '/plans');
        xhr.withCredentials = true;
        xhr.setRequestHeader('Content-type', 'application/json');
        xhr.onreadystatechange = () => {
            if (xhr.readyState !== XMLHttpRequest.DONE) return;
            if (xhr.status === 200) {
                calendar.refetchEvents();
            } else {
                console.log(xhr.response);
            }
        }
        xhr.send(eventJson);
    });


    //Plan 수정 삭제
    calendar.on('eventMouseEnter', (info) => {
        const event = info.event;
        const eventEl = info.el;
        const eventId = event.id;
        const eventTitle = event.title;
        const eventStart = event.start;
        const eventEnd = event.end;
        const eventTimeZone = timeZone;

        const editButton = document.createElement('button');
        editButton.innerText = '수정';
        editButton.setAttribute('class', 'event-button edit-button');
        editButton.addEventListener('click', () => {
            const editEvent = {
                event_name: eventTitle,
                start_date_time: eventStart,
                end_date_time: eventEnd,
                time_zone: eventTimeZone,
            }

            const editEventJson = JSON.stringify(editEvent);
            const xhr = new XMLHttpRequest();
            xhr.open('PUT', `${API_HOST}/plans/${eventId}`);
            xhr.withCredentials = true;
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.onreadystatechange = () => {
                if (xhr.readyState !== XMLHttpRequest.DONE) return;
                if (xhr.status === 200) {
                    calendar.refetchEvents();
                } else {
                    console.log(xhr.response);
                }
            }
            xhr.send(editEventJson);
        });

        const deleteButton = document.createElement('button');
        deleteButton.innerText = '삭제';
        deleteButton.setAttribute('class', 'event-button delete-button');
        deleteButton.addEventListener('click', () => {
            const xhr = new XMLHttpRequest();
            xhr.open('DELETE', `${API_HOST}/plans/${eventId}`);
            xhr.withCredentials = true;
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.onreadystatechange = () => {
                if (xhr.readyState !== XMLHttpRequest.DONE) return;
                if (xhr.status === 200) {
                    calendar.refetchEvents();
                } else {
                    console.log(xhr.response);
                }
            }
            xhr.send();
        });

        const command = document.createElement('div');
        command.setAttribute('class', 'event-command');
        command.appendChild(editButton);
        command.appendChild(deleteButton);

        eventEl.appendChild(command);

    });

    calendar.on('eventMouseLeave', (info) => {
        const eventEl = info.el;
        const eventElChildren = eventEl.children
        const eventElChildrenLength = eventElChildren.length;

        for (let i = 0; i < eventElChildrenLength; i++) {
            if (eventElChildren[i].className === 'event-command') {
                eventEl.removeChild(eventElChildren[i]);
            }
        }

    });

    calendar.render();
}