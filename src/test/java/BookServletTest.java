import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import servlet.BookServlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class BookServletTest extends Mockito {

    @Test
    public void doPostTest() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("book")).thenReturn("2");
        when(response.getOutputStream()).thenReturn(mock(ServletOutputStream.class));

        Method doGet = BookServlet.class.getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class);
        doGet.setAccessible(true);
        doGet.invoke(new BookServlet(), request, response);

        verify(request, atLeast(1)).getParameter("book");
        assertNotNull(response);
    }
}
