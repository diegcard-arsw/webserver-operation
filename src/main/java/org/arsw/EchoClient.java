package org.arsw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Cliente de eco simple para pruebas de conectividad con {@link EchoServer} u
 * otros servidores de texto. Lee desde stdin, envía cada línea al servidor y
 * muestra la respuesta recibida.
 */
public class EchoClient {

    /**
     * Inicia una conexión TCP por defecto a {@code 127.0.0.1:35000} y reenvía
     * lo que se escriba por la entrada estándar al servidor, imprimiendo su
     * respuesta en consola.
     *
     * @param args no se utilizan
     * @throws IOException si ocurre un error al conectar o transmitir datos
     */
    public static void main(String[] args) throws IOException {
        Socket echSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            echSocket = new Socket("127.0.0.1", 35000);
            out = new PrintWriter(echSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    echSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("No se conoce el host: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se pudo obtener E/S para la conexión: " + e.getMessage());
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        String userInput;
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("echo: " + in.readLine());
        }
        out.close();
        in.close();
        stdIn.close();
        echSocket.close();
    }
}