/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package elearning.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author LENOVO
 */
public class KoneksiDatabase {
   
    private static final String URL      = "jdbc:mysql://localhost:3306/siakad";
    private static final String USER     = "root";      
    private static final String PASSWORD = "";          
    private static final String DRIVER   = "com.mysql.cj.jdbc.Driver";

    private static Connection connection = null;

    public static Connection getConnection() {
    try {
        if (connection == null || connection.isClosed()) {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Koneksi MySQL berhasil!");
        }
    } catch (ClassNotFoundException e) {
        System.err.println("Driver tidak ditemukan: " + e.getMessage());
        System.err.println("Pastikan mysql-connector-j.jar sudah ditambahkan!");
    } catch (SQLException e) {
        System.err.println("Gagal konek ke MySQL: " + e.getMessage());
        System.err.println("Cek URL, username, password, dan pastikan MySQL menyala.");
    }
    return connection;
}
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Koneksi MySQL ditutup.");
            }
        } catch (SQLException e) {
            System.err.println("Gagal menutup koneksi: " + e.getMessage());
        }
    }
}
