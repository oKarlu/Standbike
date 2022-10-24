package service;

import model.Produto;

public interface VendaService {
    
    Produto controleDeEstoque(Produto produto, int quantidade);
}
