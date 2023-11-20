package com.informatorio.banco.cuenta;

import com.informatorio.banco.cliente.Cliente;

public class CuentaCorriente extends Cuenta {
    private double limiteSobreGiro;

    public CuentaCorriente(String numero, Cliente titular, double saldoInicial, double limiteSobreGiro) {
        super(numero, titular, saldoInicial);
        this.limiteSobreGiro = limiteSobreGiro;
    }

    public double getLimiteSobreGiro() {
        return limiteSobreGiro;
    }

    public void setLimiteSobreGiro(double limiteSobreGiro) {
        this.limiteSobreGiro = limiteSobreGiro;
    }

    public double gestionarSobreGiro(double monto) {
        if (monto <= limiteSobreGiro) {
            setLimiteSobreGiro(limiteSobreGiro - monto);
            return super.retirar(monto);
        } else {
            System.out.println("Sobre giro excedido.");
            return -1;
        }
    }
}
