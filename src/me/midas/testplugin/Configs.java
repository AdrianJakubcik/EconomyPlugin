package me.midas.testplugin;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class Configs {
    File cfg = FileHandler.f;
    String[] defaultcfg = {
            "",
            "",
            "",
    };



        public Configs(){

        }

    private boolean DeafultCFG(){
            try{
                for (String s: defaultcfg) {
                    appendStrToFile(FileHandler.File_Configs,s);
                }
                return true;
            }catch (Exception e)
            {
                Log.LogWriteConsole("There was an error while inserting default configs... ");
                e.printStackTrace();
                return false;
            }
    }

    public static void appendStrToFile(String fileName,
                                       String str)
    {
        try {

            // Open given file in append mode.
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(fileName, true));
            out.write(str);
            out.close();
        }
        catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
    }




}
