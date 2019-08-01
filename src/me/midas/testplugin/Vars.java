package me.midas.testplugin;

import java.util.ArrayList;
import java.util.List;

class Vars {

    public static List<Client> Players = new ArrayList();

    public static boolean isPlayerAlreadyInServer(Client player)
    {
        for (Client c : Players) if(c.getName().hashCode() == player.getName().hashCode() && c.getUUID().hashCode() == player.getUUID().hashCode()) return  true;
        return false;
    }
}
