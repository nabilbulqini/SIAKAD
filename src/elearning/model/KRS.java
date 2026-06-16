/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package elearning.model;

/**
 *
 * @author LENOVO
 */
public class KRS {
    private String     idKRS;
    private Mahasiswa  mahasiswa;
    private MataKuliah mataKuliah;
    private Dosen      dosen;
    private String     semester;   // contoh: "2024/2025 Genap"

    public KRS(String idKRS, Mahasiswa mahasiswa,
               MataKuliah mataKuliah, Dosen dosen, String semester) {
        this.idKRS      = idKRS;
        this.mahasiswa  = mahasiswa;
        this.mataKuliah = mataKuliah;
        this.dosen      = dosen;
        this.semester   = semester;
    }

    // ── Getter ────────────────────────────────────────────────────
    public String getIdKRS(){ 
        return idKRS; 
    }
    
    public Mahasiswa getMahasiswa(){ 
        return mahasiswa; 
    }
    
    public MataKuliah getMataKuliah(){ 
        return mataKuliah; 
    }
    
    public Dosen getDosen(){ 
        return dosen; 
    }
    
    public String getSemester(){ 
        return semester; 
    }
}
