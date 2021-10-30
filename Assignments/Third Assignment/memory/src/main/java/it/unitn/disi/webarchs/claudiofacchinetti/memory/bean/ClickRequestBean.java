package it.unitn.disi.webarchs.claudiofacchinetti.memory.bean;

public class ClickRequestBean {

    private Integer rowIndex;
    private Integer colIndex;

    public ClickRequestBean() {
    }

    public ClickRequestBean(Integer rowIndex, Integer colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Integer getColIndex() {
        return colIndex;
    }

    public void setColIndex(Integer colIndex) {
        this.colIndex = colIndex;
    }
}
