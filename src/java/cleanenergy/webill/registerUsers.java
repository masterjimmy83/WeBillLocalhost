/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanenergy.webill;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author master
 */
public class registerUsers extends HttpServlet {

    private static Connection sqlConnection = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //String userID = request.getParameter("user_ID");   ********* No need since it will be Auto Generated
        String givenName = request.getParameter("given_Name");
        String SurName = request.getParameter("sur_Name");
        String secret = request.getParameter("secret_pass");
        String address = request.getParameter("user_Address");
        String email = request.getParameter("user_email");
        String meterID = request.getParameter("meter_ID");
        String userRole = request.getParameter("user_Role");
        try {
            /* TODO output your page here. You may use following sample code. */
            //String sql = "insert into users values ('" + userID + "', '" + givenName + "', '" + SurName + "', '" + secret + "', '" + address + "', '" + email + "', '" + meterID + "', '" + userRole + "')";
            
            // The belwo Query removed the userID since it will be auto Generated, and also specify the field to insert into
            String sql = "insert into customers (givenName,SurName,secret,address,email,meterID,userRole) values ('" + givenName + "', '" + SurName + "', '" + secret + "', '" + address + "', '" + email + "', '" + meterID + "', '" + userRole + "')";
            PreparedStatement pst = sqlConnection.prepareStatement(sql);
            boolean execute = pst.execute();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet customerList</title>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"imgcontainer\">"
                    + " <img src=\"cleanenergy.jpg\" alt=\"Logo\" class=\"logo\">\n"
                    + "</div>\n"
                    + "<hr/>\n"
                    + "<div class=\"container\"><h2>New User Registration</h2></div>\n");
            out.println("<div class=\"container\">Registered Successful</div>\n");
            out.println("<div class=\"container\" style=\"background-color:#f1f1f1\">\n"
                    + "<button type=\"button\" class=\"cancelbtn\"><a href=\"admin\">View Registered Customers</a></button>\n"
                    + "<button type=\"button\" class=\"cancelbtn\"><a href=\"registerUsers.html\">Register New Customer</a></button><br/><br/>\n"
                    + "<button type=\"button\"><a href=\"index.html\" class=\"backhomebtn\" > Log Out</a></button>\n"
                    + "</div>\n"
            );
            out.println("</body>");
            out.println("</html>");

        } catch (SQLException e) {
            out.println(e);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(registerUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(registerUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void init() throws ServletException {
        super.init();
        if (sqlConnection == null) {
            System.out.println("Establish a new Connection to the database.....");
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                //sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost/WeBill?" + "user=webill&password=itsme&useSSL=false");
                sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost/WeBill?" + "user=webill&password=itsme&useSSL=false");
                System.out.println("The Connection was Successful!");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                System.err.println("Error in establishing the connection to the database! Details follows");
                System.err.println(ex.toString());
            }
        }
    }
}
