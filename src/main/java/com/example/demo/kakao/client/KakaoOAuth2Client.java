package com.example.demo.kakao.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.demo.kakao.client.response.KakaoTokenResponse;
import com.example.demo.kakao.client.response.KakaoUserInfoResponse;

@Component
public class KakaoOAuth2Client {
    private final static String KAKAO_AUTH_BASE_URL = "https://kauth.kakao.com";
    private final static String KAKAO_API_BASE_URL = "https://kapi.kakao.com";
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${oauth.kakao.client_id}")
    private String clientId;

    @Value("${oauth.kakao.redirect_uri}")
    private String redirectUri;

    public String generateRedirectUrl() {
        return KAKAO_AUTH_BASE_URL + "/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=" + "code";
    }

    public String getAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("code", code);

        ResponseEntity<KakaoTokenResponse> resp = restTemplate.postForEntity(
                KAKAO_AUTH_BASE_URL + "/oauth/token",
                new HttpEntity<>(body, headers),
                KakaoTokenResponse.class
        );
        if (resp.getStatusCode().isError()) {
            throw new RuntimeException(String.format("KAKAO AccessToken 조회에 실패했습니다. (code : %s)", code));
        }
        return resp.getBody().getAccessToken();

    }

    public KakaoUserInfoResponse getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        ResponseEntity<KakaoUserInfoResponse> resp = restTemplate.exchange(
                KAKAO_API_BASE_URL + "/v2/user/me",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                KakaoUserInfoResponse.class
        );
        if (resp.getStatusCode().isError()) {
            throw new RuntimeException("KAKAO 사용자 정보 조회 실패");
        }
        return resp.getBody();
    }

}
