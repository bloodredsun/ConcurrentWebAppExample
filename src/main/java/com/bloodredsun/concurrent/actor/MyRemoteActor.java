package com.bloodredsun.concurrent.actor;

import akka.actor.UntypedActor;
import com.bloodredsun.concurrent.RemoteClient;

public class MyRemoteActor extends UntypedActor {

    RemoteClient remoteClient;

    public MyRemoteActor(RemoteClient remoteClient) {
        this.remoteClient = remoteClient;
    }

    public void onReceive(Object message) throws Exception {


    }
}