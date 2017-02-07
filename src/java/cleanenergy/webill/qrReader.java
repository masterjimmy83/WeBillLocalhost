/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanenergy.webill;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import static java.nio.channels.AsynchronousFileChannel.open;
import static java.nio.channels.AsynchronousFileChannel.open;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author master
 */
public class qrReader extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        String imageFilePath = (String) session.getAttribute("imageFilePath");
        PrintWriter out = response.getWriter();
                if(session.getAttribute("userName") == null){
            response.sendRedirect("index.html");
        } else {

            try {
                // HTML Settings and Styles......

                out.println("<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "    <head>\n"
                        + "        <title>WeBill file upload page</title>\n"
                        + "        <meta charset=\"UTF-8\">\n"
                        + " <style>\n"
                        + " form {\n"
                        + "    border: 3px solid #f1f1f1; \n"
                        + "}\n"
                        + "input[type=text], input[type=password] {\n"
                        + "   width: auto;\n"
                        + "   padding: 12px 20px;\n"
                        + " margin: 0 auto;\n"
                        + " display: block;\n"
                        + "border: 1px solid #ccc;\n"
                        + "box-sizing: border-box;\n"
                        + "}\n"
                        + "button {\n"
                        + " background-color: #4CAF50;\n"
                        + "color: white;\n"
                        + " padding: 14px 20px;\n"
                        + " margin: 0 auto;\n"
                        + " border: none;\n"
                        + " cursor: pointer;\n"
                        + " width: auto;\n"
                        + " display: block;\n"
                        + " }\n"
                        + " .cancelbtn {\n"
                        + "   width: auto;\n"
                        + "  padding: 10px 18px;\n"
                        + " background-color: #f44336;\n"
                        + " display: inline-block;\n"
                        + " }\n"
                        + " .imgcontainer {\n"
                        + "   text-align: center;\n"
                        + "   margin: 24px 0 12px 0;\n"
                        + " }\n"
                        + " img.logo {\n"
                        + "  width: 10%;\n"
                        + "   border-radius: 50%;\n"
                        + " }\n"
                        + "   .container {\n"
                        + "       padding: 16px;\n"
                        + "       width: 100%;\n"
                        + "       text-align: center;\n"
                        + "       margin: 0 auto;\n"
                        + "       display: inline-block;\n"
                        + "   }\n"
                        + "   alama{\n"
                        + "       margin-bottom: 100px;\n"
                        + "   }\n"
                        + "   h2{\n"
                        + "       text-align: center;\n"
                        + "       color: blue;\n"
                        + "   }\n"
                        + "   /* Change styles for span and cancel button on extra small screens */\n"
                        + "   @media screen and (max-width: 300px) {\n"
                        + "       span.psw {\n"
                        + "           display: inline-block;\n"
                        + "           float: none;\n"
                        + "       }\n"
                        + "       .cancelbtn {\n"
                        + "           width: 100%;\n"
                        + "       }\n"
                        + "   }\n"
                        + "   inputbutton{\n"
                        + "        width: 100%;\n"
                        + "       height: 50%;\n"
                        + "       background-color: #f44336;\n"
                        + "       display: inline-block;\n"
                        + "       border: 10px;\n"
                        + "   }\n"
                        + "   a{\n"
                        + "      text-decoration: none;\n"
                        + "  }\n"
                        + " </style>\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + "<div class=\"imgcontainer\">\n"
                        + "                <img src=\"cleanenergy.jpg\" alt=\"Logo\" class=\"logo\">\n"
                        + "            </div>"
                        + "        <hr />\n"
                        + "<div><h2>QR Code Readings</h2></div>");
                if(session.getAttribute("imageFilePath") == null){
            //response.sendRedirect("uploadFile");
            out.println("<br><a href=\"index.html\">No File Uploaded, return</a>");
        }
                 

                //Now we can re-open the image file to check the QR code
                File file = new File(imageFilePath);
                BufferedImage image;
                BinaryBitmap bitmap;
                Result result;
                image = ImageIO.read(file);
                if (null == image) {
                    out.println("<div class=\"container\">\n"
                            + "               <h3> "
                            + "No bitmap detected. Probably no QR code included!"
                            + "<div class=\"container\">\n"
                            + "               <h3> ");
                    //out.println("<br><a href=\"showUploads\">ShowUploaded</a>");
                } else {
                    int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
                    RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
                    bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    MultiFormatReader reader = new MultiFormatReader();
                    result = reader.decode(bitmap);
                    out.println("<div class=\"container\">\n"
                            + "                <h3> "
                            + result.getText()
                            + " </h3>\n"
                            + "            </div>");
                }
               

            } catch (IOException | NotFoundException ex) {
                System.err.println("Error in the Servlet QrReader: " + ex.getMessage());
                out.println("<div class=\"container\">\n"
                        + "               <h3> "
                        + "There was a problem in analyzing the image you uploaded."
                        + "<div class=\"container\">\n"
                        + "               <h3> ");
                //out.println("<br><a href=\"showUploads\">ShowUploaded</a>");
            }

            out.println("                <div class=\"container\" style=\"background-color:#f1f1f1\">\n"
                    + "                    <button type=\"button\" class=\"cancelbtn\"><a href=\"showUploads\">Back to Uploaded Files</a></button><br/><br/>\n"
                    + "            </div>"
                    + "    </body>\n"
                    + "</html>");

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
