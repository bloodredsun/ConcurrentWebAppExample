package com.bloodredsun.concurrent.actor;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.routing.CyclicIterator;
import akka.routing.InfiniteIterator;
import akka.routing.UntypedLoadBalancer;
import com.bloodredsun.concurrent.RemoteClient;

import static akka.actor.Actors.actorOf;
import static java.util.Arrays.asList;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Martin Anderson
 *         Date: 18/11/2011
 *         Time: 11:01
 */
public class MasterActor extends UntypedActor {

    private ActorRef router;

    static class LoadBalancer extends UntypedLoadBalancer {
        private final InfiniteIterator<ActorRef> workers;

        public LoadBalancer(ActorRef[] workers) {
            this.workers = new CyclicIterator<ActorRef>(asList(workers));
        }

        public InfiniteIterator<ActorRef> seq() {
            return workers;
        }
    }

    public MasterActor(int nrOfWorkers, final RemoteClient remoteClient) {
        // create the workers
        final ActorRef[] workers = new ActorRef[nrOfWorkers];
        for (int i = 0; i < nrOfWorkers; i++) {

            workers[i] = actorOf(
                    new UntypedActorFactory() {
                        public UntypedActor create() {
                            return new WorkerActor(remoteClient);
                        }
                    })
                    .start();
        }

        // wrap them with a load-balancing router
        router = actorOf(new UntypedActorFactory() {
            public UntypedActor create() {
                return new LoadBalancer(workers);
            }
        }).start();
    }

    // message handler
    public void onReceive(Object message) {

        if (message instanceof Works) {
            for (Work work : ((Works) message).getAll()) {
                router.tell(work);
            }
        }

    }

}


