/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp;

/**
 *
 * @author Thiago
 */
public class RedeNeural {

    private double taxaAprendizado;
    private int nmrNeuroniosEntrada;
    private int nmrNeuroniosOculta;
    private int nmrEpocas;
    private double [][] pesosCamadaEntrada;
    private double [][] pesosCamadaOculta;

    public RedeNeural(double taxaAprendizado, int nmrNeuroniosEntrada, int nmrNeuroniosOculta) {
        this.taxaAprendizado = taxaAprendizado;
        this.nmrNeuroniosEntrada = nmrNeuroniosEntrada;
        this.nmrNeuroniosOculta = nmrNeuroniosOculta;
        this.inicializarPesos();
    }

    public int getNmrEpocas() {
        return nmrEpocas;
    }

    public void setNmrEpocas(int nmrEpocas) {
        this.nmrEpocas = nmrEpocas;
    }

    private void inicializarPesos() {
        for (int i = 0; i < nmrNeuroniosEntrada; i++) {
            for (int j = 0; j < nmrNeuroniosOculta; j++) {
                pesosCamadaEntrada[i][j] = Math.random();
                pesosCamadaOculta[i][j] = Math.random();
            }
        }
    }
}
