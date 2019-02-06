package com.example.elcy.apppet.Models;

public class Animal {

    private String sex;
    private Integer age;

    public Animal(String sex, Integer age) {
        this.sex = sex;
        this.age = age;
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
