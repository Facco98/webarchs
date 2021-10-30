package it.unitn.disi.webarchs.claudiofacchinetti.memory.bean;

import java.util.Collection;

public class RankingBean {

    private Collection<RankingEntryBean> entries;

    public RankingBean() {
    }

    public RankingBean(Collection<RankingEntryBean> entries) {
        this.entries = entries;
    }

    public Collection<RankingEntryBean> getEntries() {
        return entries;
    }

    public void setEntries(Collection<RankingEntryBean> entries) {
        this.entries = entries;
    }
}
