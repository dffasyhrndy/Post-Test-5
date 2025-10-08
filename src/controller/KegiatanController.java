package controller;

import db.DatabaseConnection;
import model.Kegiatan;

import java.sql.*;
import java.util.Scanner;
import model.KegiatanKuliah;
import model.KegiatanOrganisasi;
import model.KegiatanPraktikum;

public class KegiatanController {

    public void tambahKegiatan(String nama, Date tanggal, String status, String tipe, String tambahan) {
        String sql = "INSERT INTO kegiatan (nama, tanggal, status, tipe, tambahan) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nama);
            ps.setDate(2, tanggal);
            ps.setString(3, status);
            ps.setString(4, tipe);
            ps.setString(5, tambahan);
            ps.executeUpdate();
            System.out.println("Kegiatan berhasil ditambahkan!");
        } catch (SQLException e) {
            System.out.println("Gagal menambah kegiatan: " + e.getMessage());
        }
    }

    public void tampilkanKegiatan() {
        String sql = "SELECT * FROM kegiatan ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n=== DAFTAR KEGIATAN ===");
            boolean ada = false;
            while (rs.next()) {
                ada = true;
                int id = rs.getInt("id");
                String nama = rs.getString("nama");
                Date tanggal = rs.getDate("tanggal");
                String status = rs.getString("status");
                String tipe = rs.getString("tipe");
                String tambahan = rs.getString("tambahan");
                System.out.println(id + " | " + nama + " | " + tanggal + " | " + status +
                                   " | " + tipe + (tambahan != null ? " | " + tambahan : ""));
            }
            if (!ada) System.out.println("Belum ada data kegiatan.");
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan kegiatan: " + e.getMessage());
        }
    }

    public Kegiatan getById(int id) {
    String sql = "SELECT * FROM kegiatan WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String tipe = rs.getString("tipe");
                String nama = rs.getString("nama");
                Date tanggal = rs.getDate("tanggal");
                String status = rs.getString("status");
                String tambahan = rs.getString("tambahan");

                return switch (tipe) {
                    case "kuliah" -> new KegiatanKuliah(nama, tanggal, status, tambahan);
                    case "organisasi" -> new KegiatanOrganisasi(nama, tanggal, status);
                    case "praktikum" -> new KegiatanPraktikum(nama, tanggal, status, tambahan);
                    default -> null;
                };
            }
        }
    } catch (SQLException e) {
        System.out.println("Gagal ambil data: " + e.getMessage());
    }
    return null;
}


    public void updateKegiatan(int id, String nama, Date tanggal, String status) {
        String sql = "UPDATE kegiatan SET nama=?, tanggal=?, status=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nama);
            ps.setDate(2, tanggal);
            ps.setString(3, status);
            ps.setInt(4, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Kegiatan berhasil diupdate!");
            else System.out.println("ID tidak ditemukan.");
        } catch (SQLException e) {
            System.out.println("Gagal update: " + e.getMessage());
        }
    }

    public void hapusKegiatan(int id) {
        String sql = "DELETE FROM kegiatan WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Kegiatan berhasil dihapus!");
            else System.out.println("ID tidak ditemukan.");
        } catch (SQLException e) {
            System.out.println("Gagal hapus: " + e.getMessage());
        }
    }

    public void cariKegiatan(String keyword) {
        String sql = "SELECT * FROM kegiatan WHERE nama LIKE ? OR status LIKE ? OR tipe LIKE ? ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;
                System.out.println("\n=== HASIL PENCARIAN ===");
                while (rs.next()) {
                    found = true;
                    System.out.println(rs.getInt("id") + " | " +
                        rs.getString("nama") + " | " +
                        rs.getDate("tanggal") + " | " +
                        rs.getString("status") + " | " +
                        rs.getString("tipe") +
                        (rs.getString("tambahan") != null ? " | " + rs.getString("tambahan") : ""));
                }
                if (!found) System.out.println("Tidak ditemukan hasil untuk: " + keyword);
            }
        } catch (SQLException e) {
            System.out.println("Gagal cari: " + e.getMessage());
        }
    }
}
