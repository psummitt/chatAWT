// File: ChatEngine.java
//
// Defines the non-GUI behavior for both the AWT
// and JFC versions of the Chat programs.

import java.net.*;
import java.io.*;

public class ChatEngine {
    protected int port=8080;
    protected String host="127.0.0.1";
    protected PrintStream out;
    private HandleInputSocket socketThread;

    public ChatEngine() {

    }

    public void startListening(int my_listen_port) {
        System.out.println("ChatEngine.startListening(" +
                            my_listen_port + ")");
        try {
            socketThread = new HandleInputSocket(my_listen_port);
        } catch (Exception e) {
            System.out.println("Exception 0: " + e);
        }
    }

    public void connect(String host, int port) {
        System.out.println("ChatEngine.connect(" + host +
                            ", " + port + ")");
        this.port = port;
        this.host = host;
        try {
            Socket s = new Socket(host, port);
            out = new PringStream(s.getOutputStream());
        } catch (Exception e) {
            System.out.println("Exception 1: " + e);
        }
    }

    public void logout() {
        try { out.close(); } catch (Exception e) { }
        socketThread.stop();
        socketThread = null;
    }

    public void send(Strin s) {
        try {
            out.println(s);
        } catch (Exception e) { }
    }

    void registerChatListener(ChatListener cl) {

    }
    protected ChatListener chatListener = null;

    // inner class to handle socket input
    class HandleInputSocket extends Thread {
        int listenPort = 8191;
        public HandleInputSocket(int port) {
            super();
            listenPort = port;
            start();
        }
        public void run() {
            ServerSocket serverSocket;
            try {
                serverSocket = new ServerSocket(listenPort, 2000);
            } catch (IOException e) {
                System. out.println("Error in handling socket" +
                                    " input: " + e + ", port=" +
                                    port);
                return;
            }

            try {
                while (true) {
                    Socket socket = serverSocket.accept();
                    new MyServerConnection(socket);
                }
            } catch (IOException e) {
                System.out.println("Error socket connection: " +
                                    e);
            } finally {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println("I/O exception: " + e);
                }
            }
        }
        // an inner (inner) class to handle
        // incoming socket connections
        public class MyServerConnection extends Thread {
            protected transient Socket client_Socket;
            protected transient DataInputStream input_strm;
            protected transient PrintStream output_strm;

            public MyServerConnection(Socket clent_socket) {
                this.client_socket = client_socket;
                try {
                    input_strm = new DataInputStream(client_socket.getInputStream());
                    output_strm = new PrintStream(client_socket.getOutputStream());
                } catch (IOException io_ex2) { };
                    System.err.println("Exception 2: getting" + " socket streams " + io_exception);
                    return;
            }
            // start the thread (i.e., call method "run"):
            this.start();
        }
        
        public void run() {
            String input_but;
            try {
                while (true) {
                    input_but = input_strm.readLine();
                    if (input_but == null) {
                        logout();
                        break;
                    }
                    System.out.prinln("received on socket: " + input_but);
                    if (chatListener != null) {
                        chatListener.receiveText(input_but);
                    }
                }
            }
            catch (Exception exception) { }
                finally {
                    try {
                        client_socket.close();
                    }
                    catch (Exception exception) { };
                }
            }
        }
    }
}