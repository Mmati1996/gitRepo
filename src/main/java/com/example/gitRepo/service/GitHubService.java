package com.example.gitRepo.service;

import com.example.gitRepo.model.Branch;
import com.example.gitRepo.model.BranchToDisplay;
import com.example.gitRepo.model.RepoToDisplay;
import com.example.gitRepo.model.Repository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubService {

    private final RestTemplate template;
    private static final String GITHUB_API_URL = "https://api.github.com/";

    public GitHubService(RestTemplate template) {
        this.template = template;
    }

    public List<RepoToDisplay> getNonForkRepos(String username, HttpServletRequest request){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept",request.getHeader("Accept"));
        httpHeaders.set("X-GitHub-Api-Version",request.getHeader("X-GitHub-Api-Version"));
        httpHeaders.set("Authorization",request.getHeader("Authorization"));
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<Repository[]> reposExchange = template.exchange(GITHUB_API_URL + "users/" + username + "/repos", HttpMethod.GET, httpEntity, Repository[].class);
        Repository[] allRepos = reposExchange.getBody();
        List<RepoToDisplay> toReturn = new ArrayList<>();
        assert allRepos != null : "couldn't fetch repositories";
        for (Repository repo : allRepos){
            RepoToDisplay repoToAdd = new RepoToDisplay(repo);
            if (!repo.isFork()){
                ResponseEntity<Branch[]> branchesExchange = template.exchange(GITHUB_API_URL + "repos/" + username + "/" + repo.getName() + "/branches", HttpMethod.GET, httpEntity, Branch[].class);
                Branch[] allBranches = branchesExchange.getBody();
                assert allBranches != null : "failed to fetch "+repo.getName()+" branches";
                for (Branch branch : allBranches){
                    repoToAdd.getBranches().add(new BranchToDisplay(branch));
                }
                toReturn.add(repoToAdd);
            }
        }
        return toReturn;

    }
}
