package model;

import java.sql.Date;

public abstract class Kegiatan {
    private int id;
    private String nama;
    private Date tanggal;
    private String status;
    private String tipe;
    private String tambahan;

    public Kegiatan(String nama, Date tanggal, String status, String tipe, String tambahan) {
        this.nama = nama;
        this.tanggal = tanggal;
        this.status = status;
        this.tipe = tipe;
        this.tambahan = tambahan;
    }

    public Kegiatan(int id, String nama, Date tanggal, String status, String tipe, String tambahan) {
        this.id = id;
        this.nama = nama;
        this.tanggal = tanggal;
        this.status = status;
        this.tipe = tipe;
        this.tambahan = tambahan;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public Date getTanggal() { return tanggal; }
    public String getStatus() { return status; }
    public String getTipe() { return tipe; }
    public String getTambahan() { return tambahan; }

    public void setNama(String nama) { this.nama = nama; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }
    public void setStatus(String status) { this.status = status; }
    public void setTipe(String tipe) { this.tipe = tipe; }
    public void setTambahan(String tambahan) { this.tambahan = tambahan; }

    public abstract String getDetail();
}
