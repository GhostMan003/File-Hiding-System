package Model;

public class Data_Info {
    private int dataid;
    private String datafilename;
    private String datapath;
    private String email;
    //ID,FILE_NAME,PATH,EMAIL,BIN_DATA

    public Data_Info(int id, String filename, String path, String email) {
        this.dataid = id;
        this.datafilename = filename;
        this.datapath = path;
        this.email = email;
    }

    public int getId() {
        return dataid;
    }

    public void setId(int id) {
        this.dataid = id;
    }

    public Data_Info(int id, String filename, String path) {
        this.dataid = id;
        this.datafilename = filename;
        this.datapath = path;
    }

    public String getFilename() {
        return datafilename;
    }

    public void setFilename(String filename) {
        this.datafilename = filename;
    }

    public String getPath() {
        return datapath;
    }

    public void setPath(String path) {
        this.datapath = path;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
