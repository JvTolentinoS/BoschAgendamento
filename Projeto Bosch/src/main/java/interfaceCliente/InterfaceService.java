package interfaceCliente;

import entidade.Agendamento;
import entidade.Carro;
import entidade.Cliente;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfaceService {

    private Cliente clientSession;
    private Carro carroSession;
    private Agendamento agendamentoSession;

    static ClienteServico clienteServico = new ClienteServico();

    public void inicializadorCliente(ClienteServico clienteServico) {
        clientSession = new Cliente();
        carroSession = new Carro();
        agendamentoSession = new Agendamento();
        initialInterface(clienteServico, clientSession, carroSession, agendamentoSession);
    }

    public static InterfaceService initialInterface(ClienteServico clienteServico, Cliente cliente, Carro carro, Agendamento agendamento) {
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
                        clienteServico.registrador(cliente, carro, agendamento);
                        break;
                        case 2: // Ver Agendamentos
                            System.out.println("Iniciando processo de ver agendamentos...");
                            clienteServico.registradorVerAgendas(cliente, carro, agendamento);
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
        System.out.println("1 - Iniciar um agendamento");
        System.out.println("2 - Consultar agendamentos existentes");
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
