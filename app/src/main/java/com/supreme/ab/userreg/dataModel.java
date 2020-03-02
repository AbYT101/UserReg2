package com.supreme.ab.userreg;

class dataModel {
    private String id="";
    protected String fullName="";
    protected String userName="";
    protected String gender="";
    protected String email="";
    protected String phoneNo="";
    private String Password="";
    public dataModel(String fullName){
        this.fullName=fullName;
    }
    public dataModel (String id,String fullName, String userName,String gender,String email,String phoneNo,String Password){
        this.id=id;
        this.fullName=fullName;
        this.userName=userName;
        this.gender=gender;
        this.email=email;
        this.phoneNo=phoneNo;
        this.Password=Password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
