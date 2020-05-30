package com.example.khuisf.entitys;

public class StudentAttendancer {
    String name, code, pic;
    private boolean expanded;

    //1=hozoor //0=gheybar // 2=null
    int status;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public StudentAttendancer(String name, String code, int status, String pic) {
        this.name = name;
        this.code = code;
        this.pic = pic;
        this.status = status;
        this.expanded = false;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getPic() {
        return pic;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
