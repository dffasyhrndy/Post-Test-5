package main;

import controller.KegiatanController;
import model.Kegiatan;

import java.sql.Date;
import java.util.Scanner;
import model.KegiatanKuliah;
import model.KegiatanOrganisasi;
import model.KegiatanPraktikum;

public class MainView {
    private final Scanner input = new Scanner(System.in);
    private final KegiatanController controller = new KegiatanController();

    public static void main(String[] args) {
        new MainView().tampilkanMenu();
    }

    public void tampilkanMenu() {
        while (true) {
            System.out.println("\n=== PROGRAM TO DO LIST ===");
            System.out.println("1. Tambah Kegiatan");
            System.out.println("2. Lihat Kegiatan");
            System.out.println("3. Update Kegiatan");
            System.out.println("4. Hapus Kegiatan");
            System.out.println("5. Cari Kegiatan");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu: ");

            int pilihan;
            try {
                pilihan = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
                continue;
            }

            switch (pilihan) {
                case 1 -> tambah();
                case 2 -> controller.tampilkanKegiatan();
                case 3 -> update();
                case 4 -> {
                    System.out.print("Masukkan ID yang ingin dihapus: ");
                    int idDel = Integer.parseInt(input.nextLine());
                    controller.hapusKegiatan(idDel);
                }
                case 5 -> {
                    System.out.print("Keyword cari: ");
                    String kw = input.nextLine();
                    controller.cariKegiatan(kw);
                }
                case 6 -> {
                    System.out.println("Terima kasih!");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

        private void tambah() {
                System.out.println("Pilih jenis kegiatan:");
                System.out.println("1. Kuliah");
                System.out.println("2. Organisasi (INFORSA)");
                System.out.println("3. Praktikum");
                System.out.print("Pilihan: ");
                int jenis = Integer.parseInt(input.nextLine());

                String nama;
                System.out.print("Nama kegiatan: ");
                nama = input.nextLine();

                System.out.print("Tanggal (yyyy-MM-dd): ");
                Date tanggal = Date.valueOf(input.nextLine());

                Kegiatan kegiatan = null;

                switch (jenis) {
                    case 1 -> {
                        System.out.print("Masukkan Mata Kuliah: ");
                        String matkul = input.nextLine();
                        kegiatan = new KegiatanKuliah(nama, tanggal, "Belum Selesai", matkul);
                    }
                    case 2 -> kegiatan = new KegiatanOrganisasi(nama, tanggal, "Belum Selesai");
                    case 3 -> {
                        System.out.print("Masukkan Nama Praktikum: ");
                        String praktikum = input.nextLine();
                        kegiatan = new KegiatanPraktikum(nama, tanggal, "Belum Selesai", praktikum);
                    }
                    default -> {
                        System.out.println("Jenis tidak valid.");
                        return;
                    }
                }

                controller.tambahKegiatan(
                    kegiatan.getNama(),
                    kegiatan.getTanggal(),
                    kegiatan.getStatus(),
                    kegiatan.getTipe(),
                    kegiatan.getTambahan()
                );
    }

    private void update() {
        controller.tampilkanKegiatan();
        System.out.print("Masukkan ID yang ingin diupdate: ");
        int id;
        try {
            id = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Input harus angka. Batal.");
            return;
        }

        Kegiatan old = controller.getById(id);
        if (old == null) {
            System.out.println("ID tidak ditemukan.");
            return;
        }

        System.out.print("Update Nama (kosongkan jika tidak diubah): ");
        String nama = input.nextLine().trim();
        if (nama.isEmpty()) nama = old.getNama();

        System.out.print("Update Tanggal (yyyy-MM-dd, kosongkan jika tidak diubah): ");
        String tgl = input.nextLine().trim();
        Date tanggal = old.getTanggal();
        if (!tgl.isEmpty()) {
            try {
                tanggal = Date.valueOf(tgl);
            } catch (IllegalArgumentException ex) {
                System.out.println("Format tanggal salah. Batal.");
                return;
            }
        }

        System.out.println("Update Status:");
        System.out.println("1. Selesai");
        System.out.println("2. Belum Selesai");
        System.out.println("3. Tidak diubah");
        System.out.print("Pilih: ");
        int s;
        try {
            s = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Input harus angka. Batal.");
            return;
        }

        String status = old.getStatus();
        if (s == 1) status = "Selesai";
        else if (s == 2) status = "Belum Selesai";
        else if (s == 3) status = old.getStatus();
        else {
            System.out.println("Pilihan status tidak valid. Batal.");
            return;
        }

        controller.updateKegiatan(id, nama, tanggal, status);
    }
}
