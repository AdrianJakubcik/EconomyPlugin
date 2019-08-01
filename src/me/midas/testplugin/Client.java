package me.midas.testplugin;

class Client
{
    private String _username;
    private String _uuid;
    private String _ip;
    private double _balance;

    public Client(String username, String UUID, String IP, double balance)
    {
        this._username = username;
        this._uuid = UUID;
        this._ip = IP;
        this._balance = balance;
    }

    public String getName() { return this._username; }
    public String getUUID() { return this._uuid; }
    public String getIP() { return this._ip; }
    public double getBalance() { return this._balance; }
}
