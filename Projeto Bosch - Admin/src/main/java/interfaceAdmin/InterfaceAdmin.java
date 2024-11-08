package interfaceAdmin;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfaceAdmin {
    static AdminService adminService = new AdminService();

    public static void initialInterface(AdminService adminService) {
        int i;
        Scanner scanner = new Scanner(System.in);
        logo();
        do {
            try {
                menu();
                i = scanner.nextInt();
                    switch (i) {
                        case 0: System.exit(0);
                        case 1: adminService.AdminServiceInitialize();
                            break;
                        default:
                            UtilCollection.defaultError(scanner);
                            break;
                    }
            } catch (InputMismatchException e) {
               UtilCollection.defaultError(scanner);
            }
        } while (true);
    }

    private static void logo() {
        System.out.println
                ("\n\n\n██████╗  ██████╗ ███████╗ ██████╗██╗  ██╗                                       \n" +
                 "██╔══██╗██╔═══██╗██╔════╝██╔════╝██║  ██║                                       \n" +
                 "██████╔╝██║   ██║███████╗██║     ███████║                                       \n" +
                 "██╔══██╗██║   ██║╚════██║██║     ██╔══██║                                       \n" +
                 "██████╔╝╚██████╔╝███████║╚██████╗██║  ██║                                       \n" +
                 "╚═════╝  ╚═════╝ ╚══════╝ ╚═════╝╚═╝  ╚═╝                                       \n" +
                 "                                                                                \n" +
                 " ██████╗ █████╗ ██████╗     ███████╗███████╗██████╗ ██╗   ██╗██╗ ██████╗███████╗\n" +
                 "██╔════╝██╔══██╗██╔══██╗    ██╔════╝██╔════╝██╔══██╗██║   ██║██║██╔════╝██╔════╝\n" +
                 "██║     ███████║██████╔╝    ███████╗█████╗  ██████╔╝██║   ██║██║██║     █████╗  \n" +
                 "██║     ██╔══██║██╔══██╗    ╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██║     ██╔══╝  \n" +
                 "╚██████╗██║  ██║██║  ██║    ███████║███████╗██║  ██║ ╚████╔╝ ██║╚██████╗███████╗\n" +
                 " ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝    ╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝ ╚═════╝╚══════╝\n" +
                 "                                                                                \n" +
                 " █████╗ ██████╗ ███╗   ███╗██╗███╗   ██╗                                        \n" +
                 "██╔══██╗██╔══██╗████╗ ████║██║████╗  ██║                                        \n" +
                 "███████║██║  ██║██╔████╔██║██║██╔██╗ ██║                                        \n" +
                 "██╔══██║██║  ██║██║╚██╔╝██║██║██║╚██╗██║                                        \n" +
                 "██║  ██║██████╔╝██║ ╚═╝ ██║██║██║ ╚████║                                        \n" +
                 "╚═╝  ╚═╝╚═════╝ ╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝                                        \n" +
                 "                                                                          ");
    }

    private static void menu() {
        System.out.println("\n============================================");
        System.out.println("1 - Consultar agenda");
        System.out.println("0 - Sair");
        System.out.print("Resposta: ");
    }

}
