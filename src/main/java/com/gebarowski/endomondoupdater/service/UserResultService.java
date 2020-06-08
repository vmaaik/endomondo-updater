package com.gebarowski.endomondoupdater.service;

import com.gebarowski.endomondoupdater.model.UserResult;
import com.gebarowski.endomondoupdater.repository.UserResultRepository;
import com.gebarowski.endomondoupdater.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserResultService implements IUserResultService {

    @Autowired
    private UserResultRepository userResultRepository;

    @Override
    public List<UserResult> findAll() {
        return null;
    }

    @Override
    public void save(UserResult userResult) {
        userResultRepository.save(userResult);
    }

    @Override
    public int getLatestTotal() {
        return userResultRepository.countLatestTotal();
    }

    @Override
    public Double getGroupResult(String group) {
        return group.equals("A")
                ? Utils.round(userResultRepository.getGroupA(), 2)
                : Utils.round(userResultRepository.getGroupB(), 2);
    }
}
