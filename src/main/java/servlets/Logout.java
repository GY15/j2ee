package servlets;

import dao.UserDao;
import dao.daoImpl.UserDaoImpl;
import model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class Home
 */
@WebServlet("/logout")
public class Logout extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext Context = getServletContext();
        HttpSession session = request.getSession(false);//防止创建Session
        if(session!=null) {
            if (session.getAttribute("userID") != null) {
                int counter = Integer.parseInt((String) Context.getAttribute("loginCounter"));
                counter--;
                Context.setAttribute("loginCounter", Integer.toString(counter));
            }
        }
        session.removeAttribute("userID");
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+"/home"));
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}
