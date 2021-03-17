package org.geektimes.projects.user.management;


import org.geektimes.projects.user.domain.User;

import javax.annotation.PostConstruct;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class UserMBeanDemo {

    @PostConstruct
    public void init() throws Exception{
        // 获取平台 MBean Server
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        // 为 UserMXBean 定义 ObjectName
        ObjectName objectName = new ObjectName("org.geektimes.projects.user.management:type=User");
        // 创建 UserMBean 实例
        User user = new User();
        mBeanServer.registerMBean(createUserMBean(user), objectName);
    }

    public static void main(String[] args) throws Exception {

    }

    private static Object createUserMBean(User user) throws Exception {
        return new UserManager(user);
    }
}
