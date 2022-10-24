
package model;


import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Venda {
    private int idVenda;
    private Cliente cliente;
    private Produto produto;
    private double valorTotal;
    private int status;
}
