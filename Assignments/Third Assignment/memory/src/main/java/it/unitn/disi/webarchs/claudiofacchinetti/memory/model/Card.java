package it.unitn.disi.webarchs.claudiofacchinetti.memory.model;

public class Card {

    private Integer value;
    private boolean faceUp;

    public Card(Integer value) {
        this.value = value;
        this.faceUp = false;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }



    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }
    public void flip(){
        this.faceUp = !this.faceUp;
    }

    @Override
    public boolean equals(Object obj) {

        if( !(obj instanceof Card ) )
            return false;
        Card o = (Card) obj;
        return this.value.equals(o.value);


    }
}
