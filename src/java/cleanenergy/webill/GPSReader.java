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
import static java.lang.System.out;
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
public class GPSReader extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {

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
                    + "<div><h2>GPS Information</h2></div>");

            if ((imageFilePath != null) && !imageFilePath.isEmpty()) {
                //Reopen the file to check GPS information.
                File imageFile = new File(imageFilePath);
                //The GPs data is saved as MetaData. We need to ceck if it exists.
                if ((Sanselan.getMetadata(imageFile) != null)
                        || (Sanselan.getMetadata(imageFile) instanceof IImageMetadata)) {
                    //Check if we can convert it to JpegImageMetadata
                    final IImageMetadata metadata = (IImageMetadata) Sanselan.getMetadata(imageFile);
                    //Check if we can convert it to JpegImageMetadata
                    if (metadata instanceof JpegImageMetadata) {
                        final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                        //Inside the MetaData, the GPS inforrmation is saved as EXIF data. Check if it exists.
                        if (jpegMetadata.getExif() != null) {
                            final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
                            if (null != exifMetadata.getGPS()) {
                                final TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
                                if (null != gpsInfo) {
                                    //Finally, we get to the GPS data.
                                    final String gpsDescription = gpsInfo.toString();
                                    final double longitude = gpsInfo.getLongitudeAsDegreesEast();
                                    final double latitude = gpsInfo.getLatitudeAsDegreesNorth();

                                    out.println("<div class=\"container\">\n"
                                            + "    <h3> "
                                            + "GPS Description: "
                                            + gpsDescription
                                            + " </h3>\n"
                                            + "            </div>");
                                    out.println("    "
                                            + "GPS Longitude (Degrees East): " + longitude);
                                    out.println("    "
                                            + "GPS Latitude (Degrees North): " + latitude);
                                }
                            } else {
                                out.println("<div class=\"container\">\n"
                                        + "    <h3> "
                                        + "No GPS data!"
                                        + " </h3>\n"
                                        + "            </div>");
                            }
                        } else {
                            out.println("<div class=\"container\">\n"
                                    + "    <h3> \n"
                                    + "No EXIF data!"
                                    + " </h3>\n"
                                    + "            </div>");
                        }

                    } else {
                        out.println("<div class=\"container\">\n"
                                + "    <h3> \n"
                                + "The format of the image file you uploaded "
                                + "does not contain metadata!"
                                + " </h3>\n"
                                + "            </div>");
                    }
                } else {
                    out.println("<div class=\"container\">\n"
                            + "    <h3> \n"
                            + "No META data!"
                            + " </h3>\n"
                            + "            </div>\n");
                }
                //out.println("<br><a href=\"showUploads\">ShowUploaded</a>");

            } else {
                response.sendRedirect("fileUpload.html");
            }

            out.println("                <div class=\"container\" style=\"background-color:#f1f1f1\">\n"
                    + "                    <button type=\"button\" class=\"cancelbtn\"><a href=\"showUploads\">Back to Uploaded Files</a></button><br/><br/>\n"
                    + "            </div>\n"
                    + "    </body>\n"
                    + "</html>");

        } catch (FileNotFoundException | ImageReadException ex) {
            System.err.println("Error in the Servlet GPSReader: " + ex.getMessage());

            response.sendRedirect("fileUpload.html");

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
