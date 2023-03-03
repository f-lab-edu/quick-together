package com.flab.quicktogether.alarm.kakao;

import lombok.Data;

@Data
public class KaKaoSimpleMessageDto {
    private String objType;
    private String text;
    private String webUrl;
    private String mobileUrl;
    private String btnTitle;
}
