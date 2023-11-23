package com.informatorio.banco.cuenta;

import com.informatorio.banco.cliente.Cliente;

public class Cuenta implements OperacionesCuenta {
    private String numero;
    private Cliente titular;
    private double saldo;

    public Cuenta(String numero, Cliente titular, double saldoInicial) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    @Override
    public double depositar(double monto) {
        saldo += monto;
        return saldo;
    }

    @Override
    public double retirar(double monto) {
        if (monto <= saldo) {
            saldo -= monto;
        } else {
            System.out.println("Saldo insuficiente.");
        }
        return saldo;
    }

    @Override
    public double consultarSaldo() {
        return saldo;
    }

    public void mostrarInformacion() {
        System.out.println("Cuenta Número: " + numero);
        System.out.println("Titular: " + titular.getNombre());
        System.out.println("Saldo: " + saldo);
    }

    public String getNumero() {
        return numero;
    }

    public Cliente getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public String exportarCSV() {
        return titular.getNumeroUnico() + "," + titular.getNombre() + "," + saldo + "," + this.getClass().getSimpleName();
    }
}
