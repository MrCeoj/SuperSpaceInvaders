package com.uabc.client;

/*
    Marcelo Ortiz - Giselle Solorio - Alan Banagas
 */
import com.uabc.spaceinvaders.Menu;
import com.uabc.spaceinvaders.Space;
import com.uabc.spaceinvaders.Waiting;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client implements Runnable {

    private Socket cliente;
    private DataOutputStream out;
    private DataInputStream in;
    private int puerto = 2027;
    private String host = "localhost";

    private String mensaje;
    long id;

    private Space sp;

    public Client() {
        try {
            cliente = new Socket(host, puerto);
            in = new DataInputStream(cliente.getInputStream());
            out = new DataOutputStream(cliente.getOutputStream());
            id = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            mensaje = in.readUTF();
            System.out.println("Enviando: " + id + " " + mensaje);

            while (true) {
                mensaje = in.readUTF();
                String[] mensajes = mensaje.split(";");

                if (id == Long.parseLong(mensajes[0])) {
                    continue;
                }
                System.out.println("Desde otro cliente: " + mensajes[1]);
                if (mensajes[1].contains("Boss")) {
                    sp.boss();
                }
                if (mensajes[1].contains("Won")) {
                    sp.gameover();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviar(String mensaje_out) {
        try {
            System.out.println("Enviando: " + mensaje_out);
            out.writeUTF(id + ";" + mensaje_out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSpace(Space sp) {
        this.sp = sp;
    }

}
