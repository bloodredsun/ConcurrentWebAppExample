package com.bloodredsun.concurrent.web;

import akka.actor.ActorRef;
import akka.actor.Actors;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import com.bloodredsun.concurrent.RemoteClient;
import com.bloodredsun.concurrent.actor.MasterActor;
import com.bloodredsun.concurrent.task.Work;
import com.bloodredsun.concurrent.task.Works;
import com.bloodredsun.util.MemoryStats;
import org.mortbay.jetty.client.HttpClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static akka.actor.Actors.actorOf;

/**
 * @author Martin Anderson
 *         Date: 18/11/2011
 *         Time: 10:50
 */

public class ActorServlet extends HttpServlet {

    HttpClient httpClient = new HttpClient();
    RemoteClient remoteClient = new RemoteClient();
    ActorRef master;

    public void init() throws ServletException {
        httpClient.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
        try {
            httpClient.start();
        } catch (Exception e) {
            throw new ServletException(e);
        }
        remoteClient.setHttpClient(httpClient);

        master = actorOf(new UntypedActorFactory() {
            public UntypedActor create() {
                return new MasterActor(4, remoteClient);
            }
        }).start();
    }

    public void destroy(){
       Actors.registry().shutdownAll();
        try {
            httpClient.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            long t0 = System.currentTimeMillis();

            Works works = new Works();
            for (int ii = 0; ii < 10; ii++) {
                works.add(new Work("http://localhost:8080/endpoint.jsp?pong=actors%20rule"));
            }
            master.tell(works);

            List<Future<String>> futures = works.getFutures();


            PrintWriter writer = response.getWriter();
            writer.write("<html>");

            writer.write("<body>");
            writer.write("<p> In ActorServlet Servlet</p>");

            for (Future future : futures) {
                writer.write("<p> Work Future has returned value: '" + future.get() + "' </p>");
            }

            long t1 = System.currentTimeMillis() - t0;
            writer.write("<p>Took " + t1 + "ms");
            writer.write("</body>");
            writer.write("</html>");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IOException("Oops", e);
        }
    }
}
