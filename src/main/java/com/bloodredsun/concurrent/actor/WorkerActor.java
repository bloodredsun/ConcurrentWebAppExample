package com.bloodredsun.concurrent.actor;

import akka.actor.UntypedActor;
import com.bloodredsun.concurrent.RemoteClient;
import com.bloodredsun.concurrent.task.Work;

/**
 * @author Martin Anderson
 *         Date: 18/11/2011
 *         Time: 11:39
 */
public class WorkerActor extends UntypedActor {

    RemoteClient remoteClient;

    public WorkerActor() {
    }

    public WorkerActor(RemoteClient remoteClient) {
        this.remoteClient = remoteClient;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Work) {
            Work work = (Work) message;
            //work.setFutureResponse("local futureResponse");
            remoteClient.execute(work);
        }
    }


}
