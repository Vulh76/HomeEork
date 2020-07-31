package ru.stb.domain.entity.user;

import ru.stb.domain.dao.Column;
import ru.stb.domain.dao.Table;
import ru.stb.domain.dao.Id;

@Table("USER")
public class User {

    @Id("ID")
    private Long id;

    @Column("NAME")
    private String name;

    @Column("AGE")
    private Integer age;

    @Column("TELEPHONE")
    private String telephone;

    public User(Long id, String name, Integer age, String telephone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.telephone = telephone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getTelephone() {
        return telephone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
