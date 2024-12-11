package application.file_worker;


import application.entity.UserEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserFileWorker {
    private String pathname;
    private File file;

    public UserFileWorker(String pathname) {
        this.pathname = pathname;
        this.file = new File(pathname);
    }

    public void writeData(List<UserEntity> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathname, true))) {
            for (UserEntity item : data) {
                System.out.println(data);
                writer.write(item.getUsername() + "," + item.getEmail() + "," + item.getPassword() + "," + item.getSpeciality());
                writer.newLine();
            }
            writer.flush();
            System.out.println("Данные успешно записаны.");
        } catch (IOException e) {
            System.out.println("Ошибка при записи данных: " + e.getMessage());
        }
    }


    public List<UserEntity> readData() {
        List<UserEntity> userList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(pathname))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    UserEntity user = new UserEntity();
                    user.setUsername(parts[0]);
                    user.setEmail(parts[1]);
                    user.setPassword(parts[2]);
                    user.setSpeciality(parts[3]);

                    userList.add(user);
                } else {
                    System.out.println("Ошибка в строке: " + line);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return userList;
    }
}
