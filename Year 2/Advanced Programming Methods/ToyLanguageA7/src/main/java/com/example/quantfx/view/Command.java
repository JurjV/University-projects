package com.example.quantfx.view;

public abstract class Command
{
    private final String _key;
    private final String _description;

    public Command(String key, String description)
    {
        _key = key;
        _description = description;
    }

    public String getKey()
    {
        return _key;
    }

    public String getDescription()
    {
        return _description;
    }

    public abstract void execute();
}
