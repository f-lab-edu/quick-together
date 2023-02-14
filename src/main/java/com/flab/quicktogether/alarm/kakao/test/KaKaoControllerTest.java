package com.flab.quicktogether.alarm.kakao.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
@Slf4j
public class KaKaoControllerTest {

    @Autowired
    KaKaoMessageServiceTest kaKaoMessageServiceTest;

    @GetMapping("/kakao")
    public String kakao() throws IOException {
        log.info("KakaoController /kakao start");
        return "kakao/kakaologin";
    }

    @RequestMapping(value = "/login/getKakaoAuthUrl")
    public @ResponseBody String getKakaoAuthUrl(
            HttpServletRequest request) throws Exception {
        String reqUrl =
                "https://kauth.kakao.com/oauth/authorize"
                        + "?client_id=fc0363bb269c98e1c871fbe16ad1efaa"
                        + "&redirect_uri=http://localhost:8080/login/oauth_kakao"
                        + "&response_type=code"
                        + "&scope=talk_message";

        return reqUrl;
    }

    // 카카오 연동정보 조회
    @RequestMapping(value = "/login/oauth_kakao")
    public String oauthKakao(
            @RequestParam(value = "code", required = false) String code
            , Model model) throws Exception {

        String access_Token = getAccessToken(code);
        log.info("access_Token = {}", access_Token);

        // 알림보내기
        kaKaoMessageServiceTest.sendMessage(access_Token);

        return "kakao/hello";
    }

    //토큰발급
    public String getAccessToken (String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //  URL연결은 입출력에 사용 될 수 있고, POST 혹은 PUT 요청을 하려면 setDoOutput을 true로 설정해야함.
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //	POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=fc0363bb269c98e1c871fbe16ad1efaa");  // Rest key
            sb.append("&redirect_uri=http://localhost:8080/login/oauth_kakao");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return access_Token;
    }

}