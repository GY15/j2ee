package servlets;

import service.UserService;
import service.serviceImpl.UserServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet implementation class Home
 */
@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UserService userService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        userService = new UserServiceImpl();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext Context = getServletContext();
        String loginID = request.getParameter("id");
        String remember = request.getParameter("remember");

        HttpSession session = request.getSession(false);

        if (remember != null) {
            Cookie myCookie = new Cookie("loginID", loginID);
            response.addCookie(myCookie);
            myCookie.setMaxAge(60 * 60 * 24);
        }

        if (userService.login(loginID,request.getParameter("password"))) {
            if (session==null) {
                session = request.getSession(true);
                session.setAttribute("userID", loginID);
            }else{
                session.setAttribute("userID", loginID);
            }

            int loginCounter= Integer.parseInt((String) Context.getAttribute("loginCounter"));
            loginCounter++;
            Context.setAttribute("loginCounter", Integer.toString(loginCounter));
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() 	+ "/order?curPage=1"));
        } else {
            request.setAttribute("errorMessage", "账号或者密码错误");
            Context.getRequestDispatcher("/view/errorPage.jsp").forward(
                    request, response);
        }
    }

}
