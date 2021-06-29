import java.net.*;
import java.io.*;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

class Server extends Thread {
   Socket socket = null;
   ObjectInputStream ois = null;
   ObjectOutputStream oos = null;
   Properties prop = null;

   public Properties readProp() {
      try {
         Properties aProp = (Properties) ois.readObject();
         ois.close();
         return aProp;
      } catch (Exception e) {
         e.printStackTrace();
      }
      return null;
   }

   public Hashtable<String, String> diff(Properties aProp) {
      this.prop = System.getProperties();
      Hashtable<String, String> res = new Hashtable<String, String>();
      
      for (Enumeration<?> names = prop.propertyNames(); names.hasMoreElements();) {
         String property = (String) names.nextElement();
         String value1 = prop.getProperty(property);
         String value2 = aProp.getProperty(property);
         if (!value1.equals(value2)) { // add to hashtable
            res.put(property, value1 + " / " + value2);
         }
      }
      return res;
   }

   public void run() {
      try {
         ServerSocket server = new ServerSocket(4444);
         while (true) {
            socket = server.accept();
            ois = new ObjectInputStream(socket.getInputStream());

            Properties aProp = this.readProp();
            Hashtable<String, String> hash = this.diff(aProp);

            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(hash);
            ois.close();
            oos.close();
            socket.close();
         }
      } catch (Exception e) {
      }
   }

   public static void main(String args[]) {
      new Server().start();
   }
}