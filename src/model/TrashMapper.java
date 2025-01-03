package model;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TrashMapper {
    @Select("SELECT t.id_kategori AS id, t.nama_kategori AS categoryName, t.total_sampah_dijemput AS total, t.total_poin AS point, t.id_dropbox AS idDropbox, d.nama_lokasi AS dropboxName FROM kategori_sampah t JOIN dropbox_sampah d ON t.id_dropbox = d.id_dropbox ORDER BY t.id_kategori")
    List<Trash> getAllTrash();

    @Insert("INSERT INTO kategori_sampah (nama_kategori, total_sampah_dijemput, total_poin, id_dropbox) VALUES (#{categoryName}, #{total}, #{point}, #{idDropbox})")
    void insertTrash(Trash trash);

    @Update("UPDATE kategori_sampah SET nama_kategori = #{categoryName}, total_sampah_dijemput = #{total}, total_poin = #{point}, id_dropbox = #{idDropbox} WHERE id_kategori = #{id}")
    void updateTrash(Trash trash);

    @Delete("DELETE FROM kategori_sampah WHERE id_kategori = #{id}")
    void deleteTrash(int id);

    @Select("SELECT id_dropbox AS idDropbox, nama_lokasi AS dropboxName FROM dropbox_sampah")
    List<Dropbox> getAllDropbox();
}
