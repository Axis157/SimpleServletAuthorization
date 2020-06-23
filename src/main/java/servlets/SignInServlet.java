package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;

public class SignInServlet extends HttpServlet{
	private final DBService dbService;
	
	public SignInServlet(DBService dbService) {
        this.dbService = dbService;
    }

    //get logged user profile
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
    	RequestDispatcher view = request.getRequestDispatcher("/public_html/index.html");

        view.forward(request, response); 
    }

    //sign in
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || password == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UsersDataSet profile = null;
		try {
			profile = dbService.getUser(login);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (profile == null || !profile.getPassword().equals(password)) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            System.out.println("Status code (401)\n Unauthorized");
            return;
        }

//        accountService.addSession(request.getSession().getId(), profile);
        Gson gson = new Gson();
        String json = gson.toJson(profile);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("Authorized: " + login);
        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println(login);
        System.out.println(password);
        System.out.println("Status code (200)\n Authorized: " + login);
    }

    //sign out
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
//        String sessionId = request.getSession().getId();
//        UserProfile profile = accountService.getUserBySessionId(sessionId);
//        if (profile == null) {
//            response.setContentType("text/html;charset=utf-8");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        } else {
//            accountService.deleteSession(sessionId);
//            response.setContentType("text/html;charset=utf-8");
//            response.getWriter().println("Goodbye!");
//            response.setStatus(HttpServletResponse.SC_OK);
//        }

    }
}
