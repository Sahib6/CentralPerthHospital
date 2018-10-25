package utils;

import java.io.*;

public class Configuration implements Serializable
{

    private static final long serialVersionUID = 4609664794198994294L;
    private  String color;
    private  String font;


    public Configuration(String color, String font)
    {
        this.color = color;
        this.font = font;
    }

    private static Configuration readFromFile()
    {
        try {
            FileInputStream fis = new FileInputStream(new File("config.bin"));
            ObjectInputStream ois = new ObjectInputStream(fis);

            Configuration config = (Configuration) ois.readObject();


            fis.close();
            ois.close();

            return  config;


        }
        catch(Exception ex){ex.printStackTrace(); return null;}
    }

    public void writeToFile()
    {

        try
        {
            FileOutputStream fos = new FileOutputStream(new File("config.bin"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            Configuration config = new Configuration(this.color, this.font);
            oos.writeObject(config);

            fos.close();
            oos.close();
        }
        catch (Exception ex)
        {ex.printStackTrace();}

    }

    public static String getColor()
    {
        String color = "800080";
        if(readFromFile().color != null)
            color = readFromFile().color;

        return color;
    }

    public static String getFont()
    {
        String font = "12";
        if(readFromFile().font != null)
            font = readFromFile().font;

            return font;
    }

}
