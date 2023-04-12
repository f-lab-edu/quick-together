//import './css/header-login.css?type=text/css';

const headerTemplate = `
<h1 id="main-name" onclick="location.href = 'index.html';">Quick Together</h1>
<nav id="login-nav">
    <ul>
        <li><a style="color:#fff;" href="index.html">Home</a></li>
        <li><a href="#">내 프로젝트</a></li>
        <li><a href="schedule-plans.html">일정 관리</a></li>
        <li><a href="project-write.html">프로젝트 생성</a></li>
        <li><a href="invitation.html">초대장</a></li>
    </ul>
    <div id="login" class="login">
        <input id="login-input" type="submit" value="Login">
    </div>
    <div id="logout" class="login">
        
    </div>
</nav>

`;

export default headerTemplate;