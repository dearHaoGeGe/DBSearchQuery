package com.my.dbsearchquery.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "CASE_INFO".
 */
public class CaseInfo {

    private Long id;
    private String name;
    private Long time;
    private String number;

    public CaseInfo() {
    }

    public CaseInfo(Long id) {
        this.id = id;
    }

    public CaseInfo(Long id, String name, Long time, String number) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
