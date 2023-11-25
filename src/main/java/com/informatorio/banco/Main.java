package com.informatorio.banco;

import com.informatorio.banco.cliente.Cliente;
import com.informatorio.banco.servicio.archivo.ExportadorCSV;
import com.informatorio.banco.cuenta.CuentaAhorro;
import com.informatorio.banco.cuenta.CuentaCorriente;
import com.informatorio.banco.cuenta.Cuenta;
import com.informatorio.banco.banco.Banco;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Banco banco = new Banco();

        System.out.println("Buenas buenas");

        System.out.print("Ingrese su nombre por favor: ");
        String nombreCompleto = scanner.nextLine();

        System.out.print("Ahora su dirección: ");
        String direccion = scanner.nextLine();

        Cliente cliente = new Cliente(nombreCompleto, direccion);
        banco.registrarCliente(cliente);

        System.out.println("\nSu información:");
        cliente.mostrarInformacion();
        System.out.println("Saldo total inicial: " + cliente.consultarSaldoTotal());

        System.out.print("\nQué tipo de cuenta deseas abrir? Ahorro / Corriente / Ambas: ");
        String tipoCuenta = scanner.nextLine();

        if (tipoCuenta.equalsIgnoreCase("Ahorro") || tipoCuenta.equalsIgnoreCase("Corriente") || tipoCuenta.equalsIgnoreCase("Ambas")) {
            System.out.print("Ingrese su monto inicial: ");
            double montoInicial = scanner.nextDouble();
            scanner.nextLine();

            if (tipoCuenta.equalsIgnoreCase("Ahorro")) {
                CuentaAhorro cuentaAhorro = cliente.abrirCuentaAhorro(montoInicial);
                banco.registrarCuenta(cliente, cuentaAhorro);
            } else if (tipoCuenta.equalsIgnoreCase("Corriente")) {
                double montoLimiteSobregiro = cliente.obtenerMontoLimiteSobregiro();
                CuentaCorriente cuentaCorriente = cliente.abrirCuentaCorriente(montoInicial, montoLimiteSobregiro);
                banco.registrarCuenta(cliente, cuentaCorriente);
            } else {
                CuentaAhorro cuentaAhorro = cliente.abrirCuentaAhorro(montoInicial);
                CuentaCorriente cuentaCorriente = cliente.abrirCuentaCorriente(montoInicial, cliente.obtenerMontoLimiteSobregiro());
                banco.registrarCuenta(cliente, cuentaAhorro);
                banco.registrarCuenta(cliente, cuentaCorriente);
            }
            System.out.println("Cuenta/s creada/s con éxito.");

            while (true) {
                System.out.println("\nQué queres hacer? \n" +
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

                        for (Cuenta cuenta : banco.getCuentasDeCliente(cliente)) {
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
                        banco.realizarDeposito(cliente, montoDeposito);
                        System.out.println("Depositado con éxito.");
                        break;
                    case "4", "retirar":
                        System.out.print("Ingrese el monto para retirar: ");
                        double montoRetiro = scanner.nextDouble();
                        scanner.nextLine();
                        banco.realizarRetiro(cliente, montoRetiro);
                        System.out.println("Retirado con éxito.");
                        break;

                    case "5", "exportar csv":
                        Map<String, String> datosCliente = cliente.obtenerDatosComoMap();
                        ExportadorCSV.exportarACSV(datosCliente, "datos_cliente.csv");
                        System.out.println("Datos exportados a CSV correctamente.");
                        break;

                    case "6", "eliminar cuenta":
                        System.out.print("Ingrese el número de cuenta que desea eliminar: ");
                        String numeroCuentaEliminar = scanner.nextLine();
                        banco.eliminarCuenta(cliente, numeroCuentaEliminar);
                        System.out.println("Cuenta eliminada, gracias por usar la App");
                        break;

                    case "7", "agregar intereses":
                        banco.agregarIntereses(cliente);
                        System.out.println("Intereses agregados con éxito.");
                        break;

                    default:
                        System.out.println("Opción no válida. Por favor, ingrese un número o letra válido.");
                        break;
                }
            }

        }
    }
}
