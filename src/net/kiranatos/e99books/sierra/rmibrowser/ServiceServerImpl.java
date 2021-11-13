package net.kiranatos.e99books.sierra.rmibrowser;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;


public class ServiceServerImpl extends UnicastRemoteObject implements ServiceServer {
    HashMap serviceList;

    public ServiceServerImpl() throws RemoteException {
        setUpServices();
    }
    
    private void setUpServices(){
        serviceList = new HashMap();
        serviceList.put("Dice Rolling Service", new DiceService());
        serviceList.put("Day of the Week Service", new DayOfTheWeekService());
        serviceList.put("Visula Music Service", new MiniMusicService());        
    }

    @Override
    public Object[] getServiceList() {
        System.out.println("in remote");
        return serviceList.keySet().toArray();
    }

    @Override
    public Service getService(Object serviceKey) throws RemoteException {
        Service theService = (Service) serviceList.get(serviceKey);
        return theService;
    }
    
    public static void main(String[] args) {
        try{
             //LocateRegistry.createRegistry( Registry.REGISTRY_PORT ); 
            Naming.rebind("ServiceServer", new ServiceServerImpl());
        } catch (Exception ex){ ex.printStackTrace(); }
        System.out.println("Remote service is running");        
    }
}
