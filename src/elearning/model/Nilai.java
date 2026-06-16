/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package elearning.model;

/**
 *
 * @author LENOVO
 */
public class Nilai {
    private KRS    krs;
    private double nilaiUTS;
    private double nilaiTugas;
    private double nilaiAbsensi;
    private double nilaiUAS;

    public Nilai(KRS krs, double nilaiUTS, double nilaiTugas,
                 double nilaiAbsensi, double nilaiUAS) {
        this.krs          = krs;
        this.nilaiUTS     = nilaiUTS;
        this.nilaiTugas   = nilaiTugas;
        this.nilaiAbsensi = nilaiAbsensi;
        this.nilaiUAS     = nilaiUAS;
    }

    
    public double getNilaiAkhir() {
        return (0.30 * nilaiUTS)
             + (0.20 * nilaiTugas)
             + (0.40 * nilaiAbsensi)
             + (0.10 * nilaiUAS);
    }

    
    public String getNilaiHuruf() {
        double na = getNilaiAkhir();
        if (na >= 85) return "A";
        if (na >= 70) return "B";
        if (na >= 55) return "C";
        if (na >= 40) return "D";
        return "E";
    }

    // ── Getter ────────────────────────────────────────────────────
    public KRS    getKrs()          { return krs; }
    public double getNilaiUTS()     { return nilaiUTS; }
    public double getNilaiTugas()   { return nilaiTugas; }
    public double getNilaiAbsensi() { return nilaiAbsensi; }
    public double getNilaiUAS()     { return nilaiUAS; }
}
