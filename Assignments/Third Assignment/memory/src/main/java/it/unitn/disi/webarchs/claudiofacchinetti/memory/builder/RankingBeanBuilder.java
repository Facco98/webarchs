package it.unitn.disi.webarchs.claudiofacchinetti.memory.builder;

import it.unitn.disi.webarchs.claudiofacchinetti.memory.bean.RankingBean;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.bean.RankingEntryBean;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.model.Ranking;
import it.unitn.disi.webarchs.claudiofacchinetti.memory.model.RankingEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class RankingBeanBuilder {

    private Ranking ranking;

    public RankingBeanBuilder() {
    }

    public RankingBeanBuilder setRanking(Ranking ranking) {
        this.ranking = ranking;
        return this;
    }

    public RankingBean build(){
        RankingBean rankingBean = new RankingBean(new ArrayList<>());
        ArrayList<RankingEntry> rankingCopy = new ArrayList<>(ranking);
        Collections.sort(rankingCopy);

        Iterator<RankingEntry> i = rankingCopy.iterator();
        int max = 5;
        int current = 0;

        while( i.hasNext() && current < max ){
            RankingEntry re = i.next();
            current += 1;

            RankingEntryBean reb = new RankingEntryBean(re.getUsername(), re.getGame().getGameState().getScore());
            rankingBean.getEntries().add(reb);
        }

        return rankingBean;

    }
}
