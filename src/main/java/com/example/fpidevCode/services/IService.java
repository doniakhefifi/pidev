package com.example.fpidevCode.services;

import java.util.List;

public interface IService <S> {
    void add (S var1);

    void delete(int id);

    void update (S var1);

    List<S> readAll();

    S readById(int var1);
}
