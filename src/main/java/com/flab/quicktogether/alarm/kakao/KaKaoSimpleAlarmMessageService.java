package com.flab.quicktogether.alarm.kakao;


import com.flab.quicktogether.alarm.message.AlarmMessage;
import com.flab.quicktogether.alarm.message.SimpleMessageDto;
import com.flab.quicktogether.alarm.service.AlarmMessageService;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class KaKaoSimpleAlarmMessageService implements AlarmMessageService<HttpEntity, SimpleMessageDto> {

    @Override
    public HttpEntity createMessage(String token, AlarmMessage<SimpleMessageDto> alarmMessage){
        SimpleMessageDto messageInfo =  alarmMessage.getMessageInfo();

        KaKaoSimpleMessageDto msgDto = new KaKaoSimpleMessageDto();
        msgDto.setBtnTitle(messageInfo.getTitle());
        msgDto.setMobileUrl("");
        msgDto.setObjType("text");
        msgDto.setWebUrl("");
        msgDto.setText(messageInfo.getBody());

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
        header.set("Authorization", "Bearer " + token);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("template_object", templateObj.toString());

        HttpEntity<?> messageRequestEntity = httpClientEntity(header, parameters);

        return messageRequestEntity;
    }

    public HttpEntity<?> httpClientEntity(HttpHeaders header, Object params) {
        HttpHeaders requestHeaders = header;

        if (params == null || "".equals(params))
            return new HttpEntity<>(requestHeaders);
        else
            return new HttpEntity<>(params, requestHeaders);
    }


}
