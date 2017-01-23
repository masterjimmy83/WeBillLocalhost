/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanenergy.webill;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author itsme This Servlet will show the file that the user has uploaded or
 * send him/her to the fileUpload.html page
 */
public class showUploads extends HttpServlet {

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

        //The full path to the file which has been uploaded should have been
        //saved in the session. Get it.
        HttpSession session = request.getSession(true);
        String imageFilePath = (String) session.getAttribute("imageFilePath");
        if(session.getAttribute("userName") == null){
            response.sendRedirect("index.html");
        } else {
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <title>WeBill file upload page</title>\n"
                    + "        <meta charset=\"UTF-8\">\n"
                    + " <link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\">\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "<div class=\"imgcontainer\">\n"
                    + "                <img src=\"cleanenergy.jpg\" alt=\"Logo\" class=\"logo\">\n"
                    + "            </div>"
                    + "        <hr />\n"
                    + "<div><h2>Uploaded Files</h2></div>"
                    + "<div class=\"container\">\n"
                    + "                <h3> " + imageFilePath.substring(imageFilePath.lastIndexOf("_") + 1) + " </h3>\n"
                    + "            </div>"
                    + "                <div class=\"container\" style=\"background-color:#f1f1f1\">\n"
                    + "                    <button type=\"button\" class=\"cancelbtn\"><a href=\"GPSReader\">Read GPS information of the file.</a></button>\n"
                    + "                    <button type=\"button\" class=\"cancelbtn\"><a href=\"qrReader\">Read the information from the QR code in the file.</a></button><br/><br/>\n"
                    + "                    <button type=\"button\"><a href=\"logout\" class=\"backhomebtn\" > Log Out</a></button>\n"
                    + "            </div>"
                    + "    </body>\n"
                    + "</html>");
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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

}
