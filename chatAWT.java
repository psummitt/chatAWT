// File: ChatAWT.java

import java.awt.*;
import java.awt.event.*;

public class ChatAWT extends Frame implements ChatListener{
    Panel panel1 = new Panel();
    Label label1 = new Label();
    TextField myPortField = new TextField();
    Label label2 = new Label();
    TextField hostField = new TextField();
    Label label3 = new Label();
    TextField portField = new TextField();
    Label label4 = new Label();
    TextField inputField = new TextField();
    Label label5 = new Label();
    Button connectButton = new Button();
    Button disconnectButton = new Button();
    Button quitButton = new Button();
    TextArea outputField = new TextArea();

    protected ChatEngine chatEngine;
    Button listenButton = new Button();

    public ChatAWT() {
        super("Chat with AWT GUI");
        chatEngine = new ChatEngine();
        
    }
}
