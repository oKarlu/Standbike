package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class VendaProduto {
    private int  idVendaProduto;
    private int quantidade;
    private double valor;
    private Venda venda;
    private Produto produto;
    
    
}
