package com.lahezy.jgit.controller;

import com.lahezy.jgit.target.GitPullExample;
import com.lahezy.jgit.target.GitPullWithPlainJdk;
import com.lahezy.jgit.target.GitPullWithSSH;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JgitController {
    private final GitPullWithSSH gitPullWithSSH;
    private final GitPullExample gitPullExample;
    private final GitPullWithPlainJdk gitPullWithPlainJdk;
    @PostMapping("/jgit-ssh")
    public String fetchGitSSH(){
        return gitPullWithSSH.fetchWithSSH();
    }

    @PostMapping("/jgit-http/{token}")
    public String fetchGitHttp(@PathVariable String token) {
        return gitPullExample.fetchWithHTTP(token);
    }

    @PostMapping("/jgit-sdk/{token}")
    public String fetchGitSDK(@PathVariable String token){
        return gitPullWithPlainJdk.fetchWithPlain(token);
    }


}
