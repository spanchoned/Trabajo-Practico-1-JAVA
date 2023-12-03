package com.informatorio.banco.dominio.dominios.banco;
import com.informatorio.banco.dominio.dominios.cliente.Cliente;
import com.informatorio.banco.dominio.dominios.cuentaBancaria.CuentaBancaria;

import java.util.*;

public class Banco {
    private List<Cliente> clientes;
    private List<CuentaBancaria> cuentasOrdenadas;



    public Banco() {
        this.clientes = new ArrayList<>();
        this.cuentasOrdenadas = new ArrayList<>();
    }

    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public List<CuentaBancaria> getCuentasDeCliente(Cliente cliente) {
        Set<CuentaBancaria> cuentasUnicas = new HashSet<>(cliente.getCuentas());
        return new ArrayList<>(cuentasUnicas);
    }

    public void registrarCuenta(Cliente cliente, CuentaBancaria cuenta) {
        cliente.agregarCuenta(cuenta);
        cuentasOrdenadas.add(cuenta);
    }

    public List<CuentaBancaria> getCuentasOrdenadas() {
        return cuentasOrdenadas;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void eliminarCuentaOrdenada(CuentaBancaria cuenta) {
        cuentasOrdenadas.remove(cuenta);
    }
}
