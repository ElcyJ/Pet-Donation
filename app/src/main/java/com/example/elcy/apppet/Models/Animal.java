package com.example.elcy.apppet.Models;

public class Animal {

    private String owner;
    private String sex;
    private Integer age;

    public Animal(String owner, String sex, Integer age) {
        this.owner = owner;
        this.sex = sex;
        this.age = age;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


}
