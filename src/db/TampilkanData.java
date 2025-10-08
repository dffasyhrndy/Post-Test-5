package db;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TampilkanData {
    public void lihatDataTabel() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT * FROM kegiatan ORDER BY id ASC";
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.isBeforeFirst()) {
                System.out.println("Belum ada data kegiatan di database.");
                return;
            }

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + ". " +
                    rs.getString("nama") + " | " +
                    rs.getDate("tanggal") + " | " +
                    rs.getString("status") + " | " +
                    rs.getString("tipe") +
                    (rs.getString("tambahan") != null ? " | " + rs.getString("tambahan") : "")
                );
            }

        } catch (Exception e) {
            System.out.println("Gagal menampilkan data: " + e.getMessage());
        }
    }
}
