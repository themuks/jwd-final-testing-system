package com.kuntsevich.testsys.model.service;

import com.kuntsevich.testsys.entity.Result;
import com.kuntsevich.testsys.entity.User;

import java.util.ArrayList;
import java.util.List;

public interface ResultService {
    List<Result> findCurrentUserResults(String userHash);
}