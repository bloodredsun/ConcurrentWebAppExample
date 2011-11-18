package com.bloodredsun.concurrent.actor;

import akka.actor.UntypedActor;
import com.google.common.util.concurrent.SettableFuture;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Martin Anderson
 *         Date: 18/11/2011
 *         Time: 11:39
 */
public class SimpleWorkerActor extends UntypedActor {

    public SimpleWorkerActor() {
    }


    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof SettableFuture) {
            ((SettableFuture) message).set("Set from local actor");
        }
    }


}
