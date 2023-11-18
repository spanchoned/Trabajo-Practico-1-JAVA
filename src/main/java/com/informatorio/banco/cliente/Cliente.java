package com.informatorio.banco.cliente;

import com.informatorio.banco.cuenta.Cuenta;
import com.informatorio.banco.servicio.archivo.ArchivoServicio;
import com.informatorio.banco.cuenta.CuentaAhorro;
import com.informatorio.banco.cuenta.CuentaCorriente;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nombre;
    private String direccion;
    private List<Cuenta> cuentas;
    private double saldoCuentaAhorro;
    private double saldoCuentaCorriente;
   // private ArchivoServicio archivoServicio;

    public Cliente(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.cuentas = new ArrayList<>();
    }
    
    public String getNumeroUnico() {
        return java.util.UUID.randomUUID().toString();

    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion(){
        return direccion;
    }


    public boolean realizarOperacionEnCuenta(int numeroCuenta, double monto) {
        return false;
    }

    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

    public void eliminarCuenta(String numeroCuenta) {
        cuentas.removeIf(cuenta -> cuenta.getNumero().equals(numeroCuenta));
    }
    
       public void mostrarInformacion() {
            System.out.println("Nombre: " + nombre);
            System.out.println("Dirección: " + direccion);
            
            System.out.println("Saldo Cuenta de Ahorro: " + saldoCuentaAhorro);
            
            System.out.println("Saldo Cuenta Corriente: " + saldoCuentaCorriente);
        }
    
        public double realizarDepositoEnCuenta(double monto) {
            saldoCuentaAhorro += monto;
            return saldoCuentaAhorro;
        }
        
        public double realizarRetiroCuenta(double monto) {
            saldoCuentaCorriente -= monto;
            return saldoCuentaCorriente;
        }
        
        public double abrirCuentaAhorro(double montoInicial) {
            saldoCuentaAhorro = montoInicial;
            return saldoCuentaAhorro;
        }
        
        public double abrirCuentaCorriente(double montoInicial) {
            saldoCuentaCorriente = montoInicial;
            return saldoCuentaCorriente;
        }
        
        public double consultarSaldoTotal() {
            return saldoCuentaAhorro + saldoCuentaCorriente;
        }
        
        public String exportarCuentasCSV() {
            StringBuilder csv = new StringBuilder();
            csv.append("Número único del titular,Nombre de titular,Numero de cuenta,Saldo,Tipo\n");
            for (Cuenta cuenta : cuentas) {
                csv.append(getNumeroUnico()).append(",")
                   .append(getNombre()).append(",")
                   .append(cuenta.getNumero()).append(",")
                   .append(cuenta.getSaldo()).append(",")
                   .append((cuenta instanceof CuentaAhorro) ? "Ahorro" : "Corriente").append("\n");
            }
            return csv.toString();
        }
        
    }
    

