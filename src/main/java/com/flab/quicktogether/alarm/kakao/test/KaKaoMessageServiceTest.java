package com.flab.quicktogether.alarm.kakao.test;

import com.flab.quicktogether.alarm.kakao.KaKaoSimpleMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class KaKaoMessageServiceTest {
    private static final String MSG_SEND_SERVICE_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
    private static final String SEND_SUCCESS_MSG = "메시지 전송에 성공했습니다.";
    private static final String SEND_FAIL_MSG = "메시지 전송에 실패했습니다.";

    private static final String SUCCESS_CODE = "0"; //kakao api에서 return해주는 success code 값

    public boolean sendMessage(String accessToken) {
        KaKaoSimpleMessageDto msgDto = new KaKaoSimpleMessageDto();
        msgDto.setBtnTitle("자세히보기");
        msgDto.setMobileUrl("");
        msgDto.setObjType("text");
        msgDto.setWebUrl("");
        msgDto.setText("메시지 테스트입니다.");



        JSONObject linkObj = new JSONObject();
        linkObj.put("web_url", msgDto.getWebUrl());
        linkObj.put("mobile_web_url", msgDto.getMobileUrl());

        JSONObject templateObj = new JSONObject();
        templateObj.put("object_type", msgDto.getObjType());
        templateObj.put("text", msgDto.getText());
        templateObj.put("link", linkObj);
        templateObj.put("button_title", msgDto.getBtnTitle());


        HttpHeaders header = new HttpHeaders();
        header.set("Content-Type", "application/x-www-form-urlencoded");
        header.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("template_object", templateObj.toString());

        HttpEntity<?> messageRequestEntity = httpClientEntity(header, parameters);

        ResponseEntity<String> response = httpRequest(MSG_SEND_SERVICE_URL, HttpMethod.POST, messageRequestEntity);
        String body = response.getBody();
        log.info(body);

        return true;
    }


    public HttpEntity<?> httpClientEntity(HttpHeaders header, Object params) {
        HttpHeaders requestHeaders = header;

        if (params == null || "".equals(params))
            return new HttpEntity<>(requestHeaders);
        else
            return new HttpEntity<>(params, requestHeaders);
    }

    public ResponseEntity<String> httpRequest(String url, HttpMethod method, HttpEntity<?> entity){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, method, entity,String.class);
    }



}