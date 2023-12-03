package com.informatorio.banco.dominio.servicio;

import com.informatorio.banco.dominio.dominios.cliente.Cliente;
import com.informatorio.banco.dominio.dominios.cuentaBancaria.CuentaBancaria;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExportadorCSV {
    public static void exportarACSV(List<Cliente> clientes, String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            Set<CuentaBancaria> cuentasUnicas = new HashSet<>();

            for (Cliente cliente : clientes) {
                for (CuentaBancaria cuenta : cliente.getCuentas()) {
                    cuentasUnicas.add(cuenta);
                }
            }

            for (CuentaBancaria cuenta : cuentasUnicas) {
                Cliente cliente = cuenta.getTitular();
                writer.append(cliente.getNumeroUnico())
                        .append(',')
                        .append(cliente.getNombre())
                        .append(',')
                        .append(cliente.getDireccion())
                        .append(',')
                        .append(cuenta.getNumero())
                        .append(',')
                        .append(Double.toString(cuenta.consultarSaldo()))
                        .append(',')
                        .append(cuenta.getClass().getSimpleName())
                        .append('\n');
            }

            System.out.println("Exportación a CSV exitosa.");
        } catch (IOException e) {
            System.out.println("Error al exportar a CSV: " + e.getMessage());
        }
    }
    public static void exportarCuentasCSV() {
    }
}