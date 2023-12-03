    package com.informatorio.banco.dominio.dominios.cuentaBancaria;

    import com.informatorio.banco.dominio.dominios.cliente.Cliente;

    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;


    public abstract class CuentaBancaria {
        private static int contadorCuentas = 1;
        private String numero;
        private Cliente titular;
        protected double saldo;
    
        public CuentaBancaria(Cliente titular, double saldo) {
            this.numero = "CB" + contadorCuentas++;
            this.titular = titular;
            this.saldo = saldo;
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
    
        public abstract void depositar(double monto);
    
        public abstract boolean retirar(double monto);
    
        public double consultarSaldo() {
            return saldo;
        }
    
        public void setSaldo(double saldo) {
            this.saldo = saldo;
        }

        public abstract void calcularInteres();

        public void realizarDeposito(Cliente cliente, double monto) {
            List<CuentaBancaria> cuentasActualizadas = new ArrayList<>();

            for (CuentaBancaria cuenta : cliente.getCuentas()) {
                cuenta.depositar(monto);
                System.out.println("Depósito de " + monto + " realizado en la cuenta " + cuenta.getNumero());
                System.out.println("Nuevo saldo: " + cuenta.consultarSaldo());
                cuentasActualizadas.add(cuenta);
            }

            cliente.setCuentas(cuentasActualizadas);
        }

        public void realizarRetiro(Cliente cliente, double monto) {
            Set<CuentaBancaria> cuentasActualizadas = new HashSet<>();

            for (CuentaBancaria cuenta : cliente.getCuentas()) {
                boolean retiroExitoso = cuenta.retirar(monto);
                if (retiroExitoso) {
                    System.out.println("Retiro de " + monto + " realizado en la cuenta " + cuenta.getNumero());
                    System.out.println("Nuevo saldo: " + cuenta.consultarSaldo());
                    cuentasActualizadas.add(cuenta);
                } else {
                    System.out.println("Fondos insuficientes en la cuenta " + cuenta.getNumero() +
                            " para realizar el retiro de " + monto);
                }
            }

            cliente.setCuentas(new ArrayList<>(cuentasActualizadas));
        }

        public static void agregarIntereses(Cliente cliente) {
            for (CuentaBancaria cuenta : cliente.getCuentas()) {
                cuenta.calcularInteres();
            }
        }

        public abstract void eliminarCuenta(List<CuentaBancaria> cuentas, String numeroCuenta);
    }
