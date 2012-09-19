package controle;

import java.io.*;
import java.net.*;

class Client {
  public static void main(String[] args)
    throws Exception
  {
    BufferedReader inFromUser =
      new BufferedReader(new InputStreamReader(System.in));

    System.out.print("Enter server host name or IP addess: ");
    System.out.flush();

    String serverHost = inFromUser.readLine();

    System.out.print("Enter server port number: ");
    System.out.flush();

    int serverPort = Integer.parseInt(inFromUser.readLine());

    Socket clientSocket = new Socket(serverHost, serverPort);

    DataOutputStream outToServer =
      new DataOutputStream(clientSocket.getOutputStream());
    BufferedReader inFromServer =
      new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    System.out.print("Input: ");
    System.out.flush();
    String line = inFromUser.readLine();

    while (!line.equals(".")) {
      outToServer.writeBytes(line + '\n');

      String modifiedSentence = inFromServer.readLine();
      System.out.println("Modified: " + modifiedSentence);

      System.out.print("Input: ");
      System.out.flush();
      line = inFromUser.readLine();
    }

    clientSocket.close();
  }
}