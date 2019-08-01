package me.midas.testplugin;


import java.io.File;

class FileHandler{
    public static String Dir = "plugins\\TestPlugin";
    public static String File_Configs = Dir + "\\config";
    public static File f = new File(File_Configs);
    private File dir = new File(Dir);


    public FileHandler()
    {
        if (!direxst())
        {
            if(!dircrt())
            {
                return;
            }
        }
        if(!files()) {
            if(!filescreat())
            {
                return;
            }
            Log.LogWriteConsole("Default Files Have Been Generated...");
        }
    }


    private boolean files()
    {
        try
        {
            return f.exists() && !f.isDirectory();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean filescreat()
    {
        try
        {
            f.createNewFile();
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean direxst(){
        try
        {
            return dir.exists() && dir.isDirectory();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean dircrt(){
        try
        {
            dir.mkdir();
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
