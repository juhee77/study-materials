package com.lahezy.jgit.target;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.sshd.SshdSessionFactory;
import org.eclipse.jgit.transport.sshd.SshdSessionFactoryBuilder;
import org.eclipse.jgit.util.FS;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class GitPullWithSSH {
    public String fetchWithSSH() {
        // Git 리포지토리 URL (SSH 경로)
        String repoUrl = "git@github.com:juhee77/study-materials.git";

        // 로컬에 저장할 디렉토리
        String localPath = "./temp/gitTest-ssh";

        // ApacheSshdSessionFactory를 사용하여 SSH 세션 팩토리 설정
        File sshDir = new File(FS.DETECTED.userHome(), "/.ssh");
        SshdSessionFactory sshSessionFactory = new SshdSessionFactoryBuilder()
                .setPreferredAuthentications("publickey")
                .setHomeDirectory(FS.DETECTED.userHome())
                .setSshDirectory(sshDir)
                .build(null);
        SshSessionFactory.setInstance(sshSessionFactory);

        // SshSessionFactory 설정
        SshSessionFactory.setInstance(sshSessionFactory);


        try {
            File localRepoDir = new File(localPath);

            if (!localRepoDir.exists()) {
                // 리포지토리가 없으면 클론
                System.out.println("Cloning repository...");
                Git.cloneRepository()
                        .setURI(repoUrl)
                        .setDirectory(localRepoDir)
                        .call();
                return "Repository cloned successfully!";
            } else {
                // 리포지토리가 있으면 Pull
                System.out.println("Pulling latest changes...");
                Git git = Git.open(localRepoDir);
                PullResult pullResult = git.pull().call();

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
