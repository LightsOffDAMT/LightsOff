package ru.lightsoff.database.Entities;

public class User {
    private Long id;
    private String login;
    private String password;
    private String email;
    private String nickname;
    private Boolean online;

    public User(){
        super();
    }

    public User withId(Long id){
        this.id = id;
        return this;
    }

    public User withLogin(String login){
        this.login = login;
        return this;
    }

    public User withPassword(String password){
        this.password = password;
        return this;
    }

    public User withEmail(String email){
        this.password = password;
        return this;
    }

    public User withNickname(String nickname){
        this.nickname = nickname;
        return this;
    }

    public User online(Boolean online){
        this.online = online;
        return this;
    }
}
