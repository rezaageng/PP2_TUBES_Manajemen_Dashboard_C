package model;

public class Trash {
    private int id;
    private String categoryName;
    private int total;
    private int point;
    private int idDropbox;
    private String dropboxName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getIdDropbox() {
        return idDropbox;
    }

    public void setIdDropbox(int idDropbox) {
        this.idDropbox = idDropbox;
    }

    public String getDropboxName() {
        return dropboxName;
    }
}
