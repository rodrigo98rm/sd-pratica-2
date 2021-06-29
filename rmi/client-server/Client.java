import java.net.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

class Client extends Thread {
  InetAddress host = null;
  Socket socket = null;
  ObjectOutputStream oos = null;
  ObjectInputStream ois = null;

  public void run() {
    try {
      Properties props = System.getProperties();

      host = InetAddress.getLocalHost();
      socket = new Socket(host.getHostName(), 4444);
      oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(props);

      ois = new ObjectInputStream(socket.getInputStream());
      Properties receivedProps = (Properties) ois.readObject();

      for (Enumeration<?> names = receivedProps.propertyNames(); names.hasMoreElements();) {
        String property = (String) names.nextElement();
        System.out.println(property + ": " + receivedProps.getProperty(property));
      }

      ois.close();
      oos.close();
      socket.close();

    } catch (Exception e) {
    }
  }

  public static void main(String args[]) {
    new Client().start();
  }
}