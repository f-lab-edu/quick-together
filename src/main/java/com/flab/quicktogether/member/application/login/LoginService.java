package com.flab.quicktogether.member.application.login;

public interface LoginService {
    public Long login(String memberName, String password);
    public void logout();

}
