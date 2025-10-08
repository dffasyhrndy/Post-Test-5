package model;

import java.sql.Date;

public class KegiatanPraktikum extends Kegiatan {
    public KegiatanPraktikum(String nama, Date tanggal, String status, String praktikum) {
        super(nama, tanggal, status, "praktikum", praktikum);
    }

    @Override
    public String getDetail() {
        return "Praktikum | Nama Praktikum: " + getTambahan();
    }
}
