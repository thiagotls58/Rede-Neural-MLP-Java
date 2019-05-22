/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp;

/**
 * Representa cada neurônio da camada de saída
 * @author Thiago
 */
public class NeuronioSaida {
    
    private double net;
    private double saida;
    private double erroSaida;
    private double erroGradiente;

    public double getNet() {
        return net;
    }

    public void setNet(double net) {
        this.net = net;
    }

    public double getSaida() {
        return saida;
    }

    public void setSaida(double saida) {
        this.saida = saida;
    }

    public double getErroSaida() {
        return erroSaida;
    }

    public void setErroSaida(double erroSaida) {
        this.erroSaida = erroSaida;
    }

    public double getErroGradiente() {
        return erroGradiente;
    }

    public void setErroGradiente(double erroGradiente) {
        this.erroGradiente = erroGradiente;
    }
    
    
    
}
