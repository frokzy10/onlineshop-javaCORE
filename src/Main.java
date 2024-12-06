import java.util.Scanner;
import application.Application;
import application.dataHandlers.UserDataHandler;

public class Main {
    public static void main(String[] args)  {
        System.out.println("Программа запущена");
        getStarted().run();
        System.out.println("Программа завершена");
    }
    public static Application getStarted(){
        Scanner scanner = new Scanner(System.in);
        UserDataHandler userDataHandler = new UserDataHandler();

        return new Application (
                scanner,
                userDataHandler
        );
    }
}