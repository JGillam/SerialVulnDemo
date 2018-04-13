<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<html><head></head><body>
Welcome to the Java (JSP) Deserialization demo!
<br>
<br>Looking for the "serialdemo" cookie...
<%
    Cookie[] cookies = request.getCookies();

    boolean foundSerialDemoCookie = false;
    if( cookies != null ) {
        for (int i = 0; i < cookies.length; i++) {
            if ("serialdemo".equals(cookies[i].getName())) {
              %><br><br>Cookie found!<%
              foundSerialDemoCookie = true;
              String encodedTicket = cookies[i].getValue();
              byte[] data = Base64.getDecoder().decode(encodedTicket);
              ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
              Object o  = ois.readObject();
              ois.close();
              out.print("<br>Object: "+o);
              out.print("<br>Class: "+o.getClass().getName());
              Map ticket = (Map)o;
              %><br>
              <br>Timestamp: <%=ticket.get("timestamp")%>
              <br>Username: <%=ticket.get("username")%>
              <br>Role: <%=ticket.get("role")%>
              <%
            }
         }
     }

     if (!foundSerialDemoCookie) {
        %><br>Cookie not found... I will create a new one! Refresh the browser to see the result.<%
        Map ticket = new HashMap();
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
        response.addCookie(newcookie);
        %><br><br>Cookie generated: <%=encodedTicket %><br><%
     }
      %>
</body>
</html>