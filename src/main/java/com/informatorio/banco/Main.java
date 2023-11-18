package com.informatorio.banco;

import com.informatorio.banco.cliente.Cliente;
//import com.informatorio.banco.servicio.archivo.ArchivoServicio;
import com.informatorio.banco.cliente.cuenta;
import com.informatorio.banco.cuenta.Cuenta;
import com.informatorio.banco.cuenta.CuentaAhorro;
import com.informatorio.banco.cuenta.CuentaCorriente;

import java.util.ArrayList;
import java.util.List;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Buenas buenas");

        System.out.print("Ingrese su nombre por favor: ");
        String nombreCompleto = scanner.nextLine();

        System.out.print("Ahora su dirección: ");
        String direccion = scanner.nextLine();

        Cliente cliente = new Cliente(nombreCompleto, direccion);
        System.out.println("\nSu información inicial:");
        cliente.mostrarInformacion();
        System.out.println("Saldo total inicial: " + cliente.consultarSaldoTotal());

        System.out.print("\nQué tipo de cuenta desea abrir? Ahorro / Corriente / Ambas: ");
        String tipoCuenta = scanner.nextLine();

        if (tipoCuenta.equalsIgnoreCase("Ahorro") || tipoCuenta.equalsIgnoreCase("Corriente") || tipoCuenta.equalsIgnoreCase("Ambas")) {
            System.out.print("Ingrese su monto inicial: ");
            double montoInicial = scanner.nextDouble();
            scanner.nextLine();

            if (tipoCuenta.equalsIgnoreCase("Ahorro")) {
                cliente.abrirCuentaAhorro(montoInicial);
            } else if (tipoCuenta.equalsIgnoreCase("Corriente")) {
                cliente.abrirCuentaCorriente(montoInicial);
            } else {
                cliente.abrirCuentaAhorro(montoInicial);
                cliente.abrirCuentaCorriente(montoInicial);
            }
            System.out.println("Cuenta/s creada/s con éxito.");

           while (true) {
            System.out.print("\nQué desea hacer? Volver / Ver datos + Depositar o Retirar / Exportar CSV / Eliminar Cuenta: ");
               String opcion = scanner.nextLine();

               if (opcion.equalsIgnoreCase("Volver")) {
                break;
            } else if (opcion.equalsIgnoreCase("Ver datos")) {
                System.out.println("\nInformación actualizada del cliente:");
                cliente.mostrarInformacion();
                
            } else if (opcion.equalsIgnoreCase("Depositar") || opcion.equalsIgnoreCase("Retirar")) {
                    double montoOperacion = scanner.nextDouble();
                    scanner.nextLine();

                    if (opcion.equalsIgnoreCase("Depositar")) {
                        cliente.realizarDepositoEnCuenta(montoOperacion);
                        System.out.println("Depositado con éxito.");
                    } else {
                        cliente.realizarRetiroCuenta(montoOperacion);
                        System.out.println("Retirado con éxito.");
                        
                    }
            } else if (opcion.equalsIgnoreCase("Exportar CSV")) {
                char[] csvData = cliente.exportarCuentasCSV().toCharArray();
                System.out.println("Datos exportados a CSV:\n" + new String(csvData));

            } else if (opcion.equalsIgnoreCase("Eliminar Cuenta")) {
                System.out.print("Ingrese el número de cuenta que desea eliminar: ");
                String numeroCuentaEliminar = scanner.nextLine();
                cliente.eliminarCuenta(numeroCuentaEliminar);
                System.out.println("Cuenta eliminada, gracias por usar la App");
            }
            


    }
        }}}


//falta dar un numero de cuenta a cada cuenta creada.
// eliminar la cuenta seleccionada o ambas.
//ver logica de exportar CSV
