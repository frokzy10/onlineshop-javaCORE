package application.serialize;

import java.io.*;
import java.util.List;

public class Serialize {
    private final String pathname;
    private final File file;

    public Serialize(String pathname) {
        this.pathname = pathname;
        this.file = new File(pathname);
    }

    public <E> void serialize(List<E> dataSerialize) {
        try (FileOutputStream fos = new FileOutputStream(pathname)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dataSerialize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> deserialize() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(pathname))) {
            return (List<T>) objectInputStream.readObject();
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
