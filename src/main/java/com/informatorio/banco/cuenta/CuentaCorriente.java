package com.informatorio.banco.cuenta;

import com.informatorio.banco.cliente.Cliente;


public class CuentaCorriente extends Cuenta implements OperacionesCuenta {
    private static final double LIMITE_SOBREGIRO_PORCENTAJE = 0.90;
    private static final double INTERES_SOBREGIRO_PORCENTAJE = 0.50;
    private static final double INTERES_DIARIO = 0.01;
    private double limiteSobreGiro;
    private double interesGeneradoDia;
    private double interesGeneradoMes;
    private double interesGeneradoAnio;
    private int oportunidadesRetiro;

    public CuentaCorriente(String numero, Cliente titular, double saldoInicial, double limiteSobreGiro) {
        super(numero, titular, saldoInicial);
        this.limiteSobreGiro = limiteSobreGiro;
        this.interesGeneradoDia = 0;
        this.interesGeneradoMes = 0;
        this.interesGeneradoAnio = 0;
        this.oportunidadesRetiro = 3;
    }

    public double getLimiteSobreGiro() {
        return limiteSobreGiro;
    }

    public double getInteresGeneradoDia() {
        return interesGeneradoDia;
    }

    public double getInteresGeneradoMes() {
        return interesGeneradoMes;
    }

    public double getInteresGeneradoAnio() {
        return interesGeneradoAnio;
    }

    public int getOportunidadesRetiro() {
        return oportunidadesRetiro;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean retirar(double monto) {
        if (saldo + limiteSobreGiro >= monto) {
            setSaldo(saldo - monto);
            return true;
        } else {
            return false;
        }
    }

    public void actualizarInteresesDiarios() {
        interesGeneradoDia = 0;
    }

    public void actualizarInteresesMensuales() {
        interesGeneradoMes = 0;
    }

    public void actualizarInteresesAnuales() {
        interesGeneradoAnio = 0;
    }
}

//algunas op en construccion :(