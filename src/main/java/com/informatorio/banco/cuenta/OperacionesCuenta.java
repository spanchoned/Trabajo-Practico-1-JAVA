package com.informatorio.banco.cuenta;


public interface OperacionesCuenta {
    double depositar(double monto);
    double retirar(double monto);
    double consultarSaldo();
}
