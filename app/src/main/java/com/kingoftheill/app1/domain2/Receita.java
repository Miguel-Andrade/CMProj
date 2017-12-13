package com.kingoftheill.app1.domain2;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

/**
 * Created by João Pedro on 10/12/2017.
 */

public class Receita {

    private String name;
    private List<DocumentReference> ingsReceita;
    private DocumentReference result;
    private String image;
//    private int progresso;
//    private List<Boolean> ingsUsed;

    public Receita(){}

    public Receita (String name, List<DocumentReference>ingsReceita, DocumentReference result, String image){
        this.name = name;
        this.ingsReceita = ingsReceita;
        this.result = result;
        this.image = image;
//        this.progresso = progresso;
//        this.ingsUsed = ingsUsed;
    }

    public String getName(){return name;}
    public List<DocumentReference> getIngsReceita(){return ingsReceita;}
    public DocumentReference getResult(){return result;}
    public String getImage (){ return image;}
//    public int getProgresso(){return progresso;}
//    public List<Boolean> getIngsUsed(){return ingsUsed;}
}
