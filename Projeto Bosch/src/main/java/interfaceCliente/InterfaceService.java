package interfaceCliente;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfaceService {

    static ClienteServico clienteServico = new ClienteServico();
    public static void main(String[] args) {
        InterfaceService logo = new InterfaceService();
        logo.initialInterface(clienteServico);
    }

    public InterfaceService initialInterface(ClienteServico clienteServico) {
        logoText();
        introMessage();
        int i = -1;
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                initialMenu();
                i = scanner.nextInt();
                switch (i) {
                    case 1: // Iniciar um Agendamento
                        System.out.println("Iniciando processo de criar agendamento...");
                        clienteServico.clientInitializer();
                        break;
                        case 2: // Ver Agendamentos
                            System.out.println("Iniciando processo de ver agendamentos...");
                            break;
                            case 3: // Ver Carros
                                System.out.println("Iniciando processo de ver carros...");
                            break;
                            case 0:
                                System.out.println("Terminando aplicação...");
                                System.exit(0);
                            default:
                                System.out.println("Opção inválida! Reiniciando a seleção...");
                    }
            } catch (InputMismatchException e) {
                System.out.println("Resposta Inválida!");
                scanner.nextLine();
            }
        } while (true);
    }

    private static void initialMenu() {
        System.out.println("\n========================== MENU INICIAL ===========================");
        System.out.println("1 - Iniciar um Agendamento");
        System.out.println("0 - Sair");
        System.out.print("Resposta: ");
    }

    private static void introMessage() {
            System.out.println("\n========================== FAZEMOS TUDO POR SEU CARRO  ===========================");
            System.out.println("Bem-Vindo(a) ao Bosch Car Service!");
            System.out.println("Este é o canal oficial da Bosch Car Service. Para mais informações, siga nossas redes sociais!");
            System.out.println("Selecione entre as opções abaixo:");
    }

    private static void logoText() {
        System.out.println("    " +
                 "\n" +"██████╗  ██████╗ ███████╗ ██████╗██╗  ██╗" +
                 "\n" +"██╔══██╗██╔═══██╗██╔════╝██╔════╝██║  ██║" +
                 "\n" +"██████╔╝██║   ██║███████╗██║     ███████║" +
                 "\n" +"██╔══██╗██║   ██║╚════██║██║     ██╔══██║" +
                 "\n" +"██████╔╝╚██████╔╝███████║╚██████╗██║  ██║" +
                 "\n" +"╚═════╝  ╚═════╝ ╚══════╝ ╚═════╝╚═╝  ╚═╝ " +
                 "\n" + "" +
                 "\n" +" ██████╗ █████╗ ██████╗   " +
                 "\n" +"██╔════╝██╔══██╗██╔══██╗ " +
                 "\n" +"██║     ███████║██████╔╝" +
                 "\n" +"██║     ██╔══██║██╔══██╗ " +
                 "\n" +"╚██████╗██║  ██║██║  ██║" +
                 "\n" +" ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝ " +
                 "\n" +"" +
                 "\n" +"███████╗███████╗██████╗ ██╗   ██╗██╗ ██████╗███████╗" +
                 "\n" +"██╔════╝██╔════╝██╔══██╗██║   ██║██║██╔════╝██╔════╝" +
                 "\n" +"███████╗█████╗  ██████╔╝██║   ██║██║██║     █████╗  " +
                 "\n" +"╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██║     ██╔══╝  " +
                 "\n" +"███████║███████╗██║  ██║ ╚████╔╝ ██║╚██████╗███████╗" +
                 "\n" +"╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝ ╚═════╝╚══════╝" +
                 "\n");
    }
}