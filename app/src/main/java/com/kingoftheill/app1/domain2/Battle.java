package com.kingoftheill.app1.domain2;

import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

/**
 * Created by Andrade on 12/12/2017.
 */

public class Battle {

    private DocumentReference attacker;

    private DocumentReference defender;

    private Date start;

    private boolean stop;

    private boolean attackerOnline;

    private boolean defenderOnline;

    private int value;

    public Battle(DocumentReference attacker, DocumentReference defender) {
        this.attacker = attacker;
        this.defender = defender;
        this.start = new Date();
        this.stop = false;
        this.attackerOnline = true;
        this.defenderOnline = false;
        this.value = 50;
    }

    public void setDefenderOnline(boolean defenderOnline) {
        this.defenderOnline = defenderOnline;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public DocumentReference getAttacker() {
        return attacker;
    }

    public DocumentReference getDefender() {
        return defender;
    }

    public Date getStart() {
        return start;
    }

    public boolean isAttackerOnline() {
        return attackerOnline;
    }

    public boolean isDefenderOnline() {
        return defenderOnline;
    }

    public int getValue() { return value;    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
