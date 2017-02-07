/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanenergy.webill;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.sanselan.ImageReadException;

import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;

/**
 *
 * @author itsme
 */
@MultipartConfig
public class uploadFile extends HttpServlet {

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
        
        //Checking Sessions if the userName has values, means person have loged in
                if(session.getAttribute("userName") == null){
            response.sendRedirect("index.html");
        } else {
        try (PrintWriter out = response.getWriter()) {
            //Get the file from the request. It is a multipart file.
            final Part filePart = request.getPart("fileToUpload");
            OutputStream outFile;
            InputStream fileContent;
            //Get the file name
            final String partHeader = filePart.getHeader("content-disposition");
            String fileName = "";
            for (String content : partHeader.split(";"))  {
                if (content.trim().startsWith("filename")) {
                    fileName = content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
                    break;
                }
            }
//We will save the file in the temporary folder of the system.
            //We will add _ at the beginning of the file name.
            try {
                File fileToUpload = new File(System.getProperty("java.io.tmpdir") + "/_"
                                + fileName);
                outFile = new FileOutputStream(fileToUpload);
                fileContent = filePart.getInputStream();
                int read;
                final byte[] bytes = new byte[1024];
                while ((read = fileContent.read(bytes)) != -1) {
                    outFile.write(bytes, 0, read);
                }
                fileContent.close();
                outFile.close();
                session.setAttribute("imageFilePath", fileToUpload.getCanonicalPath());
                        if(session.getAttribute("imageFilePath") == null){
        response.sendRedirect("index.html");
                }else{
                response.sendRedirect("showUploads");}
            } catch (FileNotFoundException ex) {
                System.err.println("Error in the Servlet uploadFile: " + ex.getMessage());
            }
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