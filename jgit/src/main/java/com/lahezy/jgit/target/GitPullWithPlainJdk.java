package com.lahezy.jgit.target;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Service

public class GitPullWithPlainJdk {
    public String fetchWithPlain(String token) {
        String repoUrl = "https://github.com/juhee77/study-materials.git";
        String username = "juhee77"; // Bitbucket 아이디

        try {
            // 기본 인증을 위한 헤더 설정
            String auth = username + ":" + token;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes("UTF-8"));

            // HttpClient 생성
            HttpClient client = HttpClient.newHttpClient();

            // HttpRequest 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(repoUrl))
                    .header("Authorization", "Basic " + encodedAuth)
                    .build();

            // 요청 보내기
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 응답 코드 및 내용 출력
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
            return "Response Body: " + response.body();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAIL";
    }
}
