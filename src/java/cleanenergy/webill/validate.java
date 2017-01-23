/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanenergy.webill;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author master
 */
public class validate extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            //Getting Parameters from the User Form in HTML File for Login!
            String userID = request.getParameter("userid");
            String secret = request.getParameter("password");
            String userRole = request.getParameter("userRole");

            // Creating the Hash code of the secret, since it does not saved as a plain text
            byte[] md5SecretBytes = MessageDigest.getInstance("MD5").digest(secret.getBytes());
            //we need the hash code as a string
            String md5Secret;
            StringBuilder strBuilder = new StringBuilder();
            String tmpStr;
            for (int i = 0; i < md5SecretBytes.length; i++) {
                tmpStr = Integer.toHexString((0xFF & md5SecretBytes[i]));
                if (tmpStr.length() == 1) {
                    strBuilder.append('0');
                }
                strBuilder.append(tmpStr);
            }
            md5Secret = strBuilder.toString();
            if (!userID.isEmpty() && !secret.isEmpty() && !userRole.isEmpty()) {
                if ("Admin".equals(userRole)){
                String idSecretMatchQuery = "select * from admins where userName='" + userID + "'and password ='" + md5Secret + "' and role ='" + userRole + "';";
                //select * from admins where userName ='admin-001' and password='df76807c147cb7e4348b55dd4e6cb48e'and role='Admin';
                ResultSet matchingUser = sqlConnection.createStatement().executeQuery(idSecretMatchQuery);
                if (matchingUser.next()) {

                    //Here we found a matching user in the DB
                    //Save the given name in the session.
                    //session.setAttribute("userName", matchingUser.getString("givenName"));
                    //out.println("Welcome " + matchingUser.getString("givenName") + " " + matchingUser.getString("SurName"));
                    //response.sendRedirect("web/fileUpload.html");
                    switch (userRole) {
                        case "Technician": {
                            session.setAttribute("userName", matchingUser.getString("userName"));
                            if (session.getAttribute("userName") == null) {
                                response.sendRedirect("index.html");
                            } else {
                                RequestDispatcher rd = request.getRequestDispatcher("fileUpload.html");
                                rd.forward(request, response);}
                                break;
                            }
                        
                    case "Admin":
                            {
                                session.setAttribute("userName", matchingUser.getString("userName"));
                                RequestDispatcher rd = request.getRequestDispatcher("admin");
                                rd.forward(request, response);
                                break;
                            }
                        default:
                            {
                                RequestDispatcher rd = request.getRequestDispatcher("index.html");
                                rd.forward(request, response);
                                break;
                            }
                    }

                } else {
                    // Mean the credential given are not correct, so send back to login page
                    response.sendRedirect("index.html");

                }
                }
              if ("Customer".equals(userRole)){ 
                  String idSecretMatchQuery = "select * from users where userID='" + userID + "'and secret ='" + md5Secret + "' and userRole ='" + userRole + "';";
                ResultSet matchingUser = sqlConnection.createStatement().executeQuery(idSecretMatchQuery);
                if (matchingUser.next()) {

                    //Here we found a matching user in the DB
                    //Save the given name in the session.
                    //session.setAttribute("userName", matchingUser.getString("givenName"));
                    //out.println("Welcome " + matchingUser.getString("givenName") + " " + matchingUser.getString("SurName"));
                    //response.sendRedirect("web/fileUpload.html");
                            session.setAttribute("userName", matchingUser.getString("givenName"));
                            if (session.getAttribute("userName") == null) {
                                response.sendRedirect("index.html");
                            } else {
                                RequestDispatcher rd = request.getRequestDispatcher("fileUpload.html");
                                rd.forward(request, response);}
              
              }
                
                }
            }

            }catch (NoSuchAlgorithmException | SQLException ex) {
            //Logger.getLogger(validate.class.getName()).log(Level.SEVERE, null, ex);
            //System.err.println("Error while getting the hashcode:"+ex.getMessage());
            System.err.println("Error in the Servlet");
            System.err.println(ex.getMessage());
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
        protected void doGet
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
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
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
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
