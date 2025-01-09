package model;

public class History {
    private int id;
    private String transaction;
    private int courierId;
    private String courierName;
    private int userId;
    private String userName;
    private int dropboxId;
    private String dropboxName;
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getDropboxId() {
        return dropboxId;
    }

    public void setDropboxId(int dropboxId) {
        this.dropboxId = dropboxId;
    }

    public String getDropboxName() {
        return dropboxName;
    }

    public void setDropboxName(String dropboxName) {
        this.dropboxName = dropboxName;
    }
}
