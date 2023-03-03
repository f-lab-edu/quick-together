    insert
    into
        project
        (create_date_time, member_id, meeting_method, period_date_time, project_description, project_summary, project_name, project_status, start_date_time, views, project_id)
    values
        ('2022-12-22T01:09:49.683354', '1', 'SLACK', '2022-12-22T01:09:49.683354', '인원은 4명으로 자바 프로젝트 입니다.', '자바 백엔드 프로젝트', '다 함께', 'OPEN', '2022-12-22T01:09:49.683354', '0', '1');


    insert
    into
        project
        (create_date_time, member_id, meeting_method, period_date_time, project_description, project_summary, project_name, project_status, start_date_time, views, project_id)
    values
        ('2022-12-22T01:09:49.683354', '2', 'SLACK', '2022-12-22T01:09:49.683354', '인원은 6명으로 자바스크립트 프로젝트 입니다.', '자바스크립트 프로젝트', '모두와', 'OPEN', '2022-12-22T01:09:49.683354', '0', '2');


    insert
    into
        participant
        (member_id, participant_role, project_id, participant_id)
    values
        (1, 'ROLE_ADMIN', 1, 1);

    insert
    into
        participant
        (member_id, participant_role, project_id, participant_id)
    values
        (2, 'ROLE_USER', 1, 2);

    insert
    into
        participant
        (member_id, participant_role, project_id, participant_id)
    values
        (2, 'ROLE_ADMIN', 2, 3);