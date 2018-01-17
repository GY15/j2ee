package servlets;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Home
 */
@WebServlet("/home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login="";
		HttpSession session = request.getSession(false);
		Cookie cookie = null;

        ServletContext Context= getServletContext();
        int allCounter = Integer.parseInt((String) Context.getAttribute("allCounter"));
        int loginCounter = Integer.parseInt((String) Context.getAttribute("loginCounter"));

        if(session==null) {
            request.getSession(true);
            System.out.println("create session");
            allCounter++;
            Context.setAttribute("allCounter", Integer.toString(allCounter));
        }else{
            if(session.getAttribute("userID")!=null){
                loginCounter--;
                session.removeAttribute("userID");
                Context.setAttribute("loginCounter", Integer.toString(loginCounter));
            }
        }



        Cookie[] cookies = request.getCookies();

        if (null != cookies) {
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals("loginID")) {
                    login=cookie.getValue();
                    break;
                }
            }
        }
        request.setAttribute("login",login);
        request.setAttribute("allCounter",allCounter);
        request.setAttribute("loginCounter",loginCounter);
        Context.getRequestDispatcher("/view/login.jsp").forward(
                request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
