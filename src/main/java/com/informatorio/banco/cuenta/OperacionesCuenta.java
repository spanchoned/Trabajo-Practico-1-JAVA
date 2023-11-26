package com.informatorio.banco.cuenta;


public interface OperacionesCuenta {
    double depositar(double monto);
    boolean retirar(double monto);
    double consultarSaldo();
    void agregarIntereses();

}
