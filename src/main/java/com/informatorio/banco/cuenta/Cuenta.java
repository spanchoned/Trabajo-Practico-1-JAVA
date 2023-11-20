package com.informatorio.banco.cuenta;

import java.util.function.ToDoubleFunction;

import com.informatorio.banco.cliente.Cliente;
import com.informatorio.banco.cliente.cuenta;

public class Cuenta implements cuenta {
    public static ToDoubleFunction<? super Cuenta> consultarSaldo;
    private String numero;
    private Cliente titular;
    private double saldo;

    public Cuenta(String numero, Cliente titular, double saldoInicial) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    public double depositar(double monto) {
        saldo += monto;
        return saldo;
    }

    public double retirar(double monto) {
        if (monto <= saldo) {
            saldo -= monto;
        } else {
            System.out.println("Saldo insuficiente.");
        }
        return saldo;
    }

    public double consultarSaldo() {
        return saldo;
    }

    public void getNumeroCuenta(){
        
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

// falta algunas operaciones que operaciones en construcción :S