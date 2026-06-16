/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package elearning.model;
import elearning.util.IValidasi;
/**
 *
 * @author LENOVO
 */
public class Mahasiswa extends Orang implements IValidasi {
    private String nim;
    private String prodi;
    
    public Mahasiswa(String nim, String nama, String prodi) {
        super(nama);
        this.nim = nim;
        this.prodi = prodi;
    }
    
    public String getNim() {
        return nim;
    }
    
    public String getProdi() {
        return prodi;
    }
    
    public void setNim(String nim) {
        this.nim = nim;
    }
    
    public void setProdi(String prodi) {
        this.prodi = prodi;
    }
    
    @Override
    public String getInfo() {
        return "Mahasiswa [" + nim + "] " + nama + " - " + prodi;
    }
    
    @Override
    public boolean isValid() {
        return nim != null && !nim.trim().isEmpty()
                && nama != null && !nama.trim().isEmpty()
                && prodi != null && !prodi.trim().isEmpty();
    }
    
    @Override
    public String getPesanError() {
        return "NIM, nama, dan Prodi tidak boleh kosong!";
    }

   
}

