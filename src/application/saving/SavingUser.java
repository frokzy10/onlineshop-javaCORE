package application.saving;

import application.entity.UserEntity;

import java.io.Serializable;
import java.util.Arrays;

public class SavingUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private UserEntity[] userEntities;

    public SavingUser(UserEntity[] userEntities) {
        this.userEntities = userEntities;
    }

    public UserEntity[] getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(UserEntity[] userEntities) {
        this.userEntities = userEntities;
    }

    @Override
    public String toString() {
        return "SavingUser{" +
                "userEntities=" + Arrays.toString(userEntities) +
                '}';
    }
}
