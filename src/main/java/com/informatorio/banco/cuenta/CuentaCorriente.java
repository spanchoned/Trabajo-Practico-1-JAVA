package com.informatorio.banco.cuenta;

import com.informatorio.banco.cliente.Cliente;

public class CuentaCorriente extends Cuenta {
    private static final double LIMITE_SOBREGIRO_PORCENTAJE = 0.90;
    private static final double INTERES_SOBREGIRO_PORCENTAJE = 0.50;
    private double limiteSobreGiro;

    public CuentaCorriente(String numero, Cliente titular, double saldoInicial, double limiteSobreGiro) {
        super(numero, titular, saldoInicial);
        this.limiteSobreGiro = limiteSobreGiro;
    }

    public double getLimiteSobreGiro() {
        return limiteSobreGiro;
    }

    @Override
    public double retirar(double monto) {
        double saldoDisponible = getSaldo() + limiteSobreGiro;

        if (monto <= saldoDisponible) {
            return super.retirar(monto);
        } else {
            System.out.println("Retiro excede el saldo disponible y límite de sobregiro.");
            return -1;
        }
    }

    public void aplicarInteresSobregiro() {
        double interes = getSaldo() * INTERES_SOBREGIRO_PORCENTAJE;
        super.retirar(interes);
        System.out.println("Se ha aplicado un interés por sobregiro del 1%.");
    }
}
