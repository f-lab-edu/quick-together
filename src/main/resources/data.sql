    insert
    into
        project
        (create_date_time, member_id, meeting_method, period_date_time, project_description, project_summary, project_name, project_status, start_date_time, views, project_id)
    values
        ('2022-12-22T01:09:49.683354', '1', 'SLACK', '2022-12-22T01:09:49.683354', '<h1 style="border-width: 0px; border-style: solid; border-color: var(--chakra-colors-gray-200); overflow-wrap: break-word; margin-top: 0px; font-size: 28px; font-weight: 700; line-height: 1.3; color: rgb(59, 63, 78); font-family: &quot;Noto Sans KR&quot;, sans-serif; text-align: start; background-color: rgb(247, 247, 251);"><span style="border-width: 0px; border-style: solid; border-color: var(--chakra-colors-gray-200); overflow-wrap: break-word;">좋은 회사 타이틀뿐 아니라</span><span style="border-width: 0px; border-style: solid; border-color: var(--chakra-colors-gray-200); overflow-wrap: break-word;">&nbsp;"실력"까지 검증받은</span><p style="border-width: 0px; border-style: solid; border-color: var(--chakra-colors-gray-200); overflow-wrap: break-word; margin-bottom: 24px;">개발자와 깊이있게 성장합니다.</p></h1><p style="border-width: 0px; border-style: solid; border-color: var(--chakra-colors-gray-200); overflow-wrap: break-word; margin-bottom: 52px; font-size: medium; color: rgb(59, 63, 78); font-family: &quot;Noto Sans KR&quot;, sans-serif; background-color: rgb(247, 247, 251);">개발자가 부족해지며 네카라쿠배 등 네임밸류있는 회사들의 채용의 문턱도 낮아졌습니다. 그리고 경력은 실력과 비례하지 않기 때문에 좋은 회사를 오래 다녔더라도 실력있는 시니어가 아닐 수 있습니다.<br style="border-width: 0px; border-style: solid; border-color: var(--chakra-colors-gray-200); overflow-wrap: break-word;"><br style="border-width: 0px; border-style: solid; border-color: var(--chakra-colors-gray-200); overflow-wrap: break-word;">개발경험이 부족한 사람들은 교육자를 평가할 때 경력이나 회사 타이틀밖에 볼 수가 없지만, F-Lab에서는 개발자의 실력까지 직접 엄격하게 검증하기 때문에 타이틀만 화려한 개발자가 아닌 정말 실력있는 상위권 개발자만 멘토로 만나실 수 있습니다.<br style="border-width: 0px; border-style: solid; border-color: var(--chakra-colors-gray-200); overflow-wrap: break-word;"><br style="border-width: 0px; border-style: solid; border-color: var(--chakra-colors-gray-200); overflow-wrap: break-word;"><strong style="border-width: 0px; border-style: solid; border-color: var(--chakra-colors-gray-200); overflow-wrap: break-word;">실제로 네카라쿠배 출신 400명의 개발자들이 멘토로 지원했지만, 그 중 100명만이 멘토로 함께할 수 있었습니다. (2023년 1월 기준)</strong><br style="border-width: 0px; border-style: solid; border-color: var(--chakra-colors-gray-200); overflow-wrap: break-word;"><br style="border-width: 0px; border-style: solid; border-color: var(--chakra-colors-gray-200); overflow-wrap: break-word;">F-Lab은 앞으로도 "멘토의 실력"만큼은 최우선 가치로 삼아 절대 타협하지 않고 엄격하게 검증해나갈 것입니다.</p>', '자바 백엔드 프로젝트', '다 함께', 'OPEN', '2022-12-22T01:09:49.683354', '0', '51');


    insert
    into
        project
        (create_date_time, member_id, meeting_method, period_date_time, project_description, project_summary, project_name, project_status, start_date_time, views, project_id)
    values
        ('2022-12-22T01:09:49.683354', '2', 'SLACK', '2022-12-22T01:09:49.683354', '인원은 6명으로 자바스크립트 프로젝트 입니다.', '자바스크립트 프로젝트', '모두와', 'OPEN', '2022-12-22T01:09:49.683354', '0', '52');


    insert
    into
        participant
        (member_id, participant_role, project_id, participant_id)
    values
        (1, 'ROLE_ADMIN', 51, 41);

    insert
    into
        participant
        (member_id, participant_role, project_id, participant_id)
    values
        (2, 'ROLE_USER', 51, 42);

    insert
    into
        participant
        (member_id, participant_role, project_id, participant_id)
    values
        (2, 'ROLE_ADMIN', 52, 43);


    insert
    into
        project_skill_stack
        (project_id, skill_stacks)
    values
        (51, 'JAVA');

    insert
    into
        project_skill_stack
        (project_id, skill_stacks)
    values
        (51, 'SPRING');

    insert
    into
        project_recruitment_position
        (project_id, recruitment_positions)
    values
        (51, 'FRONTEND');

    insert
    into
        project_recruitment_position
        (project_id, recruitment_positions)
    values
        (51, 'DESIGNER');

    insert
    into
        participant_position
        (participant_id, positions)
    values
        (41, 'BACKEND');

    insert
    into
        participant_position
        (participant_id, positions)
    values
        (42, 'FRONTEND');


    insert
    into
        participant_skill_stack
        (participant_id, skill_stacks)
    values
        (41, 'SPRING');