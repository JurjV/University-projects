package com.example.quantfx.view;

import com.example.quantfx.controller.GUIController;

public class RunExample extends Command
{
    private GUIController _controller;

    public RunExample(String key, String description, GUIController controller)
    {
        super(key, description);
        _controller = controller;
    }

    public GUIController getController()
    {
        return _controller;
    }

    @Override
    public void execute()
    {
        try
        {
            _controller.oneStep();
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }
}
