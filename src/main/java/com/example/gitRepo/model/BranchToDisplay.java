package com.example.gitRepo.model;

public class BranchToDisplay {

    private String name;
    private String commitSha;

    public BranchToDisplay(Branch branch) {
        this.name=branch.getName();
        this.commitSha=branch.getCommit().getSha();
    }

    public BranchToDisplay() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommitSha() {
        return commitSha;
    }

    public void setCommitSha(String commitSha) {
        this.commitSha = commitSha;
    }
}
