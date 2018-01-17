package servlets;

import dao.OrderDao;
import dao.daoImpl.OrderDaoImpl;
import model.OrderList;
import service.OrderService;
import service.serviceImpl.OrderServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet implementation class Home
 */
@WebServlet("/order")
public class Order extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;
    //一页有多少订单条目
    private int numOfpage;
    //一共有多少条订单
    private int numOfOrder;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Order() {
        super();
        numOfpage = 20;
        orderService = new OrderServiceImpl();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session==null||session.getAttribute("userID")==null){
            getServletContext().getRequestDispatcher("/home").forward(
                    request, response);
        }
        String userID = (String) session.getAttribute("userID");
        int curPage = Integer.parseInt(request.getParameter("curPage"));
        numOfOrder = orderService.getNumOfOrders(userID);
        ServletContext context= getServletContext();
        if (numOfOrder == 0) {
            request.setAttribute("errorMessage", "您没有任何订单");
            context.getRequestDispatcher("/view/errorPage.jsp").forward(
                    request, response);
        } else {
            initPage((curPage-1)*numOfpage,userID,curPage,response,request);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    /**
     * 初始化整个order界面
     * */
    protected void initPage(int start,String userID,int curPage,HttpServletResponse response,HttpServletRequest request) throws ServletException, IOException {
        List<model.Order>  orders =orderService.getOrdersOfThePage(userID,start,numOfpage);

        ServletContext Context= getServletContext();
        int loginCounter= Integer.parseInt((String) Context.getAttribute("loginCounter"));
        int allCounter = Integer.parseInt((String) Context.getAttribute("allCounter"));

        String buttonField = initButtonField(curPage);
        String  location= response.encodeURL(request.getContextPath() + "/order?curPage=");

        request.setAttribute("loginCounter", loginCounter);
        request.setAttribute("allCounter", allCounter);
        request.setAttribute("buttonField", buttonField);
        request.setAttribute("location", location);
        request.setAttribute("curPage", curPage);

        OrderList orderList = new OrderList();
        orderList.setOrderList(orders);
        request.getSession(true).setAttribute("orderList", orderList);
        Context.getRequestDispatcher("/view/order.jsp").forward(request, response);
    }
    /**
     * 初始化页码区域
     * */
    private String initButtonField(int curPage){
        String buttonField = "";
        int pageNum = (int)Math.ceil(numOfOrder*1.0/numOfpage);
        if(pageNum<=9){
            for (int i = 1; i < pageNum ; i++){
                buttonField += "<button id=\""+i+"\" onclick=gotoPage()>"+i+"</button>\n";
            }
        }else{
            if(curPage-1 > 4 && pageNum - curPage > 4 ){
                buttonField +="<button id=\""+1+"\" onclick=gotoPage()>"+1+"</button>\n";
                buttonField +="<button id=\""+(curPage-4)+"\" onclick=gotoPage()>…</button>\n";
                for (int i = curPage-3;i<=curPage+3;i++){
                    buttonField += "<button id=\""+i+"\" onclick=gotoPage()>"+i+"</button>\n";
                }
                buttonField +="<button id=\""+(curPage+4)+"\" onclick=gotoPage()>…</button>\n";
                buttonField +="<button id=\""+pageNum+"\" onclick=gotoPage()>"+pageNum+"</button>\n";
            }
            else if (curPage-1<=4){
                for (int i = 1; i<=7;i++){
                    buttonField += "<button id=\""+i+"\" onclick=gotoPage()>"+i+"</button>\n";
                }
                buttonField +="<button id=\""+8+"\" onclick=gotoPage()>…</button>\n";
                buttonField +="<button id=\""+pageNum+"\" onclick=gotoPage()>"+pageNum+"</button>\n";
            }else{
                buttonField +="<button id=\""+1+"\" onclick=gotoPage()>"+1+"</button>\n";
                buttonField +="<button id=\""+(pageNum-7)+"\" onclick=gotoPage()>…</button>\n";
                for (int i = pageNum-7;i<=pageNum;i++){
                    buttonField += "<button id=\""+i+"\" onclick=gotoPage()>"+i+"</button>\n";
                }
            }
        }
        return buttonField;
    }
}
