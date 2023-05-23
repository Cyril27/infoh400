/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.infoh400.labs2022.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author Adrien Foucart
 */
public class EntityListModel<T> extends AbstractListModel {  // sous classe de abstract list
    
    private List<T> entities;   // main feature : sait prendre list avec objets de différents types (use of template <T> remplacer par la classe)
    
    public EntityListModel(List<T> entities){
        if( entities == null ){
            entities = new ArrayList();
        }
        this.entities = entities;
    }
    
    public void setList(List<T> entities){
        this.entities = entities;
    }
    
    public List<T> getList(){
        return entities;
    }
    
    @Override
    public int getSize() {         // donne la taille de la liste
        return entities.size();
    }

    @Override
    public Object getElementAt(int index) {  // quel est l'élément à une certaine adresse
        return entities.get(index);
    }
    
}
