package com.airtribe.meditrack.entity;

public class Person extends MedicalEntity{
    private String name;
    private int age;

    protected Person() {}
    public Person(String id, String name, int age) {
        this.id = id; this.name = name; this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }

    @Override public String toString() {
        return getClass().getSimpleName() + "{id='" + id + "', name='" + name + "', age=" + age + "}";
    }
}