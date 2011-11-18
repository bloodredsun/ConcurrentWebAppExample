package com.bloodredsun.concurrent.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Martin Anderson
 *         Date: 18/11/2011
 *         Time: 13:28
 */
public class Works {

    private List<Work> works = new ArrayList<Work>();

    public void add(Work work){
        works.add(work);
    }

    public List<Future<String>> getFutures(){
        List<Future<String>> futures = new ArrayList<Future<String>>();
        for(Work work : works){
            futures.add(work.getFutureResponse());
        }
        return futures;
    }

    public List<Work> getAll() {
        return works;
    }
}
