/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp;

/**
 * Representa cada neurônio da camada oculta
 * @author Thiago
 */
public class NeuronioOculto {
    
    private double net;
    private double saida;
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

    public double getErroGradiente() {
        return erroGradiente;
    }

    public void setErroGradiente(double erroGradiente) {
        this.erroGradiente = erroGradiente;
    }
    
    
    
}
