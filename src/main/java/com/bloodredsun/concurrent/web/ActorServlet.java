package com.bloodredsun.concurrent.web;

import akka.actor.ActorRef;
import akka.actor.Actors;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import com.bloodredsun.concurrent.RemoteClient;
import com.bloodredsun.concurrent.actor.MasterActor;
import com.bloodredsun.concurrent.actor.Work;
import com.bloodredsun.concurrent.actor.Works;
import org.mortbay.jetty.client.HttpClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Future;

import static akka.actor.Actors.actorOf;

/**
 * Created by IntelliJ IDEA.
 *
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
                return new MasterActor(4);
            }
        }).start();
    }

    public void destroy(){
       Actors.registry().shutdownAll();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            Works works = new Works();

            for (int ii = 0; ii < 10; ii++) {
                works.add(new Work());

            }
            master.tell(works);

            List<Future<String>> futures = works.getFutures();


            PrintWriter writer = response.getWriter();
            writer.write("<html>");
            writer.write("<body>");
            writer.write("<p> In ActorServlet Servlet with Callables </p>");
            for (Future future : futures) {
                writer.write("<p> Callable has returned value: '" + future.get() + "' </p>");
            }
            writer.write("</body>");
            writer.write("</html>");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IOException("Oops", e);
        }
    }
}
