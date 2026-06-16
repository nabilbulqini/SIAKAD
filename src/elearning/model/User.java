/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package elearning.model;

import elearning.util.LevelUser;

/**
 *
 * @author LENOVO
 */
public class User {
    private String idUser;
    private String namaUser;
    private String password;
    private LevelUser level;
    
    public User(String idUser, String namaUser, String password, LevelUser level) {
        this.idUser = idUser;
        this.namaUser = namaUser;
        this.password = password;
        this.level = level;
    }
    
    //Getter dan Setter
    public String getIdUser() {
        return idUser;
    }
    
    public String getNamaUser() {
        return namaUser;
    }
    
    public String getPassword() {
        return password;
    }
    
    public LevelUser getLevel() {
        return level;
    }
    
    public void setPassword(String p) {
        this.password = p;
    }
    
    public void setNamaUser(String n) {
        this.namaUser = n;
    }
}
