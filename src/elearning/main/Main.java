/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package elearning.main;

import elearning.database.KoneksiDatabase;
import elearning.gui.Login_Form;
import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {

        // Tutup koneksi MySQL saat program ditutup
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            KoneksiDatabase.closeConnection();
        }));

        SwingUtilities.invokeLater(() -> {
            new Login_Form().setVisible(true);
        });
    }
}