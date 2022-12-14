package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Produto {
    private int idProduto;
    private String nome;
    private String descricao;
    private double preco;
    private String nomeArquivo;
    private String caminho;
    private int status;
}
