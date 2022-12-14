package vn.com.viettel.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.com.viettel.core.log.AppLog;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Set;


public class NetworkUtils {
    private static Logger logger = LoggerFactory.getLogger(NetworkUtils.class);

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    /**
     * lay dia chi server
     *
     * @return
     */
    public static String getIpAddressAndPort_q() {
        String strIpServer = "";
        try {
            MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();

            Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
                    Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));

            strIpServer = InetAddress.getLocalHost().getHostAddress();
            if (objectNames != null && objectNames.size() > 0) {
                String port = objectNames.iterator().next().getKeyProperty("port");
                strIpServer += ":" + port;
            }
        } catch (MalformedObjectNameException | UnknownHostException ex) {
            AppLog.error(ex);
        }
        return strIpServer;
    }

    /**
     * fetchClientIpAddr
     *
     * @param request
     * @return
     */
    public static String fetchClientIpAddr(HttpServletRequest request) {
        String ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        ip = ip.replace(",", "-");
        return ip;
    }

}
