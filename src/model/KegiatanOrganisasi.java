package model;

import java.sql.Date;

public class KegiatanOrganisasi extends Kegiatan {
    public KegiatanOrganisasi(String nama, Date tanggal, String status) {
        super(nama, tanggal, status, "organisasi", "INFORSA");
    }

    @Override
    public String getDetail() {
        return "Organisasi | Nama Organisasi: INFORSA";
    }
}
