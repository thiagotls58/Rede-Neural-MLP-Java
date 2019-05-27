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
public class Treino {
    
    /**
     * Número do treinamento.
     */
    private int id;
    
    /**
     * Erro médio da rede neural.
     */
    private double erro;
    
    /**
     * Função de ativação utilizada na rede.
     */
    private FuncaoAtivacao ativacao;
    
    /**
     * Taxa de aprendizado utilizada na rede.
     */
    private double taxaAprendizado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getErro() {
        return erro;
    }

    public void setErro(double erro) {
        this.erro = erro;
    }

    public FuncaoAtivacao getAtivacao() {
        return ativacao;
    }

    public void setAtivacao(FuncaoAtivacao ativacao) {
        this.ativacao = ativacao;
    }

    public double getTaxaAprendizado() {
        return taxaAprendizado;
    }

    public void setTaxaAprendizado(double taxaAprendizado) {
        this.taxaAprendizado = taxaAprendizado;
    }
    
    @Override
    public String toString() {
        
        String func = "";
        if (this.ativacao == FuncaoAtivacao.LINEAR) {
            func = "Linear";
        } else if (this.ativacao == FuncaoAtivacao.LOGISTICA) {
            func = "Logística";
        } else if (this.ativacao == FuncaoAtivacao.HIPERBOLICA) {
            func = "Hiperbólica";
        }
        
        String strErro = String.format("%.6f", erro);
        
        return "Treino: " + id + ", Taxa Apr.: " + taxaAprendizado + 
                ", Ativação: " + func + ", Erro: " + strErro;
    }
    
}
