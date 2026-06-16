package elearning.database;



// ── Model ─────────────────────────────────────────
import elearning.model.*;
import elearning.model.Dosen;
import elearning.model.KRS;
import elearning.model.Mahasiswa;
import elearning.model.MataKuliah;
import elearning.model.Nilai;
import elearning.model.User;      

// ── Util ──────────────────────────────────────────
import elearning.util.JabatanDosen;
import elearning.util.LevelUser;

// ── SQL ───────────────────────────────────────────
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class DataStore {

    // ── Kapasitas maksimal array (sesuai soal UTS) ────────────────
    public static final int MAX = 5;

    // ── Array buffer ──────────────────────────────────────────────
    private User[]       users       = new User[MAX];
    private Mahasiswa[]  mahasiswas  = new Mahasiswa[MAX];
    private Dosen[]      dosens      = new Dosen[MAX];
    private MataKuliah[] mataKuliahs = new MataKuliah[MAX];
    private KRS[]        krsList     = new KRS[MAX];
    private Nilai[]      nilais      = new Nilai[MAX];

    // ── Counter jumlah data di array ─────────────────────────────
    private int jumlahUser = 0, jumlahMhs  = 0, jumlahDosen = 0;
    private int jumlahMK   = 0, jumlahKRS  = 0, jumlahNilai = 0;

    // ── Singleton ─────────────────────────────────────────────────
    private static DataStore instance;

    private DataStore() {
        loadFromDB(); // muat data dari MySQL saat pertama kali
    }

    public static DataStore getInstance() {
        if (instance == null) instance = new DataStore();
        return instance;
    }

    //  LOAD FROM DATABASE — isi array dari MySQL saat program mulai

    private void loadFromDB() {
        Connection conn = KoneksiDatabase.getConnection();
        if (conn == null) {
            JOptionPane.showMessageDialog(null,
                "Gagal konek ke database!Program berjalan tanpa data.",
                "Koneksi Gagal", JOptionPane.ERROR_MESSAGE);
            return;
        }
        loadUsers(conn);
        loadMahasiswa(conn);
        loadDosen(conn);
        loadMataKuliah(conn);
        loadKRS(conn);
        loadNilai(conn);
    }

    private void loadUsers(Connection conn) {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM tb_user LIMIT " + MAX)) {
            while (rs.next() && jumlahUser < MAX) {
                LevelUser lv = LevelUser.valueOf(rs.getString("level"));
                users[jumlahUser++] = new User(
                    rs.getString("id_user"),
                    rs.getString("nama_user"),
                    rs.getString("password"),
                    lv
                );
            }
        } catch (SQLException e) {
            System.err.println("Load user gagal: " + e.getMessage());
        }
    }

    private void loadMahasiswa(Connection conn) {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM tb_mahasiswa LIMIT " + MAX)) {
            while (rs.next() && jumlahMhs < MAX) {
                mahasiswas[jumlahMhs++] = new Mahasiswa(
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("prodi")
                );
            }
        } catch (SQLException e) {
            System.err.println("Load mahasiswa gagal: " + e.getMessage());
        }
    }

    private void loadDosen(Connection conn) {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM tb_dosen LIMIT " + MAX)) {
            while (rs.next() && jumlahDosen < MAX) {
                JabatanDosen jab = JabatanDosen.valueOf(rs.getString("jabatan"));
                dosens[jumlahDosen++] = new Dosen(
                    rs.getString("kode_dosen"),
                    rs.getString("nama"),
                    jab
                );
            }
        } catch (SQLException e) {
            System.err.println("Load dosen gagal: " + e.getMessage());
        }
    }

    private void loadMataKuliah(Connection conn) {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM tb_mata_kuliah LIMIT " + MAX)) {
            while (rs.next() && jumlahMK < MAX) {
                mataKuliahs[jumlahMK++] = new MataKuliah(
                    rs.getString("kode_mk"),
                    rs.getString("nama_mk"),
                    rs.getInt("sks")
                );
            }
        } catch (SQLException e) {
            System.err.println("Load mata kuliah gagal: " + e.getMessage());
        }
    }

    private void loadKRS(Connection conn) {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM tb_krs LIMIT " + MAX)) {
            while (rs.next() && jumlahKRS < MAX) {
                Mahasiswa  mhs  = findMahasiswaByNim(rs.getString("nim"));
                MataKuliah mk   = findMKByKode(rs.getString("kode_mk"));
                Dosen      dsn  = findDosenByKode(rs.getString("kode_dosen"));
                if (mhs != null && mk != null && dsn != null) {
                    krsList[jumlahKRS++] = new KRS(
                        rs.getString("id_krs"), mhs, mk, dsn,
                        rs.getString("semester")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Load KRS gagal: " + e.getMessage());
        }
    }

    private void loadNilai(Connection conn) {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM tb_nilai LIMIT " + MAX)) {
            while (rs.next() && jumlahNilai < MAX) {
                String idKRS = rs.getString("id_krs");
                KRS krs = findKRSById(idKRS);
                if (krs != null) {
                    nilais[jumlahNilai++] = new Nilai(
                        krs,
                        rs.getDouble("nilai_uts"),
                        rs.getDouble("nilai_tugas"),
                        rs.getDouble("nilai_absensi"),
                        rs.getDouble("nilai_uas")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Load nilai gagal: " + e.getMessage());
        }
    }

    //  USER

    public boolean tambahUser(User u) {
        if (jumlahUser >= MAX) return false;

        // INSERT ke MySQL
        String sql = "INSERT INTO tb_user (id_user, nama_user, password, level) VALUES (?,?,?,?)";
        try (PreparedStatement ps = KoneksiDatabase.getConnection().prepareStatement(sql)) {
            ps.setString(1, u.getIdUser());
            ps.setString(2, u.getNamaUser());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getLevel().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("INSERT user gagal: " + e.getMessage());
            return false;
        }

        // Tambah ke array
        users[jumlahUser++] = u;
        return true;
    }

   
    public boolean updatePassword(User u, String passwordBaru) {
        String sql = "UPDATE tb_user SET password = ? WHERE id_user = ?";
        try (PreparedStatement ps = KoneksiDatabase.getConnection().prepareStatement(sql)) {
            ps.setString(1, passwordBaru);
            ps.setString(2, u.getIdUser());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("UPDATE password gagal: " + e.getMessage());
            return false;
        }
        u.setPassword(passwordBaru); // update di array juga
        return true;
    }

    public User login(String namaUser, String password) {
        for (int i = 0; i < jumlahUser; i++)
            if (users[i].getNamaUser().equals(namaUser)
             && users[i].getPassword().equals(password))
                return users[i];
        return null;
    }

    public User[]  getUsers()      { return users; }
    public int     getJumlahUser() { return jumlahUser; }

    //  MAHASISWA

    public boolean tambahMahasiswa(Mahasiswa m) {
        if (jumlahMhs >= MAX) return false;

        String sql = "INSERT INTO tb_mahasiswa (nim, nama, prodi) VALUES (?,?,?)";
        try (PreparedStatement ps = KoneksiDatabase.getConnection().prepareStatement(sql)) {
            ps.setString(1, m.getNim());
            ps.setString(2, m.getNama());
            ps.setString(3, m.getProdi());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("INSERT mahasiswa gagal: " + e.getMessage());
            return false;
        }

        mahasiswas[jumlahMhs++] = m;
        return true;
    }

    public boolean hapusMahasiswa(String nim) {
        String sql = "DELETE FROM tb_mahasiswa WHERE nim = ?";
        try (PreparedStatement ps = KoneksiDatabase.getConnection().prepareStatement(sql)) {
            ps.setString(1, nim);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("DELETE mahasiswa gagal: " + e.getMessage());
            return false;
        }

        // Geser array ke kiri
        for (int i = 0; i < jumlahMhs; i++) {
            if (mahasiswas[i].getNim().equals(nim)) {
                for (int j = i; j < jumlahMhs - 1; j++)
                    mahasiswas[j] = mahasiswas[j + 1];
                mahasiswas[--jumlahMhs] = null;
                return true;
            }
        }
        return false;
    }

    public Mahasiswa findMahasiswaByNim(String nim) {
        for (int i = 0; i < jumlahMhs; i++)
            if (mahasiswas[i] != null && mahasiswas[i].getNim().equals(nim))
                return mahasiswas[i];
        return null;
    }

    public Mahasiswa[] getMahasiswas()  { return mahasiswas; }
    public int         getJumlahMhs()   { return jumlahMhs; }

    //  DOSEN

    public boolean tambahDosen(Dosen d) {
        if (jumlahDosen >= MAX) return false;

        String sql = "INSERT INTO tb_dosen (kode_dosen, nama, jabatan) VALUES (?,?,?)";
        try (PreparedStatement ps = KoneksiDatabase.getConnection().prepareStatement(sql)) {
            ps.setString(1, d.getKodeDosen());
            ps.setString(2, d.getNama());
            ps.setString(3, d.getJabatan().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("INSERT dosen gagal: " + e.getMessage());
            return false;
        }

        dosens[jumlahDosen++] = d;
        return true;
    }
    
    public boolean hapusDosen(String kode) {
    // Hapus dari MySQL
    String sql = "DELETE FROM tb_dosen WHERE kode_dosen = ?";
    try (PreparedStatement ps = KoneksiDatabase.getConnection().prepareStatement(sql)) {
        ps.setString(1, kode);
        ps.executeUpdate();
    } catch (SQLException e) {
        System.err.println("DELETE dosen gagal: " + e.getMessage());
        return false;
    }

    // Hapus dari array — geser ke kiri
    for (int i = 0; i < jumlahDosen; i++) {
        if (dosens[i].getKodeDosen().equals(kode)) {
            for (int j = i; j < jumlahDosen - 1; j++)
                dosens[j] = dosens[j + 1];
            dosens[--jumlahDosen] = null;
            return true;
        }
    }
    return false;
}

    public Dosen findDosenByKode(String kode) {
        for (int i = 0; i < jumlahDosen; i++)
            if (dosens[i] != null && dosens[i].getKodeDosen().equals(kode))
                return dosens[i];
        return null;
    }

    public Dosen[] getDosens()    { return dosens; }
    public int     getJumlahDosen() { return jumlahDosen; }

    
    //  MATA KULIAH

    public boolean tambahMataKuliah(MataKuliah mk) {
        if (jumlahMK >= MAX) return false;

        String sql = "INSERT INTO tb_mata_kuliah (kode_mk, nama_mk, sks) VALUES (?,?,?)";
        try (PreparedStatement ps = KoneksiDatabase.getConnection().prepareStatement(sql)) {
            ps.setString(1, mk.getKodeMK());
            ps.setString(2, mk.getNamaMK());
            ps.setInt(3, mk.getSks());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("INSERT mata kuliah gagal: " + e.getMessage());
            return false;
        }

        mataKuliahs[jumlahMK++] = mk;
        return true;
    }
    
    public boolean hapusMataKuliah(String kode) {
    String sql = "DELETE FROM tb_mata_kuliah WHERE kode_mk = ?";
    try (PreparedStatement ps = KoneksiDatabase.getConnection().prepareStatement(sql)) {
        ps.setString(1, kode);
        ps.executeUpdate();
    } catch (SQLException e) {
        System.err.println("DELETE mata kuliah gagal: " + e.getMessage());
        return false;
    }

    for (int i = 0; i < jumlahMK; i++) {
        if (mataKuliahs[i].getKodeMK().equals(kode)) {
            for (int j = i; j < jumlahMK - 1; j++)
                mataKuliahs[j] = mataKuliahs[j + 1];
            mataKuliahs[--jumlahMK] = null;
            return true;
        }
    }
    return false;
}

    public MataKuliah findMKByKode(String kode) {
        for (int i = 0; i < jumlahMK; i++)
            if (mataKuliahs[i] != null && mataKuliahs[i].getKodeMK().equals(kode))
                return mataKuliahs[i];
        return null;
    }

    public MataKuliah[] getMataKuliahs() { return mataKuliahs; }
    public int          getJumlahMK()    { return jumlahMK; }

   
    //  KRS

    public boolean tambahKRS(KRS k) {
        if (jumlahKRS >= MAX) return false;

        String sql = "INSERT INTO tb_krs (id_krs, nim, kode_mk, kode_dosen, semester) "
                   + "VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = KoneksiDatabase.getConnection().prepareStatement(sql)) {
            ps.setString(1, k.getIdKRS());
            ps.setString(2, k.getMahasiswa().getNim());
            ps.setString(3, k.getMataKuliah().getKodeMK());
            ps.setString(4, k.getDosen().getKodeDosen());
            ps.setString(5, k.getSemester());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("INSERT KRS gagal: " + e.getMessage());
            return false;
        }

        krsList[jumlahKRS++] = k;
        return true;
    }
    
    public boolean hapusKRS(String idKRS) {
    String sql = "DELETE FROM tb_krs WHERE id_krs = ?";
    try (PreparedStatement ps = KoneksiDatabase.getConnection().prepareStatement(sql)) {
        ps.setString(1, idKRS);
        ps.executeUpdate();
    } catch (SQLException e) {
        System.err.println("DELETE KRS gagal: " + e.getMessage());
        return false;
    }

    for (int i = 0; i < jumlahKRS; i++) {
        if (krsList[i].getIdKRS().equals(idKRS)) {
            for (int j = i; j < jumlahKRS - 1; j++)
                krsList[j] = krsList[j + 1];
            krsList[--jumlahKRS] = null;
            return true;
        }
    }
    return false;
}

    public KRS findKRSById(String idKRS) {
        for (int i = 0; i < jumlahKRS; i++)
            if (krsList[i] != null && krsList[i].getIdKRS().equals(idKRS))
                return krsList[i];
        return null;
    }

   
    public int getTotalSKS(String nim) {
        int total = 0;
        for (int i = 0; i < jumlahKRS; i++)
            if (krsList[i] != null && krsList[i].getMahasiswa().getNim().equals(nim))
                total += krsList[i].getMataKuliah().getSks();
        return total;
    }

    public KRS[] getKrsList()   { return krsList; }
    public int   getJumlahKRS() { return jumlahKRS; }

    // ══════════════════════════════════════════════════════════════
    //  NILAI
    // ══════════════════════════════════════════════════════════════

    public boolean tambahNilai(Nilai n) {
        if (jumlahNilai >= MAX) return false;

        String sql = "INSERT INTO tb_nilai "
                   + "(id_krs, nilai_uts, nilai_tugas, nilai_absensi, nilai_uas, nilai_akhir, nilai_huruf) "
                   + "VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = KoneksiDatabase.getConnection().prepareStatement(sql)) {
            ps.setString(1, n.getKrs().getIdKRS());
            ps.setDouble(2, n.getNilaiUTS());
            ps.setDouble(3, n.getNilaiTugas());
            ps.setDouble(4, n.getNilaiAbsensi());
            ps.setDouble(5, n.getNilaiUAS());
            ps.setDouble(6, n.getNilaiAkhir());     // hasil rumus
            ps.setString(7, n.getNilaiHuruf());     // A/B/C/D/E
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("INSERT nilai gagal: " + e.getMessage());
            return false;
        }

        nilais[jumlahNilai++] = n;
        return true;
    }

    public Nilai[] getNilais()    { return nilais; }
    public int     getJumlahNilai() { return jumlahNilai; }
}