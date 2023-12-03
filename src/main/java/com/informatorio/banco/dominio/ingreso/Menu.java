package com.informatorio.banco.dominio.ingreso;

import com.informatorio.banco.dominio.dominios.cuentaAhorro.CuentaAhorro;
import com.informatorio.banco.dominio.dominios.cliente.Cliente;
import com.informatorio.banco.dominio.dominios.banco.Banco;
import com.informatorio.banco.dominio.dominios.cuentaBancaria.CuentaBancaria;
import com.informatorio.banco.dominio.dominios.cuentaCorriente.CuentaCorriente;
import com.informatorio.banco.dominio.servicio.ClienteServicio;
import com.informatorio.banco.dominio.servicio.ClienteServicioImpl;
import com.informatorio.banco.dominio.servicio.ExportadorCSV;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private static Banco banco;
    private static ClienteServicio clienteServicio;
    private static Scanner scanner;

    public static void iniciar() {
        banco = new Banco();
        clienteServicio = new ClienteServicioImpl(banco);
        scanner = new Scanner(System.in);

        int opcion;

        do {
            mostrarMenu();
            opcion = obtenerOpcion();

            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    clienteServicio.ofrecerServicios();
                    break;
                case 3:
                    ExportadorCSV.exportarCuentasCSV();
                    break;
                case 4:
                    System.out.println("Nos vemos pronto");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 4);
    }

    private static void mostrarMenu() {
        System.out.println("*** Sistema Bancario del informatorio ***");
        System.out.println("1. Registrar Cliente");
        System.out.println("2. Ofrecer Servicios");
        System.out.println("3. Exportar Cuentas a CSV");
        System.out.println("4. Salir");
        System.out.print("Ingrese la opción: ");
    }

    private static int obtenerOpcion() {
        while (true) {
            String entrada = scanner.next().toLowerCase();

            if (entrada.equals("1") || entrada.equals("registrar") || entrada.equals("cliente")) {
                return 1;
            } else if (entrada.equals("2") || entrada.equals("ofrecer") || entrada.equals("servicios")) {
                return 2;
            } else if (entrada.equals("3") || entrada.equals("exportar") || entrada.equals("csv")) {
                return 3;
            } else if (entrada.equals("4") || entrada.equals("salir")) {
                return 4;
            } else {
                System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private static void registrarCliente() {
        System.out.print("Ingrese su nombre por favor: ");
        String nombre = scanner.nextLine();
        scanner.nextLine();
        System.out.print("Ahora su dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Ingrese su número único: ");
        String numeroUnico = scanner.nextLine();
        Cliente cliente = new Cliente(numeroUnico, nombre, direccion);
        banco.registrarCliente(cliente);

        System.out.println("\nSu información:");
        System.out.println("Saldo total inicial: " + cliente.consultarSaldoTotal());

        System.out.print("\nQué tipo de cuenta deseas abrir? Ahorro / Corriente / Ambas: ");
        String tipoCuenta = scanner.nextLine();

        System.out.print("Ingrese su monto inicial: ");
        double montoInicial = scanner.nextDouble();
        scanner.nextLine();
        cliente.abrirCuentas(montoInicial, tipoCuenta, banco);

        while (true) {
                System.out.println("\nQué quieres hacer? \n" +
                        "1. Volver\n" +
                        "2. Ver datos\n" +
                        "3. Depositar\n" +
                        "4. Retirar\n" +
                        "5. Exportar CSV\n" +
                        "6. Eliminar Cuenta\n" +
                        "7. Agregar Intereses\n" +
                        "Ingrese el número o letra correspondiente a la opción:");

                String opcion = scanner.nextLine();

                switch (opcion.toLowerCase()) {
                    case "1", "volver":
                        return;
                    case "2", "ver datos":
                        System.out.println("\nDetalles de las cuentas:");
                        System.out.println("---------------------------");

                        for (CuentaBancaria cuenta : banco.getCuentasDeCliente(cliente)) {
                            System.out.println("Número de cuenta: " + cuenta.getNumero());

                            if (cuenta instanceof CuentaAhorro) {
                                CuentaAhorro cuentaAhorro = (CuentaAhorro) cuenta;
                                System.out.println("Tipo: Cuenta de Ahorro");
                                System.out.println("Tasa de interés: " + cuentaAhorro.getTasaInteres());
                                System.out.println("Descripción de C/ de Ahorro: Las cuentas de ahorro pagan intereses a una tasa específica.");
                            } else if (cuenta instanceof CuentaCorriente) {
                                CuentaCorriente cuentaCorriente = (CuentaCorriente) cuenta;
                                System.out.println("Tipo: Cuenta Corriente");
                                System.out.println("Límite de sobregiro: " + cuentaCorriente.getLimiteSobreGiro());
                                System.out.println("Descripción de C/ Corriente: Las cuentas corrientes pueden tener un límite de sobregiro y deben manejar retiros que excedan el saldo disponible.");
                            } else {
                                System.out.println("Tipo de cuenta sin detalles.");
                            }

                            System.out.println("Saldo: " + cuenta.consultarSaldo());
                            System.out.println("---------------------------");
                        }
                        break;

                    case "3", "depositar":
                        System.out.print("Ingrese el monto para depositar: ");
                        double montoDeposito = scanner.nextDouble();
                        scanner.nextLine();

                        for (CuentaBancaria cuenta : banco.getCuentasDeCliente(cliente)) {
                            cuenta.depositar(montoDeposito);
                            System.out.println("Depositado con éxito en la cuenta " + cuenta.getNumero());
                            System.out.println("Nuevo saldo: " + cuenta.consultarSaldo());
                        }
                        break;

                    case "4", "retirar":
                        System.out.print("Ingrese el monto para retirar: ");
                        double montoRetiro = scanner.nextDouble();
                        scanner.nextLine();

                        for (CuentaBancaria cuenta : banco.getCuentasDeCliente(cliente)) {
                            boolean retiroExitoso = cuenta.retirar(montoRetiro);
                            if (retiroExitoso) {
                                System.out.println("Retirado con éxito de la cuenta " + cuenta.getNumero());
                                System.out.println("Nuevo saldo: " + cuenta.consultarSaldo());
                            } else {
                                System.out.println("Fondos insuficientes en la cuenta " + cuenta.getNumero() +
                                        " para realizar el retiro de " + montoRetiro);
                            }
                        }
                        break;

                    case "5", "exportar csv":
                        List<Cliente> listaClientes = new ArrayList<>();
                        listaClientes.add(cliente);
                        ExportadorCSV.exportarACSV(listaClientes, "datos_cliente.csv");
                        System.out.println("Datos exportados a CSV correctamente.");
                        break;

                    case "6", "eliminar cuenta":
                        System.out.print("Ingrese el número de cuenta que desea eliminar: ");
                        String numeroCuentaEliminar = scanner.nextLine();
                        cliente.eliminarCuenta(numeroCuentaEliminar, banco);
                        System.out.println("Cuenta eliminada, gracias por usar la App");
                        break;

                    case "7", "agregar intereses":
                        CuentaBancaria.agregarIntereses(cliente);
                        System.out.println("Intereses agregados con éxito.");
                        break;

                    default:
                        System.out.println("Opción no válida. Por favor, ingrese un número o letra válido.");
                        break;
                }
            }
        }
    }

