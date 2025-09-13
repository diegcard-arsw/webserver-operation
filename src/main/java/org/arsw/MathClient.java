package org.arsw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Cliente para probar el servidor de operaciones matemáticas {@link MathServer}.
 * Conecta por defecto a {@code 127.0.0.1:35000} y permite enviar números
 * y comandos de función.
 */
public class MathClient {

    /**
     * Inicia una conexión TCP al servidor de matemáticas y reenvía
     * lo que se escriba por la entrada estándar al servidor.
     *
     * @param args no se utilizan
     * @throws IOException si ocurre un error al conectar o transmitir datos
     */
    public static void main(String[] args) throws IOException {
        Socket mathSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        
        try {
            mathSocket = new Socket("127.0.0.1", 35000);
            out = new PrintWriter(mathSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    mathSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("No se conoce el host: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se pudo obtener E/S para la conexión: " + e.getMessage());
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        
        // Mostrar mensaje de bienvenida del servidor
        System.out.println(in.readLine());
        
        System.out.println("\nComandos disponibles:");
        System.out.println("  fun:sin - cambiar a función seno");
        System.out.println("  fun:cos - cambiar a función coseno");
        System.out.println("  fun:tan - cambiar a función tangente");
        System.out.println("  [número] - calcular función actual sobre el número");
        System.out.println("  exit - salir\n");
        
        String userInput;
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            String response = in.readLine();
            System.out.println("Respuesta: " + response);
            
            if (userInput.equals("exit") || userInput.equals("quit") || userInput.equals("Adiós.")) {
                break;
            }
        }
        
        out.close();
        in.close();
        stdIn.close();
        mathSocket.close();
    }
}