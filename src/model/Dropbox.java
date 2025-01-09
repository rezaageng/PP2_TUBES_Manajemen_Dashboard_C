package model;

public class Dropbox {
    private int idDropbox;
    private String dropboxName;

    public int getIdDropbox() {
        return idDropbox;
    }

    public void setIdDropbox(int idDropbox) {
        this.idDropbox = idDropbox;
    }

    public String getDropboxName() {
        return dropboxName;
    }

    public void setDropboxName(String dropboxName) {
        this.dropboxName = dropboxName;
    }

    @Override
    public String toString() {
        return dropboxName;
    }
}
