package com.example.fpidevCode.interfaces;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public interface IService<T>{
    void ajouter (T t) throws SQLException, NoSuchAlgorithmException;
    void supprimer (T t)throws SQLException;
    void modifier (T t)throws SQLException;
    List<T> afficher();
}
