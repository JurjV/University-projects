package view;

import controller.Controller;
import controller.MyException;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class RunExampleCommand extends Command {
    private final Controller controller;

    public RunExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            int ok;
            while (true) {
                System.out.println("Do you want to display the steps?[Y/N]");
                Scanner readOption = new Scanner(System.in);
                String opt = readOption.next();
                if (opt.equals("Y")) {
                    ok = 1;
                    break;
                } else if (opt.equals("N")) {
                    ok = 0;
                    break;
                } else System.out.println("Incorrect input!");
            }
            controller.allStep(ok);
        } catch (MyException | IOException exception) {
            System.out.println("\u001B[31m" + exception.getMessage() + "\u001B[0m");
        }
    }
}