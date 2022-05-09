import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
  public static Scanner scan = new Scanner(System.in);
  
  static PrintWriter output = null;
  static BufferedReader input = null;
  static String IP = "127.0.0.1";
  static String host = null;
  static int PORTA = 5555;
  static String mensagemRecebida = "";
  static String mensagemEnviada = "";
  public static String checksum;

  public static String contaDestino = null;
  public static String agenciaDestino = null;
  public static String comando = null;
  public static String mensagem = null;
  public static String agenciaOrigem = null;
  public static String contaOrigem = null;
  public static String valor = null;
  
  public static int opcao = 0;
  public static int sair = 1;
  public static int menuOpcao;

  public static void imprimirMenu() {
    System.out.println("\n\tBanco X\n");
    System.out.println("Menu: ");
    System.out.println("1 - Cadastrar Conta");
    System.out.println("2 - Sacar");
    System.out.println("3 - Depositar");
    System.out.println("4 - Transferir");
    System.out.println("5 - Empréstimo");
    System.out.print("\nSelecione uma opção: ");
  }

  public static int criarChecksumXor(String mensagemEnviada) {
    byte[] bytes = mensagemEnviada.getBytes();
    int check_sum = 0;
    for (int i = 0; i < bytes.length; i++) {
      check_sum ^= bytes[i];
    }
    return check_sum;
  }

  public static String padLeftZeros(String inputString, char pad, int length) {
    if (inputString.length() >= length) {
      return inputString;
    }
    StringBuilder sb = new StringBuilder();
    while (sb.length() < length - inputString.length()) {
      sb.append(pad);
    }
    sb.append(inputString);

    return sb.toString();
  }

  public static String criarString(String endereco, String comando, String mensagem) {
    String mensagemFinal = null;
    mensagemFinal = padLeftZeros(endereco, ' ', 20);
    mensagemFinal += padLeftZeros(comando, ' ', 15);
    mensagemFinal += padLeftZeros(mensagem, ' ', 50);
    mensagemFinal += padLeftZeros(criarChecksumXor(mensagemFinal) + "", ' ', 2);
    return mensagemFinal;
  }

  public static void cadastrarConta() {
    String cpf;
    
    comando = "CADASTRAR CONTA";
    System.out.println("\n\tCadastrar Conta");
    
    do {
      System.out.print("Digite o número da agencia: ");
      agenciaOrigem = scan.next();
       
      if (agenciaOrigem.length() != 4) {
        System.out.println("O número da agencia precisa conter 4 digitos!\n");
      }
    } while (agenciaOrigem.length() != 4);

    do {
      System.out.print("Digite o número da conta: ");
      contaOrigem = scan.next();
      
      if (contaOrigem.length() != 6) {
        System.out.println("O número da conta precisa conter 6 digitos!\n");
      }
    } while (contaOrigem.length() != 6);

    System.out.print("Digite o seu CPF: ");
    cpf = scan.next();

    mensagem = agenciaOrigem;
    mensagem = mensagem.concat(contaOrigem);
    mensagem = mensagem.concat(cpf);
  }

  public static void sacar() {
    comando = "SACAR";
    System.out.println("\n\tSacar");

    do {
      System.out.print("Digite o número da agencia: ");
      agenciaOrigem = scan.next();
      
      if (agenciaOrigem.length() != 4) {
    	  System.out.println("O número da agencia precisa conter 4 digitos!\n");
      }
    } while (agenciaOrigem.length() != 4);

    do {
      System.out.print("Digite o número da conta: ");
      contaOrigem = scan.next();
      
      if (contaOrigem.length() != 6) {
    	  System.out.println("O número da conta precisa conter 6 digitos!\n");
      }
    } while (contaOrigem.length() != 6);

    System.out.print("Digite o valor: ");
    valor = scan.next();
    
    mensagem = agenciaOrigem;
    mensagem = mensagem.concat(contaOrigem);
    mensagem = mensagem.concat(valor);
  }

  public static void depositar() {
    comando = "DEPOSITAR";
    System.out.println("\n\tDepositar");
    
    do {
      System.out.print("Digite o número da agencia: ");
      agenciaOrigem = scan.next();
      
      if (agenciaOrigem.length() != 4) {
    	  System.out.println("O número da agencia precisa conter 4 digitos!\n");
      }
    } while (agenciaOrigem.length() != 4);

    do {
      System.out.print("Digite o número da conta:");
      contaOrigem = scan.next();
      if (contaOrigem.length() != 6) {
    	  System.out.println("O número da conta precisa conter 6 digitos!\n");
      }
    } while (contaOrigem.length() != 6);

    System.out.print("Digite o valor: ");
    valor = scan.next();
    
    mensagem = agenciaOrigem;
    mensagem = mensagem.concat(contaOrigem);
    mensagem = mensagem.concat(valor);
  }

  public static void transferir() {
    comando = "TRANSFERIR";
    System.out.println("\n\tTransferência");

    do {
      System.out.print("Digite o número da agencia de origem: ");
      agenciaOrigem = scan.next();
      
      if (agenciaOrigem.length() != 4) {
    	  System.out.println("O número da agencia precisa conter 4 digitos!\n");
      }
    } while (agenciaOrigem.length() != 4);

    do {
      System.out.print("Digite o número da conta de origem: ");
      contaOrigem = scan.next();
      
      if (contaOrigem.length() != 6) {
    	  System.out.println("O número da conta precisa conter 6 digitos!\n");
      }
    } while (contaOrigem.length() != 6);

    do {
      System.out.print("Digite o número da agencia de destino: ");
      agenciaDestino = scan.next();
      
      if (agenciaDestino.length() != 4) {
    	  System.out.println("O número da agencia precisa conter 4 digitos!\n");
      }
    } while (agenciaDestino.length() != 4);

    do {
      System.out.print("Digite o número da conta de destino: ");
      contaDestino = scan.next();
      
      if (contaDestino.length() != 6) {
    	  System.out.println("O número da conta precisa conter 6 digitos!\n");
      }
    } while (contaDestino.length() != 6);

    System.out.print("Digite o valor: ");
    valor = scan.next();
    
    mensagem = agenciaOrigem;
    mensagem = mensagem.concat(contaOrigem);
    mensagem = mensagem.concat(agenciaDestino);
    mensagem = mensagem.concat(contaDestino);
    mensagem = mensagem.concat(valor);
  }

  public static void emprestimo() {
    comando = "EMPRESTIMO";
    System.out.println("\n\tEmpréstimo");
    
    do {
      System.out.print("Digite o número da agencia de destino:");
      agenciaDestino = scan.next();
      
      if (agenciaDestino.length() != 4) {
    	  System.out.println("O número da agencia precisa conter 4 digitos!\n");
      }
    } while (agenciaDestino.length() != 4);

    do {
      System.out.println("Digite o número da conta de destino:");
      contaDestino = scan.next();
      
      if (contaDestino.length() != 6) {
    	  System.out.println("O número da conta precisa conter 6 digitos!\n");
      }
    } while (contaDestino.length() != 6);

    System.out.print("Digite o valor: ");
    valor = scan.next();
    
    mensagem = "0001";
    mensagem = mensagem.concat("989898");
    mensagem = mensagem.concat(agenciaDestino);
    mensagem = mensagem.concat(contaDestino);
    mensagem = mensagem.concat(valor);
  }

  public static void main(String[] args) {
    do {
      imprimirMenu();
      menuOpcao = scan.nextInt();
      
      switch (menuOpcao) {
      case 1:
        cadastrarConta();
        break;
      case 2:
        sacar();
        break;
      case 3:
        depositar();
        break;
      case 4:
        transferir();
        break;
      case 5:
        emprestimo();
        break;
      default:
        System.out.println("\n\t\tCódigo Incorreto!\n\tFavor digitar um código válido!");
        break;
      }

      try {
    	host = IP;
        Socket socket = new Socket(host, PORTA);
        
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        System.out.println("\n[Conectado]\n");
        
        host = host.concat(":" + PORTA);

        System.out.println("[Mensagem]:" + criarString(host, comando, mensagem));
        output.print(criarString(host, comando, mensagem));
        output.flush();

        char[] charArray = new char[30];
        input.read(charArray);
        mensagemRecebida = new String(charArray);
        System.out.println("[Mensagem Servidor]: " + mensagemRecebida);
        
        do {
        	System.out.println("\n1 - Continuar\n2 - Sair");
        	sair = scan.nextInt();
        
        	if (sair != 1 && sair != 2) {
        		System.out.println("\nOpção inválida!");
        	} else if (sair == 2) {
                System.out.println("\nConexão finalizada!");
            }
        }while(sair != 1 && sair != 2);

      } catch (Exception e) {
        System.out.println(e);
      }
    } while (sair == 1);
  }
}