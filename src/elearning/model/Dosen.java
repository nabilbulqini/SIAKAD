/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package elearning.model;
import elearning.util.IValidasi;
import elearning.util.JabatanDosen;

/**
 *
 * @author LENOVO
 */
public class Dosen extends Orang implements IValidasi {
    private String kodeDosen;
    private JabatanDosen jabatan;
    
    public Dosen(String kodeDosen, String nama, JabatanDosen jabatan) {
        super(nama);
        this.kodeDosen = kodeDosen;
        this.jabatan = jabatan;
    }
    
    public String getKodeDosen() {
        return kodeDosen;
    }
    
    public JabatanDosen getJabatan() {
        return jabatan;
    }
    
    public void setKodeDosen(String k) {
        this.kodeDosen = k;
    }
    
    public void setJabatan(JabatanDosen j) {
        this.jabatan = j;
    }
    
    @Override
    public String getInfo() {
        return "Dosen [" + kodeDosen + "] " + nama + " - " + jabatan;
    }
    
    @Override
    public boolean isValid() {
        return kodeDosen != null && !kodeDosen.trim().isEmpty()
                && nama != null && !nama.trim().isEmpty();
    }
    
    @Override
    public String getPesanError() {
        return "Kde Dosen dan Nama tidak boleh kosong!";
    }
}
