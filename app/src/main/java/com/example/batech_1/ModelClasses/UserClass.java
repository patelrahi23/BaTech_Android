package com.example.batech_1.ModelClasses;

public class UserClass {

String fname, email,pass, ph_number, gender, DOB, organisation;

    public UserClass(String fname, String email, String pass, String ph_number, String gender, String DOB, String organisation) {
        this.fname = fname;
        this.email = email;
        this.pass = pass;
        this.ph_number = ph_number;
        this.gender = gender;
        this.DOB = DOB;
        this.organisation = organisation;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPh_number() {
        return ph_number;
    }

    public void setPh_number(String ph_number) {
        this.ph_number = ph_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }
}
