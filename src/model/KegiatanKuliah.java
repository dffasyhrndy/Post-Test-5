package model;

import java.sql.Date;

public class KegiatanKuliah extends Kegiatan {
    public KegiatanKuliah(String nama, Date tanggal, String status, String mataKuliah) {
        super(nama, tanggal, status, "kuliah", mataKuliah);
    }

    @Override
    public String getDetail() {
        return "Kuliah | Mata Kuliah: " + getTambahan();
    }
}
