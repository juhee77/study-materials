package com.lahezy.jgit.target;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class GitPullExample {
    public String fetchWithHTTP(String token) {
        // Git 리포지토리 URL
        String repoUrl = "https://github.com/juhee77/study-materials.git";

        // 로컬에 저장할 디렉토리
        String localPath = "./temp/gitTest-http";

        // GitHub 또는 원격 리포지토리 인증 정보 (필요하면 사용)
        String username = "juhee77";
        String password = token;

        try {
            File localRepoDir = new File(localPath);

            if (!localRepoDir.exists()) {
                // 리포지토리가 없으면 클론
                System.out.println("Cloning repository...");
                Git.cloneRepository()
                        .setURI(repoUrl)
                        .setDirectory(localRepoDir)
                        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
                        .call();
                return "Repository cloned successfully!";
            } else {
                // 리포지토리가 이미 존재하면 Pull
                System.out.println("Pulling latest changes...");
                Git git = Git.open(localRepoDir);
                PullResult pullResult = git.pull()
                        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
                        .call();

                if (pullResult.isSuccessful()) {
                    return "Pull completed successfully!";
                } else {
                    return "Pull failed: " + pullResult.toString();
                }
            }
        } catch (GitAPIException | java.io.IOException e) {
            e.printStackTrace();
        }
        return "FAIL";
    }
}
