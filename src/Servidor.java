import java.io.*;
import java.net.*;

public class Servidor {
  static PrintWriter output = null;
  static BufferedReader input = null;
  private ServerSocket serverSocket = null;
  Socket socket = null;
  static int PORTA = 5555;
  String messageRecebida = "";
  static String checksum;

  static String enderecoOrigem;
  static String comando;
  static String mensagem;
  static String numAgencia = null;
  static String numConta = null;
  static String numAgenciaDestino = null;
  static String numContaDestino = null;
  static String valor = null;

  private void criarServerSocket(int PORTA) throws IOException {
	System.out.println("\tSERVIDOR INICIALIZADO!\n\n\tPorta: " + PORTA + "\n\n\t[Aguardando conexão]");
    serverSocket = new ServerSocket(PORTA);
  }

  private Socket esperarConexao() throws IOException {
    Socket socket = serverSocket.accept();
    return socket;
  }

  private void fechaSocket(Socket s) throws IOException {
	System.out.println("\n\t [Cliente terminou]\n\t[Aguardando conexão]");
    s.close();
  }

  public static void separarMensagem(String mensagemCompleta) {
    enderecoOrigem = mensagemCompleta.substring(0, 20);
    comando = mensagemCompleta.substring(20, 35);
    mensagem = mensagemCompleta.substring(35, 85);
    checksum = mensagemCompleta.substring(85, 87);
  }

  public static boolean conferirChecksumXor(String mensagemCompleta) {
    String mensagem = mensagemCompleta.substring(0, 85);
    String checkOrigem = mensagemCompleta.substring(85, 87);
    int calculo = 0;
    String check;
    byte[] bytes = mensagem.getBytes();

    for (int i = 0; i < bytes.length; i++) {
      calculo ^= bytes[i];
    }
    check = calculo + "";

    if (checkOrigem.equals(check)) {
      System.out.println("Checksum válido!");
      return true;
    }
    System.out.println("Checksum invalido:" + check + "!=" + checkOrigem);
    return false;
  }

  public static boolean verificarTamanhoTexto(String texto, int tamanhoVerificacao) {
    if (texto.length() == tamanhoVerificacao) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean verificarComando(String comando) {
    String resultado = comando.replaceAll("\s+", "");
    
    if (resultado.equalsIgnoreCase("TRANSFERIR")) {
      return true;
    } else {
      return false;
    }
  }

  private void messageLoop(Socket socket) throws IOException, ClassNotFoundException {
    try {
      System.out.println("\n\t[Conexão Realizada]\n");

      output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"), true);
      input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      char[] charArray = new char[87];
      input.read(charArray);
      messageRecebida = new String(charArray);

      System.out.println("Mensagem Cliente: " + messageRecebida);
      separarMensagem(messageRecebida);

      if (conferirChecksumXor(messageRecebida)) { 
        if (verificarTamanhoTexto(messageRecebida, 87)) { 
          separarMensagem(messageRecebida);
          if (verificarTamanhoTexto(enderecoOrigem, 20)) { 
            if (verificarTamanhoTexto(comando, 15)) {
              if (verificarComando(comando)) {
                if (verificarTamanhoTexto(mensagem, 50)) {
                  output.print("RESP200");
                  output.flush();
                }
              } else {
                output.print("ERRO400"); 
                output.flush();
              }
            } else {
              output.print("ERRO411"); 
              output.flush();
            }
          } else {
            output.print("ERRO411"); 
            output.flush();
          }
        } else {
          output.print("ERRO411");
          output.flush();
        }
      } else {
        output.print("ERRO411"); 
        output.flush();
      }

    } catch (IOException ex) {
      System.out.println("Ocorreu um problema ao se conectar com o Cliente " + socket.getInetAddress());
      System.out.println("Mensagem de erro: " + ex.getMessage());
    } finally {
      fechaSocket(socket);
    }

  }

  public static void main(String[] args) {
    try {
      Servidor server = new Servidor();
      server.criarServerSocket(PORTA);
      
      while (true) {
        Socket socket = server.esperarConexao();
        server.messageLoop(socket);
      }

    } catch (IOException ex) {
      System.out.println("Erro no servidor: " + ex.getMessage());
    } catch (ClassNotFoundException ex) {
      System.out.println("Erro no cast: " + ex.getMessage());
    }
  }
}