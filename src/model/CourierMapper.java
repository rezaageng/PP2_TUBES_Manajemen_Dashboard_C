package model;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CourierMapper {
    @Select("SELECT id, nama AS name, tanggal_transaksi AS transactionDate, total_antar AS total FROM kurir ORDER BY id")
    List<Courier> getAllCourier();

    @Insert("INSERT INTO kurir (nama, tanggal_transaksi, total_antar) VALUES (#{name}, #{transactionDate}, #{total})")
    void insertCourier(Courier courier);

    @Update("UPDATE kurir SET nama = #{name}, tanggal_transaksi = #{transactionDate}, total_antar = #{total} WHERE id = #{id}")
    void updateCourier(Courier courier);

    @Delete("DELETE FROM kurir WHERE id = #{id}")
    void deleteCourier(int id);
}
