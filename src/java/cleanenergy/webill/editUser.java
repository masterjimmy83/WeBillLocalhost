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
import javax.servlet.http.HttpSession;

/**
 *
 * @author master
 */
public class editUser extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
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
                    + "<div class=\"container\"><h2>Customer List</h2></div>\n"
            );
            String sql = "select userID, givenName, SurName, address, email, meterID from customers where userRole='Customer';";
            PreparedStatement pst = sqlConnection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            out.println("<div class=\"container\" id=\"container\">");
            String str = "<table><tr><th>Operation</th><th>User ID</th><th>First Name</th><th>Sur Name</th><th>Address</th><th>e-Mail</th><th>Meter Number</th></tr>";
            while (rs.next()) {
                               
String userID = rs.getString("userID");
            String givenName = rs.getString("givenName");
            String SurName = rs.getString("SurName"); 
            String address = rs.getString("address"); 
            String email = rs.getString("email"); 
            String meterID = rs.getString("meterID"); 
            String id = rs.getString("userID");

// Enable Session here and take the session ID
HttpSession session = request.getSession(true); 
session.setAttribute("userID",id);
//out.println("<tr><td align=\"center\">" + singername + "</td><td align=\"center\">" + stagename + "</td><td align=\"center\">" + language + "</td><td align=\"center\"><a href = \"./deleterow?userID="+id+"\">Delete</a></td></tr>");

// My First Code, which I have to Correct.....
                str += "<tr><td>&nbsp;<a href=\"editUser\">Edit</a>&nbsp;&nbsp;<a href=\"deleteCustomer?userID="+id+"\">Delete</a>&nbsp;</td><td>" + userID + "</td><td>" + givenName + "</td><td>" + SurName + "</td><td>" + address + "</td><td>" + email + "</td><td>" + meterID + "</td></tr>";
                                                                                
            }
            str += "</table>";
            out.println(str);
            out.println("</div>");

            out.println("<div class=\"container\" style=\"background-color:#f1f1f1\">\n"
                    + "<button type=\"button\" class=\"cancelbtn\"><a href=\"admin\">View Clean Energy Customer List</a></button>\n"
                    + "<button type=\"button\" class=\"cancelbtn\"><a href=\"registerUsers.jsp\">Register New Customer</a></button><br/><br/>\n"
                    + "<button type=\"button\"><a href=\"index.html\" class=\"backhomebtn\" > Log Out</a></button>\n"
                    + "</div>\n"
            );

            out.println("</body>");
            out.println("</html>");
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
