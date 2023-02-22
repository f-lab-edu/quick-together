package com.flab.quicktogether.alarm.kakao;

import com.flab.quicktogether.alarm.service.AlarmSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class KaKaoAlarmSendService implements AlarmSendService<HttpEntity> {

    @Autowired
    private RestTemplate restTemplate;


    private static final String MSG_SEND_SERVICE_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

    @Override
    public void sendAlarm(String token, HttpEntity message) {
        ResponseEntity<String> response = httpRequest(MSG_SEND_SERVICE_URL, HttpMethod.POST, message);
        String body = response.getBody();
        log.info(body);

    }

    public ResponseEntity<String> httpRequest(String url, HttpMethod method, HttpEntity<?> entity){
        return restTemplate.exchange(url, method, entity,String.class);
    }

}
