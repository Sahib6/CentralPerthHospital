package utils;

import java.awt.*;

public class ScreenSize
{
    public static double getScreenWidth()
    {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    public static double getScreenHeight()
    {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }

}
