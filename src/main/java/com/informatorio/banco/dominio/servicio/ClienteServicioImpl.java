package com.informatorio.banco.dominio.servicio;

import com.informatorio.banco.dominio.dominios.cliente.Cliente;
import com.informatorio.banco.dominio.dominios.banco.Banco;

import java.io.IOException;



public class ClienteServicioImpl implements ClienteServicio {
    private Banco banco;

    public ClienteServicioImpl(Banco banco) {
        this.banco = banco;
    }

    @Override
    public void registrarCliente(Cliente cliente) {
        banco.registrarCliente(cliente);
    }

    public void ofrecerServicios() {
        System.out.println("Bienvenido a nuestro banco. Ofrecemos servicios como abrir cuentas y gestionar clientes.");
    }


    public void exportarCuentasCSV() {
        ExportadorCSV.exportarACSV(banco.getClientes(), "ruta/del/archivo.csv");
    }
    }
