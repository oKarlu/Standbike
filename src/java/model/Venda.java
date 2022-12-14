
package model;


import java.util.ArrayList;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Venda {
    private int idVenda;
    private Date dataVenda;
    private Usuario vendedor;
    private Cliente cliente;
    private ArrayList<VendaProduto> carrinho;
    private double valorTotal;
    private int status;
}
