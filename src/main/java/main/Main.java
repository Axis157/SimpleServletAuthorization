package main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;
import servlets.SignInServlet;
import servlets.SignUpServlet;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия:
 *         https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class Main {
	public static void main(String[] args) throws Exception {
//		AccountService accountService = new AccountService();

//      accountService.addNewUser(new UserProfile("admin"));
//      accountService.addNewUser(new UserProfile("test"));
		DBService dbService = new DBService();
		dbService.printConnectInfo();

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//      context.addServlet(new ServletHolder(new UsersServlet(accountService)), "/api/v1/users");
//      context.addServlet(new ServletHolder(new SessionsServlet(accountService)), "/api/v1/sessions");
		context.addServlet(new ServletHolder(new SignInServlet(dbService)), "/signin");
		context.addServlet(new ServletHolder(new SignUpServlet(dbService)), "/signup");

		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setResourceBase("public_html");

		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { resource_handler, context });

		Server server = new Server(8080);
		server.setHandler(handlers);

		server.start();
		System.out.println("Server started");
		server.join();
	}
}
