import java.net.*;
import java.io.*;

class MySockets {
  public static void main(String args[]) {
    new Server().start();
    new Client().start();
  }
}

class Server extends Thread {
  Socket socket = null;
  ObjectInputStream ois = null;
  ObjectOutputStream oos = null;

  public void run() {
    try {
      ServerSocket server = new ServerSocket(4444);
      while (true) {
        socket = server.accept();
        ois = new ObjectInputStream(socket.getInputStream());
        String message = (String) ois.readObject();
        System.out.println("Server Received: " + message);
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("Server Reply");
        ois.close();
        oos.close();
        socket.close();
      }
    } catch (Exception e) {
    }
  }
}

class Client extends Thread {
  InetAddress host = null;
  Socket socket = null;
  ObjectOutputStream oos = null;
  ObjectInputStream ois = null;

  public void run() {
    try {
      for (int x = 0; x < 5; x++) {
        host = InetAddress.getLocalHost();
        socket = new Socket(host.getHostName(), 4444);
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("Client Message " + x);
        ois = new ObjectInputStream(socket.getInputStream());
        String message = (String) ois.readObject();
        System.out.println("Client Received: " + message);
        ois.close();
        oos.close();
        socket.close();
      }
    } catch (Exception e) {
    }
  }
}