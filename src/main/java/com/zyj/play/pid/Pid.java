package com.zyj.play.pid;

import org.hyperic.sigar.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;


public class Pid {
    public static void main(String[] args) throws IOException, SigarException, URISyntaxException{
//        System.out.println("pid=={}" + getPid());
//        System.out.println("pid=={}" + getPid2());
//        System.out.println("pid=={}" + getPid3());
//        System.out.println("pid=={}" + getPid4());
    }

    public static String getPid() {
        final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println("name=={}" + jvmName);
        final int index = jvmName.indexOf('@');
        if (index < 1) {
            // part before '@' empty (index = 0) / '@' not found (index = -1)
            return null;
        }
        try {
            return Long.toString(Long.parseLong(jvmName.substring(0, index)));
        } catch (NumberFormatException e) {
            // ignore
        }
        return null;
    }

    //TODO 只适用于Linux环境
    public static String getPid2() throws IOException {
        byte[] bo = new byte[256];
        InputStream is = new FileInputStream("/proc/self/stat");
        is.read(bo);
        for (int i = 0; i < bo.length; i++) {
            if ((bo[i] < '0') || (bo[i] > '9')) {
                return new String(bo, 0, i);
            }
        }
        return "-1";
    }

    public static long getPid3() throws SigarException {
        System.out.print(System.getProperty("java.library.path"));
        Sigar sigarImpl = new Sigar();
        SigarProxy sigar = SigarProxyCache.newInstance(sigarImpl, 6000);
        Cpu cpu = sigar.getCpu();
        System.out.println(cpu);
        return sigar.getPid();
    }

    public static int getPid4() throws URISyntaxException {
//        // 获取监控主机
////        MonitoredHost local = MonitoredHost.getMonitoredHost("http://10.0.100.237:1099");
//        MonitoredHost local = MonitoredHost.getMonitoredHost("localhost");
//        // 取得所有在活动的虚拟机集合
//        Set<?> vmlist = new HashSet<Object>(local.activeVms());
//        // 遍历集合，输出PID和进程名
//        for (Object process : vmlist) {
//            MonitoredVm vm = local.getMonitoredVm(new VmIdentifier("//" + process));
//            // 获取类名
//
//            String processname = MonitoredVmUtil.mainClass(vm, true);
//            System.out.println("processname=={}" + processname);
//            if ("org.apache.hadoop.hdfs.server.namenode.NameNode".equals(processname)) {
//                return ((Integer) process).intValue();
//            }
//        }
        return -1;
    }
}
