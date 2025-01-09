package model;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface HistoryMapper {
    @Select("SELECT h.id, h.transaksi AS transaction, h.id_kurir AS CourierId, c.nama AS courierName, h.id_pengguna as userId, u.nama AS userName, h.id_dropbox AS dropboxId, d.nama_lokasi AS dropboxName, h.timestamp " +
            "FROM riwayat h " +
            "JOIN kurir c ON h.id_kurir = c.id " +
            "JOIN pengguna u ON h.id_pengguna = u.id " +
            "JOIN dropbox_sampah d ON h.id_dropbox = d.id_dropbox")
    List<History> getAllHistories();

    @Insert("INSERT INTO riwayat (transaksi, id_kurir, id_pengguna, id_dropbox) VALUES (#{transaction}, #{courierId}, #{userId}, #{dropboxId})")
    void insertHistory(History history);

    @Update("UPDATE riwayat SET transaksi = #{transaction}, id_kurir = #{courierId}, id_pengguna = #{userId}, id_dropbox = #{dropboxId} WHERE id = #{id}")
    void updateHistory(History history);

    @Delete("DELETE FROM riwayat WHERE id = #{id}")
    void deleteHistory(int id);

    @Select("SELECT id_dropbox AS idDropbox, nama_lokasi AS dropboxName FROM dropbox_sampah")
    List<Dropbox> getAllDropbox();

    @Select("SELECT id, nama AS name FROM kurir")
    List<Courier> getAllCourier();

    @Select("SELECT id, nama AS name FROM pengguna")
    List<User> getAllUser();
}
