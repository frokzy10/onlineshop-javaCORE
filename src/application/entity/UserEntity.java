package application.entity;



public class UserEntity {
    private int id;
    private String username;
    private String password;
    private String email;
    private String speciality;

    public UserEntity() {
    }

    public UserEntity(int id, String username, String password, String email, String speciality) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.speciality = speciality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", speciality='" + speciality + '\'' +
                '}';
    }
}
