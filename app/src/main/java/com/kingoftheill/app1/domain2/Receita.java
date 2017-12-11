package com.kingoftheill.app1.domain2;

import java.util.List;

/**
 * Created by João Pedro on 10/12/2017.
 */

public class Receita {

    private int id;
    private String name;
    private List<Integer> ingsReceita;
    private int resultId;
    private int progresso;
    private List<Boolean> ingsUsed;

    public Receita(){}

    public Receita (int id, String name, List<Integer>ingsReceita, int resultId, int progresso, List<Boolean>ingsUsed){
        this.id = id;
        this.name = name;
        this.ingsReceita = ingsReceita;
        this.resultId = resultId;
        this.progresso = progresso;
        this.ingsUsed = ingsUsed;
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public List<Integer> getIngsReceita(){return ingsReceita;}
    public int getResultId(){return resultId;}
    public int getProgresso(){return progresso;}
    public List<Boolean> getIngsUsed(){return ingsUsed;}
}
