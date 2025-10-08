### Nama  : Daffa Syahrandy Husain  
### NIM   : 2409116069  
---

## Deskripsi Singkat
Program **To-Do List** ini dibuat menggunakan bahasa **Java** dengan konsep **CRUD (Create, Read, Update, Delete)** yang terus dikembangkan dengan penerapan **OOP (MVC, Abstraction, Polymorphism)** serta koneksi ke database menggunakan **JDBC dan ORM**.  
Program memungkinkan pengguna untuk:
- Menambahkan kegiatan (Kuliah, Organisasi, Praktikum).  
- Melihat daftar kegiatan yang tersimpan di database.  
- Mengupdate data kegiatan (nama, tanggal, status).  
- Menghapus kegiatan tertentu.  
- Mencari kegiatan berdasarkan keyword.  

---

## Alur Program

1. **Tampilan Menu Utama**  
   Menampilkan 6 opsi menu utama: Tambah, Lihat, Update, Hapus, Cari, dan Keluar.  
   Input diproses dengan `switch-case`.

2. **Tambah Kegiatan (Create)**  
   - User memilih jenis kegiatan:  
     1. Kuliah → input nama, tanggal, dan mata kuliah.  
     2. Organisasi → input nama & tanggal, nama organisasi otomatis **INFORSA**.  
     3. Praktikum → input nama, tanggal, dan nama praktikum.  
   - Data disimpan ke database melalui controller menggunakan JDBC.

3. **Lihat Daftar Kegiatan (Read)**  
   - Program membaca data dari tabel `kegiatan` di database.  
   - Data ditampilkan dalam bentuk daftar kegiatan lengkap.

4. **Update Kegiatan (Update)**  
   - User memilih kegiatan berdasarkan ID.  
   - Bisa ubah nama, tanggal (format `yyyy-MM-dd`), atau status (1=Selesai, 2=Belum Selesai, 3=Tidak diubah).  
   - Perubahan langsung disimpan ke database.

5. **Hapus Kegiatan (Delete)**  
   - User memilih ID kegiatan yang ingin dihapus.  
   - Data dihapus dari database menggunakan SQL `DELETE`.

6. **Cari Kegiatan (Search)**  
   - User memasukkan keyword.  
   - Program menampilkan hasil pencarian berdasarkan nama atau tipe kegiatan.

7. **Keluar Program**  
   - Program berhenti dan koneksi database ditutup dengan aman.

---

---

## Penerapan JDBC

Koneksi ke database dilakukan menggunakan **JDBC (Java Database Connectivity)**, yang dikelola lewat class `DatabaseConnection.java`.  
Berikut contoh-contoh penerapannya:

### 1. Koneksi ke Database
```java
Connection conn = DatabaseConnection.getConnection();
```

Menghubungkan program Java ke database MySQL dengan memanggil method getConnection() dari class DatabaseConnection. Koneksi ini digunakan di seluruh proses CRUD agar program bisa mengeksekusi perintah SQL. Tanpa koneksi ini, program tidak dapat berinteraksi dengan database.

### 2. Select (Menampilkan Data)
```java
String sql = "SELECT * FROM kegiatan";
PreparedStatement ps = conn.prepareStatement(sql);
ResultSet rs = ps.executeQuery();
while (rs.next()) {
    System.out.println(rs.getString("nama"));
}
```

Digunakan untuk menampilkan data kegiatan yang tersimpan dalam tabel kegiatan. Perintah SELECT mengambil semua baris dari tabel, lalu hasilnya disimpan di ResultSet yang akan di-loop dan ditampilkan ke user. Bagian ini digunakan ketika user memilih menu Lihat Kegiatan atau Cari Kegiatan.

### 3. Insert (Menambah Data)
```java
String sql = "INSERT INTO kegiatan (nama, tanggal, status, tipe, tambahan) VALUES (?, ?, ?, ?, ?)";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, nama);
ps.setDate(2, Date.valueOf(tanggal));
ps.setString(3, status);
ps.setString(4, tipe);
ps.setString(5, tambahan);
ps.executeUpdate();
```

Berfungsi untuk menambahkan data baru ke tabel kegiatan berdasarkan input user. Data yang diisi melalui menu Tambah Kegiatan akan dikirim ke database menggunakan query SQL ini. PreparedStatement digunakan agar data yang dikirim lebih aman dan mencegah SQL Injection. Setelah perintah executeUpdate() dijalankan, data baru akan langsung muncul di tabel database.

### 4. Update
```java
String sql = "UPDATE kegiatan SET nama=?, tanggal=?, status=? WHERE id=?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, namaBaru);
ps.setDate(2, Date.valueOf(tanggalBaru));
ps.setString(3, statusBaru);
ps.setInt(4, id);
ps.executeUpdate();
```

Menjalankan proses pembaruan data kegiatan tertentu. Ketika user memilih menu Update Kegiatan, program akan menanyakan ID kegiatan yang ingin diubah, lalu mengeksekusi perintah SQL UPDATE. Perubahan langsung tersimpan di database tanpa perlu restart program. Jika user memilih status 3 (Tidak diubah), data lama akan tetap dipertahankan.

### 5. Delete (Menghapus Data)
```java
String sql = "DELETE FROM kegiatan WHERE id=?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setInt(1, id);
ps.executeUpdate();
```

Menghapus satu baris data dari tabel kegiatan berdasarkan ID yang dimasukkan user. Digunakan saat user memilih menu Hapus Kegiatan. Setelah dijalankan, data otomatis terhapus dari database tanpa perlu dihapus manual melalui phpMyAdmin atau MySql.

## Penerapan ORM
```java
switch (rs.getString("tipe")) {
    case "kuliah" -> new KegiatanKuliah(nama, tanggal, status, tambahan);
    case "organisasi" -> new KegiatanOrganisasi(nama, tanggal, status);
    case "praktikum" -> new KegiatanPraktikum(nama, tanggal, status, tambahan);
}
```

Mengubah setiap baris data dari tabel kegiatan menjadi objek kegiatan sesuai tipenya. Contohnya, jika kolom tipe berisi "kuliah", maka objek yang dibuat adalah KegiatanKuliah. Hal ini membuat program bisa menangani berbagai jenis kegiatan tanpa perlu menulis query SQL yang berbeda untuk setiap tipe.
