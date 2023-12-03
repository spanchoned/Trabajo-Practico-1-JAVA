package com.informatorio.banco.dominio.dominios.cuentaCorriente;
import com.informatorio.banco.dominio.dominios.cliente.Cliente;
import com.informatorio.banco.dominio.dominios.cuentaBancaria.CuentaBancaria;

import java.util.List;
import java.util.Scanner;

public class CuentaCorriente extends CuentaBancaria {
    private double limiteSobregiro;


    public CuentaCorriente(Cliente titular, double saldo, double limiteSobregiro) {
        super(titular, saldo);
        this.limiteSobregiro = limiteSobregiro;
    }

    public double getLimiteSobreGiro() {
        return limiteSobregiro;
    }
    public CuentaCorriente abrirCuentaCorriente(Cliente cliente) {
        CuentaCorriente cuentaCorriente = new CuentaCorriente(cliente, 1000.0, 500.0);
        cliente.agregarCuenta(cuentaCorriente);
        return cuentaCorriente;
    }

    @Override
    public void depositar(double monto) {
        if (monto > 0) {
            setSaldo(getSaldo() + monto);
        }
    }

    @Override
    public boolean retirar(double monto) {
        double saldoDisponible = getSaldo() + limiteSobregiro;
        if (monto <= saldoDisponible) {
            setSaldo(getSaldo() - monto);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void eliminarCuenta(List<CuentaBancaria> cuentas, String numeroCuenta) {
        cuentas.removeIf(cuenta -> cuenta.getNumero().equals(numeroCuenta));
    }

    @Override
    public void calcularInteres() {

    }

    public double obtenerMontoLimiteSobregiro() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el límite de sobregiro para la cuenta corriente: ");
        return scanner.nextDouble();
    }

    public void agregarCuenta(CuentaCorriente cuentaCorriente, Cliente cliente) {
        cliente.agregarCuenta(cuentaCorriente);
    }
}

