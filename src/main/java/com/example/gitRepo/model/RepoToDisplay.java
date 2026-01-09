package com.example.gitRepo.model;

import java.util.ArrayList;

public class RepoToDisplay {

    String repositoryName;
    String ownerLogin;
    private ArrayList<BranchToDisplay> branches;

    public RepoToDisplay(Repository repository) {
        this.repositoryName = repository.getName();
        this.ownerLogin = repository.getOwner().getLogin();
        this.branches = new ArrayList<>();
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public ArrayList<BranchToDisplay> getBranches() {
        return branches;
    }

    public void setBranches(ArrayList<BranchToDisplay> branches) {
        this.branches = branches;
    }
}
