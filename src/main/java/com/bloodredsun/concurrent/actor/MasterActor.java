package com.bloodredsun.concurrent.actor;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.routing.CyclicIterator;
import akka.routing.InfiniteIterator;
import akka.routing.UntypedLoadBalancer;
import com.bloodredsun.concurrent.RemoteClient;
import com.bloodredsun.concurrent.task.Work;
import com.bloodredsun.concurrent.task.Works;

import static akka.actor.Actors.actorOf;
import static java.util.Arrays.asList;

/**
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

    public MasterActor(final int numWorkers, final RemoteClient remoteClient) {
        // create an array of started workers
        final ActorRef[] workers = new ActorRef[numWorkers];
        for (int i = 0; i < numWorkers; i++) {
            workers[i] = actorOf(
                    new UntypedActorFactory() {
                        public UntypedActor create() {
                            return new WorkerActor(remoteClient);
                        }
                    })
                    .start();
        }

        // wrap all the workers with a load-balancing router
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


