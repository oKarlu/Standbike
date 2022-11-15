package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class VendaProduto {
    private long idVendaProduto;
    private int qtd;
    private double precoUnitario;
    private Venda venda;
    private Produto produto = new Produto();
    
    public VendaProduto(Produto produto, int qtd){
        this.produto = produto;
        this.qtd = qtd;
    }
    
}
