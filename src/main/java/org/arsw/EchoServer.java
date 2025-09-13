package org.arsw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor de eco simple para demostración y pruebas.
 * Lee líneas del cliente y responde la misma línea prefijada con "Eco:".
 * Finaliza cuando recibe exactamente {@code "Adiós."}.
 *
 * <p>Este servidor es de un solo cliente y bloqueante, útil para ejemplos de
 * sockets TCP.</p>
 */
public class EchoServer {

    /**
     * Inicia el servidor de eco en el puerto 35000 y atiende una única conexión.
     *
     * @param args no se utilizan
     * @throws IOException si ocurre un error de E/S al abrir el socket o comunicar
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e){
            System.err.println("No se pudo escuchar en el puerto: " + e.getMessage());
            System.exit(1);
        }
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Fallo al aceptar la conexión: " + e.getMessage());
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibido: " + inputLine);
            out.println("Eco: " + inputLine);
            if (inputLine.equals("Adiós.")) {
                break;
            }
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}