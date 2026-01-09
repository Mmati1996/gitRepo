package com.example.gitRepo.controller;


import com.example.gitRepo.ExceptionResponse;
import com.example.gitRepo.model.RepoToDisplay;
import com.example.gitRepo.service.GitHubService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class GitHubController {

    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/repos")
    public ResponseEntity<?> getUserRepositories(@RequestParam String username, HttpServletRequest request){
        try{
            List<RepoToDisplay> repos = gitHubService.getNonForkRepos(username);
            return ResponseEntity.ok(repos);
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse("404","user "+username+" doesn't exist"));
        }


    }

}
