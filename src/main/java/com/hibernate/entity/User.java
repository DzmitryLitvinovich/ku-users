package com.hibernate.entity;



import javax.persistence.*;
import javax.xml.namespace.QName;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    private int age;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "inserted_date_at_utc")
    private LocalDateTime insertedDateAtUtc;

    // Конструкторы, геттеры и сеттеры

    // Конструктор по умолчанию
    public void UserHibernate() {
    }

    // Конструктор с параметрами
    public void UserHibernate(Long id, String name, String surname, int age, String username, String password, LocalDateTime insertedDateAtUtc) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.username = username;
        this.password = password;
        this.insertedDateAtUtc = insertedDateAtUtc;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getInsertedDateAtUtc() {
        return insertedDateAtUtc;
    }

    public void setInsertedDateAtUtc(LocalDateTime insertedDateAtUtc) {
        this.insertedDateAtUtc = insertedDateAtUtc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(insertedDateAtUtc, user.insertedDateAtUtc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, age, username, password, insertedDateAtUtc);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", insertedDateAtUtc=" + insertedDateAtUtc +
                '}';
    }
}
