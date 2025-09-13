# Webserver Operation

Proyecto Maven que implementa servidores de red para operaciones de eco y cálculos matemáticos.

## Descripción

Este proyecto contiene dos tipos de servidores:

1. **EchoServer**: Servidor simple que devuelve el mismo mensaje que recibe, prefijado con "Eco:"
2. **MathServer**: Servidor que realiza operaciones matemáticas (seno, coseno, tangente) sobre números recibidos

## Estructura del Proyecto

```
src/
├── main/java/org/arsw/
│   ├── MathServer.java      # Servidor de operaciones matemáticas
│   └── MathClient.java      # Cliente para el servidor matemático
└── test/java/               # Directorio para pruebas
```

## Compilación

```bash
mvn clean compile
```

## Ejecución

### Servidor de Matemáticas

1. **Iniciar el servidor:**
```bash
mvn exec:java -Dexec.mainClass="org.arsw.MathServer"
```

2. **Conectar con el cliente:**
```bash
mvn exec:java -Dexec.mainClass="org.arsw.MathClient"
```

## Uso del Servidor de Matemáticas

El servidor de matemáticas funciona con los siguientes comandos:

- **Cambiar función:**
  - `fun:sin` - cambia a función seno
  - `fun:cos` - cambia a función coseno  
  - `fun:tan` - cambia a función tangente

- **Calcular:** 
  - Enviar cualquier número para aplicar la función actual

### Ejemplo de Uso

```
Servidor iniciado con función: coseno
Entrada: 0        → Respuesta: 1.0
Entrada: 2        → Respuesta: -0.4161468365471424
Entrada: fun:sin  → Respuesta: Función cambiada a: seno
Entrada: 0        → Respuesta: 0.0
Entrada: 1.5708   → Respuesta: 1.0 (π/2)
```

![Ejemplo de uso del MathServer](/img/mathserver_example.png)

## Características

- **Puerto:** Ambos servidores usan el puerto 35000 por defecto
- **Protocolo:** TCP
- **Conexiones:** Un cliente a la vez (servidor bloqueante)
- **Función por defecto:** Coseno para el MathServer
- **Salida:** Ambos servidores se cierran cuando reciben "Adiós." o "exit"

## Requisitos

- Java 11 o superior
- Maven 3.6 o superior

# Realizado por 

Diego Cardenas