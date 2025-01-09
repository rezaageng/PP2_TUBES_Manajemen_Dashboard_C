package model;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {
    @Select("SELECT id, nama AS name, total_transaksi AS totalTransaction FROM pengguna")
    List<User> getAllUser();

    @Insert("INSERT INTO pengguna (nama, total_transaksi) VALUES (#{name}, #{totalTransaction})")
    void insertUser(User user);

    @Update("UPDATE pengguna SET nama = #{name}, total_transaksi = #{totalTransaction} WHERE id = #{id}")
    void updateUser(User user);

    @Delete("DELETE FROM pengguna WHERE id = #{id}")
    void deleteUser(int id);
}
