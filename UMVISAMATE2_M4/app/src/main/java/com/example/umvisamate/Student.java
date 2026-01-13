package com.example.umvisamate;

import java.io.Serializable;

public class Student implements Serializable {
    private String name;
    private String passportId; // 对应你 ID 显示的内容
    private String status;

    public Student(String name, String passportId, String status) {
        this.name = name;
        this.passportId = passportId;
        this.status = status;
    }

    public String getName() { return name; }
    public String getPassportId() { return passportId; }
    public String getStatus() { return status; }
}