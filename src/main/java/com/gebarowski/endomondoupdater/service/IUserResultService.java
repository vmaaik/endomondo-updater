package com.gebarowski.endomondoupdater.service;

import com.gebarowski.endomondoupdater.model.UserResult;

import java.util.List;

public interface IUserResultService {

    List<UserResult> findAll();

    void save(UserResult userResult);

    int getLatestTotal();

    Double getGroupResult(String group);

}
