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

    public boolean realizarOperacionEnCuenta(Cuenta cuenta, double monto) {
        if (monto >= 0) {
            cuenta.depositar(monto);
            System.out.println("Operación realizada con éxito.");
            return true;
        } else {
            double saldoDisponible = cuenta.consultarSaldo();
            if (saldoDisponible >= Math.abs(monto)) {
                cuenta.retirar(Math.abs(monto));
                System.out.println("Operación realizada con éxito.");
                return true;
            } else {
                System.out.println("Saldo insuficiente para realizar la operación.");
                return false;
            }
        }
    }}