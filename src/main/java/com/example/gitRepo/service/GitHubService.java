package com.example.gitRepo.service;

import com.example.gitRepo.model.Branch;
import com.example.gitRepo.model.BranchToDisplay;
import com.example.gitRepo.model.RepoToDisplay;
import com.example.gitRepo.model.Repository;
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

    public List<RepoToDisplay> getNonForkRepos(String username){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept","application/vnd.github+json");
        httpHeaders.set("X-GitHub-Api-Version","2022-11-28");
        httpHeaders.set("Authorization","Bearer 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");
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        //Repository[] allRepos = template.getForObject(GITHUB_API_URL+"users/" + username + "/repos", Repository[].class);
        ResponseEntity<Repository[]> reposExchange = template.exchange(GITHUB_API_URL + "users/" + username + "/repos", HttpMethod.GET, httpEntity, Repository[].class);
        Repository[] allRepos = reposExchange.getBody();
        List<RepoToDisplay> toReturn = new ArrayList<>();
        assert allRepos != null : "couldn't fetch repositories";
        for (Repository repo : allRepos){
            RepoToDisplay repoToAdd = new RepoToDisplay(repo);
            if (!repo.isFork()){
                //Branch[] allBranches = template.getForObject(GITHUB_API_URL+"repos/" + username + "/" + repo.getName() + "/branches", Branch[].class);
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
