package com.gslc.repository;

import java.util.List;

public interface Repository<T> {
    List<T> find(String column, String[] filterConditions);
    T findOne(String column, String[] filterConditions);
    void insert(List<T> items);
}