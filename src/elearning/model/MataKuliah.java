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
public class MataKuliah implements IValidasi {
    private String kodeMK;
    private String namaMK;
    private int    sks;

    public MataKuliah(String kodeMK, String namaMK, int sks) {
        this.kodeMK = kodeMK;
        this.namaMK = namaMK;
        this.sks    = sks;
    }

    // ── Getter & Setter ──────────────────────────────────────────
    public String getKodeMK()  { return kodeMK; }
    public String getNamaMK()  { return namaMK; }
    public int    getSks()     { return sks; }
    public void setKodeMK(String k)  { this.kodeMK = k; }
    public void setNamaMK(String n)  { this.namaMK = n; }
    public void setSks(int s)        { this.sks = s; }

    @Override
    public boolean isValid() {
        return kodeMK != null && !kodeMK.trim().isEmpty()
            && namaMK != null && !namaMK.trim().isEmpty()
            && sks > 0;
    }

    @Override
    public String getPesanError() {
        return "Kode MK, Nama MK wajib diisi, dan SKS harus > 0!";
    }

    @Override
    public String toString() {
        return kodeMK + " - " + namaMK + " (" + sks + " SKS)";
    }
}
