let xhr = new XMLHttpRequest();
xhr.open('GET', `${window.API_HOST}/planner/setting`);
xhr.withCredentials = true;
xhr.onreadystatechange = () => {
    if (xhr.readyState === XMLHttpRequest.DONE) return;
    if (xhr.status === 200) {
        const planSetting = JSON.parse(xhr.response);
        const { sequence_priority, minute_unit, marginal_minutes, limit } = planSetting;
        if (sequence_priority === 'FAST') {
            sequence_priority = '빠른순으로';
        } else if (sequence_priority === 'SLOW') {
            sequence_priority = '느린순으로';
        }

        const settingHtml =
            `
<div class="page-description">
    <h1 class="page-title">나의 스케줄 설정</h1>
</div>
<div class="signup-content">
    <div class="signup-form">
        <div class="title-section">
            <h2 class="form-title">스케줄 우선순위</h2><br>
            <div class="form-group">
                <label class="radio-block">
                    <div id="sequence_priority" class="form-text sequence_priority">${sequence_priority}</div>
                </label>
            </div>
        </div>
        <div class="title-section">
            <h2 class="form-title">분단위</h2>
            <div class="form-group">
                <label class="radio-block">
                    <div id="minute_unit" class="form-text minute_unit">${minute_unit} 분</div>
                </label>
            </div>
        </div>
        <div class="title-section">
            <h2 class="form-title">여분시간</h2>
            <div class="form-group">
                <label class="radio-block">
                    <div id="marginal_minutes" class="form-text marginal_minutes">${marginal_minutes} 분
                    </div>
                </label>
            </div>
        </div>
        <div class="title-section">
            <h2 class="form-title">추천 최대개수</h2>
            <div class="form-group">
                <label class="radio-block">
                    <div id="limit" class="form-text limit">${limit} 개</div>
                </label>
            </div>
        </div><br>
        <button type="submit" id="update" class="submit-able-plan form-submit">수정하기</button>
    </div>
</div>
`
        document.querySelector('.planner-setting').innerHTML = settingHtml;
    } else {
        console.log(xhr.response, xhr.status)
    }
}
