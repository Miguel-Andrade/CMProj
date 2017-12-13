package com.kingoftheill.app1.domain2;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

/**
 * Created by João Pedro on 10/12/2017.
 */

public class Receita {

    private String name;
    private List<DocumentReference> ids;
    private DocumentReference result;
    private String image;
    private String description;
//    private int progresso;
//    private List<Boolean> ingsUsed;

    public Receita(){}

    public Receita (String name, List<DocumentReference> ids, DocumentReference result, String image, String description){
        this.name = name;
        this.ids = ids;
        this.result = result;
        this.image = image;
        this.description = description;
//        this.progresso = progresso;
//        this.ingsUsed = ingsUsed;
    }

    public String getName(){ return name; }

    public List<DocumentReference> getIds(){ return ids; }

    public DocumentReference getResult(){ return result; }

    public String getImage (){ return image; }

    public String getDescription() { return description; }

    //    public int getProgresso(){return progresso;}
//    public List<Boolean> getIngsUsed(){return ingsUsed;}
}
