package uemapdm.com.carwasher.entidades;

import java.io.Serializable;

/**
 * Created by rodri on 06/06/2017.
 */

public class Veiculo implements Serializable {
    private String placa;
    private String modelo;
    private String cor;
    private String marca;

    Veiculo() { }

    public Veiculo(String placa, String marca, String modelo, String cor) {
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
