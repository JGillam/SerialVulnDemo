package demo.serialization;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DemoServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        ServletOutputStream out = resp.getOutputStream();

        try {
            boolean foundSerialDemoCookie = false;
            if( cookies != null ) {
                for (Cookie cooky : cookies) {
                    if ("serialdemo".equals(cooky.getName())) {
                        out.println("<br><br>Cookie found!");
                        foundSerialDemoCookie = true;
                        String encodedTicket = cooky.getValue();
                        byte[] data = Base64.getDecoder().decode(encodedTicket);
                        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
                        Object o = ois.readObject();
                        ois.close();
                        out.println("<br>Object: " + o);
                        out.println("<br>Class: " + o.getClass().getName());
                        //Thread.currentThread().sleep(2000);
                        // Map ticket = (Map)o;
                  //<br > Timestamp: <%-- = ticket.get("timestamp")-- % >
                  //<br > Username: <%-- = ticket.get("username")-- % >
                  //<br > Role: <%-- = ticket.get("role")-- % >
                    }
                }
            }

            if (!foundSerialDemoCookie) {
                out.println("<br>Cookie not found... I will create a new one! Refresh the browser to see the result.");
                Map<String, Serializable> ticket = new HashMap<>();
                ticket.put("timestamp", System.currentTimeMillis());
                ticket.put("username", "guestuser");
                ticket.put("role", "guest");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream( baos );
                oos.writeObject( ticket );
                oos.close();
                String encodedTicket = Base64.getEncoder().encodeToString(baos.toByteArray());
                Cookie newcookie = new Cookie("serialdemo", encodedTicket);
                newcookie.setMaxAge(300);  // 5 minute cookie
                resp.addCookie(newcookie);
                out.println("<br><br>Cookie generated: "+encodedTicket);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
