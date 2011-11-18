package com.bloodredsun.concurrent.web;

import com.bloodredsun.concurrent.RemoteClient;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadedServlet extends HttpServlet {

    HttpClient httpClient = new HttpClient();
    ExecutorService executorService = Executors.newCachedThreadPool();
    RemoteClient remoteClient = new RemoteClient();

    public void init() throws ServletException {
        httpClient.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
        try {
            httpClient.start();
        } catch (Exception e) {
            throw new ServletException(e);
        }
        remoteClient.setHttpClient(httpClient);
    }

    public void destroy() {
        try {
            httpClient.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            List<MyRemoteCallable> myRemoteCallables = new ArrayList<MyRemoteCallable>();

            for (int ii = 0; ii < 10; ii++) {
                myRemoteCallables.add(new MyRemoteCallable(remoteClient));
            }

            List<Future<String>> futures = executorService.invokeAll(myRemoteCallables);


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
