package com.informatorio.banco.banco;

import com.informatorio.banco.cliente.Cliente;
import com.informatorio.banco.cuenta.Cuenta;
import com.informatorio.banco.cuenta.CuentaAhorro;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Banco {
    private List<Cliente> clientes;

    public Banco() {
        this.clientes = new ArrayList<>();
    }

    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public List<Cuenta> getCuentasDeCliente(Cliente cliente) {
        Set<Cuenta> cuentasUnicas = new HashSet<>(cliente.getCuentas());
        return new ArrayList<>(cuentasUnicas);
    }

    public void registrarCuenta(Cliente cliente, Cuenta cuenta) {
        cliente.agregarCuenta(cuenta);
    }

    public void realizarDeposito(Cliente cliente, double monto) {
        List<Cuenta> cuentasActualizadas = new ArrayList<>();

        for (Cuenta cuenta : cliente.getCuentas()) {
            Cuenta cuentaActualizada = new Cuenta(cuenta.getNumero(), cliente, cuenta.consultarSaldo()); // Ajusta el constructor según tus necesidades
            cuentaActualizada.depositar(monto);
            System.out.println("Depósito de " + monto + " realizado en la cuenta " + cuentaActualizada.getNumero());
            System.out.println("Nuevo saldo: " + cuentaActualizada.consultarSaldo());
            cuentasActualizadas.add(cuentaActualizada);
        }

        cliente.setCuentas(cuentasActualizadas);
    }

    public void realizarRetiro(Cliente cliente, double monto) {
        Set<Cuenta> cuentasActualizadas = new HashSet<>();

        for (Cuenta cuenta : cliente.getCuentas()) {
            boolean retiroExitoso = cuenta.retirar(monto);
            if (retiroExitoso) {
                System.out.println("Retiro de " + monto + " realizado en la cuenta " + cuenta.getNumero());
                System.out.println("Nuevo saldo: " + cuenta.consultarSaldo());
                cuentasActualizadas.add(cuenta);
            } else {
                System.out.println("Fondos insuficientes en la cuenta " + cuenta.getNumero() + " para realizar el retiro de " + monto);
            }
        }

        cliente.setCuentas(new ArrayList<>(cuentasActualizadas));
    }

    public void eliminarCuenta(Cliente cliente, String numeroCuenta) {
        for (Cuenta cuenta : cliente.getCuentas()) {
            if (cuenta.getNumero().equals(numeroCuenta)) {
                cliente.eliminarCuenta(cuenta.getNumero());
                System.out.println("Cuenta eliminada con éxito.");
                return;
            }
        }

        System.out.println("La cuenta con el número " + numeroCuenta + " no existe para este cliente.");
    }

    public void agregarIntereses(Cliente cliente) {
        for (Cuenta cuenta : cliente.getCuentas()) {
            if (cuenta instanceof CuentaAhorro) {
                CuentaAhorro cuentaAhorro = (CuentaAhorro) cuenta;
                cuentaAhorro.agregarIntereses();
                System.out.println("Intereses agregados a la cuenta de ahorro. Nuevo saldo: " + cuentaAhorro.getSaldo());
            }
        }
    }

}


