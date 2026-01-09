package com.example.gitRepo.service;

import com.example.gitRepo.model.Branch;
import com.example.gitRepo.model.BranchToDisplay;
import com.example.gitRepo.model.RepoToDisplay;
import com.example.gitRepo.model.Repository;
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

        Repository[] allRepos = template.getForObject(GITHUB_API_URL+"users/" + username + "/repos", Repository[].class);
        List<RepoToDisplay> toReturn = new ArrayList<>();
        assert allRepos != null : "couldn't fetch repositories";
        for (Repository repo : allRepos){
            RepoToDisplay repoToAdd = new RepoToDisplay(repo);
            if (!repo.isFork()){
                Branch[] allBranches = template.getForObject(GITHUB_API_URL+"repos/" + username + "/" + repo.getName() + "/branches", Branch[].class);
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
