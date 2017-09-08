package com.linCu.servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "ImageServlet", urlPatterns = { "/imageservlet" })
public class ImageServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "image/jpeg; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
        String imageId = request.getParameter("docId");
        OutputStream os = response.getOutputStream();
        Connection conn = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/LinCuDBDS");
            conn = ds.getConnection();
            PreparedStatement ps;
            ps = conn.prepareStatement("SELECT DOCUMENT, DOCUMENT_NAME, PATH FROM LINCU_MEMBER_CARD_DOCS WHERE DOC_ID = ?");
            ps.setInt(1, new Integer(imageId));
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                //Blob blob = rs.getBlob("DOCUMENT");
                String fileName = rs.getString("DOCUMENT_NAME");
                String filePath = rs.getString("PATH");
                response.setContentType(ContentTypes.get(fileName));
                //if(blob != null){
                if(filePath != null){
                //BufferedInputStream bis = new BufferedInputStream(blob.getBinaryStream());
                File filed = new File(filePath);
                FileInputStream bis = new FileInputStream(filed);
                int b;
                byte[] buffer = new byte[10240];
                while((b = bis.read(buffer, 0, 10240)) != -1){
                  os.write(buffer,0,b);  
                }
                    bis.close();
                }
               
                os.close();
            }
        } catch (NamingException e) {
        } catch (SQLException e) {
        }finally{
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }
        }
    }
}
