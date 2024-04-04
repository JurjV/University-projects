package com.example.quantfx.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu
{
    private Map<String, Command> _commands;

    public TextMenu()
    {
        _commands = new HashMap<>();
    }

    public void addCommand(Command command)
    {
        _commands.put(command.getKey(), command);
    }

    private void printMenu()
    {
        for (Command command : _commands.values())
        {
            String line = String.format("%4s : %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    public void show()
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            printMenu();
            System.out.printf("Input the option: ");
            String key = scanner.nextLine();
            Command command = _commands.get(key);
            if (command == null)
            {
                System.out.println("Invalid Option");
                continue;
            }

            command.execute();
        }
    }
}
