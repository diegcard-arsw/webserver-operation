package org.arsw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor de operaciones matemáticas que puede calcular seno, coseno y tangente.
 * Por defecto calcula coseno. Cambia de función cuando recibe un mensaje que
 * empiece con "fun:".
 * 
 * <p>Comandos soportados:</p>
 * <ul>
 *   <li>fun:sin - cambia a función seno</li>
 *   <li>fun:cos - cambia a función coseno</li>
 *   <li>fun:tan - cambia a función tangente</li>
 *   <li>[número] - calcula la función actual sobre el número</li>
 * </ul>
 */
public class MathServer {
    
    /**
     * Enumeración de las funciones matemáticas disponibles.
     */
    private enum MathFunction {
        SIN("seno"), COS("coseno"), TAN("tangente");
        
        private final String description;
        
        MathFunction(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    private static MathFunction currentFunction = MathFunction.COS; // Por defecto coseno

    /**
     * Inicia el servidor de operaciones matemáticas en el puerto 35000.
     *
     * @param args no se utilizan
     * @throws IOException si ocurre un error de E/S al abrir el socket o comunicar
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
            System.out.println("Servidor de operaciones matemáticas iniciado en puerto 35000");
            System.out.println("Función inicial: " + currentFunction.getDescription());
        } catch (IOException e) {
            System.err.println("No se pudo escuchar en el puerto: " + e.getMessage());
            System.exit(1);
        }
        
        Socket clientSocket = null;
        try {
            System.out.println("Esperando conexiones...");
            clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado desde: " + clientSocket.getInetAddress());
        } catch (IOException e) {
            System.err.println("Fallo al aceptar la conexión: " + e.getMessage());
            System.exit(1);
        }
        
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;
        
        // Mensaje de bienvenida
        out.println("Servidor de operaciones matemáticas. Función actual: " + currentFunction.getDescription());
        
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibido: " + inputLine);
            
            // Verificar si es un comando de cambio de función
            if (inputLine.startsWith("fun:")) {
                String response = handleFunctionChange(inputLine);
                out.println(response);
            } else {
                // Intentar procesar como número
                String response = processNumber(inputLine);
                out.println(response);
            }
            
            // Condición de salida
            if (inputLine.equals("exit") || inputLine.equals("quit") || inputLine.equals("Adiós.")) {
                break;
            }
        }
        
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("Servidor cerrado");
    }
    
    /**
     * Maneja el cambio de función matemática.
     * 
     * @param command comando que empieza con "fun:"
     * @return respuesta del servidor
     */
    private static String handleFunctionChange(String command) {
        try {
            String functionName = command.substring(4).trim().toLowerCase();
            
            switch (functionName) {
                case "sin":
                    currentFunction = MathFunction.SIN;
                    return "Función cambiada a: " + currentFunction.getDescription();
                case "cos":
                    currentFunction = MathFunction.COS;
                    return "Función cambiada a: " + currentFunction.getDescription();
                case "tan":
                    currentFunction = MathFunction.TAN;
                    return "Función cambiada a: " + currentFunction.getDescription();
                default:
                    return "Error: Función '" + functionName + "' no reconocida. Use: sin, cos, o tan";
            }
        } catch (Exception e) {
            return "Error al procesar comando de función: " + e.getMessage();
        }
    }
    
    /**
     * Procesa un número y aplica la función matemática actual.
     * 
     * @param input entrada del usuario
     * @return resultado del cálculo o mensaje de error
     */
    private static String processNumber(String input) {
        try {
            double number = Double.parseDouble(input.trim());
            double result;
            
            switch (currentFunction) {
                case SIN:
                    result = Math.sin(number);
                    break;
                case COS:
                    result = Math.cos(number);
                    break;
                case TAN:
                    result = Math.tan(number);
                    break;
                default:
                    return "Error: Función no definida";
            }
            
            // Formatear el resultado para que sea más legible
            if (Math.abs(result) < 1e-10) {
                result = 0.0; // Evitar números muy pequeños por precisión flotante
            }
            
            return String.valueOf(result);
            
        } catch (NumberFormatException e) {
            return "Error: '" + input + "' no es un número válido";
        } catch (Exception e) {
            return "Error al calcular: " + e.getMessage();
        }
    }
}