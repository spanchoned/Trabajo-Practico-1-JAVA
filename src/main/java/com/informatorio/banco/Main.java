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

        System.out.println("\nSu información inicial:");
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
                System.out.print("\nQué queres hacer? Volver / Ver datos + Depositar o Retirar / Exportar CSV / Eliminar Cuenta / Agregar Intereses: ");
                String opcion = scanner.nextLine();

                if (opcion.equalsIgnoreCase("Volver")) {
                    break;
                } else if (opcion.equalsIgnoreCase("Ver datos")) {
                    System.out.println("\nInformación actualizada del cliente:");
                    cliente.mostrarInformacion();

                    System.out.println("\nDetalles de las cuentas:");

                    for (Cuenta cuenta : banco.getCuentasDeCliente(cliente)) {
                        System.out.println("Número de cuenta: " + cuenta.getNumero());
                        System.out.println("Tipo de cuenta: " + cuenta.getClass().getSimpleName());

                        if (cuenta instanceof CuentaAhorro) {
                            CuentaAhorro cuentaAhorro = (CuentaAhorro) cuenta;
                            System.out.println("Tasa de interés: " + cuentaAhorro.getTasaInteres());
                            System.out.println("Descripción de Cuenta de Ahorro: Las cuentas de ahorro pagan intereses a una tasa específica.");
                        } else if (cuenta instanceof CuentaCorriente) {
                            CuentaCorriente cuentaCorriente = (CuentaCorriente) cuenta;
                            System.out.println("Límite de sobregiro: " + cuentaCorriente.getLimiteSobreGiro());
                            System.out.println("Descripción de Cuenta Corriente: Las cuentas corrientes pueden tener un límite de sobregiro y deben manejar retiros que excedan el saldo disponible.");
                        }

                        System.out.println("Saldo: " + cuenta.getSaldo());
                        System.out.println("-----");
                    }

                } else if (opcion.equalsIgnoreCase("Depositar") || opcion.equalsIgnoreCase("Retirar")) {
                    System.out.print("Ingrese el monto a " + (opcion.equalsIgnoreCase("Depositar") ? "depositar" : "retirar") + ": ");
                    double montoOperacion = scanner.nextDouble();
                    scanner.nextLine();  // Consumir la nueva línea en el búfer del Scanner

                    if (opcion.equalsIgnoreCase("Depositar")) {
                        banco.realizarDeposito(cliente, montoOperacion);
                        System.out.println("Depositado con éxito.");
                    } else {
                        banco.realizarRetiro(cliente, montoOperacion);
                        System.out.println("Retirado con éxito.");
                    }
                } else if (opcion.equalsIgnoreCase("Exportar CSV")) {
                    Map<String, String> datosCliente = cliente.obtenerDatosComoMap();

                    ExportadorCSV.exportarACSV(datosCliente, "datos_cliente.csv");
                    System.out.println("Datos exportados a CSV correctamente.");

                } else if (opcion.equalsIgnoreCase("Eliminar Cuenta")) {
                    System.out.print("Ingrese el número de cuenta que desea eliminar: ");
                    String numeroCuentaEliminar = scanner.nextLine();

                    banco.eliminarCuenta(cliente, numeroCuentaEliminar);
                    System.out.println("Cuenta eliminada, gracias por usar la App");
                } else if (opcion.equalsIgnoreCase("Agregar Intereses")) {
                    banco.agregarIntereses(cliente);
                    System.out.println("Intereses agregados con éxito.");
                }
            }
        }
    }
}

