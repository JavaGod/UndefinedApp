package monki.study.undefinedapp.entity;

public class User {
    public int id;
    public String account;
    public String password;
    public boolean isRemember;

    public User(){}

    public User(String account, String password, boolean isRemember) {
        this.account = account;
        this.password = password;
        this.isRemember = isRemember;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", isRemember=" + isRemember +
                '}';
    }
}
