
package model;

import java.util.ArrayList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Perfil {
    private int idPerfil;
    private String nome;
    private int status;
    private ArrayList<Menu> menus;
    private ArrayList<Menu> naoMenus;
    
}
