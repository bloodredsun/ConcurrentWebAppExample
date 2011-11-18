package com.bloodredsun.concurrent.web;

import com.bloodredsun.concurrent.RemoteClient;
import com.bloodredsun.concurrent.actor.MasterActor;
import com.bloodredsun.concurrent.task.MyRemoteCallable;
import org.mortbay.jetty.client.HttpClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

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
    MasterActor master

    public void init() throws ServletException{
        httpClient.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
        try {
            httpClient.start();
        } catch (Exception e) {
            throw new ServletException(e);
        }
        remoteClient.setHttpClient(httpClient);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
              try {
            List<MyRemoteCallable> myRemoteCallables = new ArrayList<MyRemoteCallable>();

            for (int ii = 0; ii < 10; ii++) {
                myRemoteCallables.add(new MyRemoteCallable(remoteClient));
            }

            List<Future<String>> futures = master.invokeAll(myRemoteCallables);


            PrintWriter writer = response.getWriter();
            writer.write("<html>");
            writer.write("<body>");
            writer.write("<p> In ThreadedServlet Servlet with Callables </p>");
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
