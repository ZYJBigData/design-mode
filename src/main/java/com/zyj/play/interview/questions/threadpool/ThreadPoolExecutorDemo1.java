package com.zyj.play.interview.questions.threadpool;

import java.util.concurrent.Executors;

/**
 * @author zhangyingjie
 * 1.线程池七大参数含义
 * int corePoolSize, ：线程池中的常驻核心线程数
 * int maximumPoolSize,：线程池中能够容纳同时执行的最大线程池，此值必须大于等于1
 * long keepAliveTime,：多余的空闲线程的存活时间。当前线程池数量超过corePoolSize时，多余空闲线程会被销毁 直到只剩下corePoolSize个线程为止。
 * TimeUnit unit,：keepAliveTime的单位
 * BlockingQueue<Runnable> workQueue,：任务队列，被提交但尚未被执行的任务。
 * ThreadFactory threadFactory,：表示生成线程池中工作线程的线程工厂，用于创建线程一般用默认的即可。
 * RejectedExecutionHandler handler，：拒绝策略，表示队列满了并且工作线程大于等于线程池的最大线程数。
 *
 * 银行排队办理业务的例子：
 *   1.当核心线程被沾满了之后，会在队列里排队。
 *   2.当队列也满了之后，线程池开始扩容，新增线程处理队列中的任务。
 *   3.当大于核心线程空闲了设定的时间后，会被收回最后剩下核心的线程数。
 * 拒绝策略是什么？
 *   1.等待队列满了，再也塞不下任务。
 *   2.线程池中max线程也达到了最大值，无法为新任务服务。
 *   3.满足上面两个条件就会启动重启策略。
 * 拒绝策略总共有四种:
 *   1.AbortPolicy:直接抛出RejectExcutionException异常阻止系统正常运行
 *   2.CellerRunsPolicy:"调用者异常"这种方式既不抛弃任务，也不会抛出异常，而是将某些任务回退到调用者,从而降低新任务的流量
 *   3.DiscardOldesPolicy: 抛弃任务中等待时间最久的任务，然后把当前的任务加入队列中尝试再次提交当前任务
 *   4.DiscardPolicy：直接丢弃任务，不予任何处理也不抛出异常，如果允许任务丢失这是最好的一种方案。
 *
 * 如何设置线程池的个数：
 *   1.要明确自己的环境的cpu核数：Runtime.getRuntime.availableProcess();
 *   2.确定任务是cpu密集型还是IO密集型
 *   3.如果是CPU密集型其计算应该是：
 *     1.CPU密集型其实就是任务需要大量的计算，而没有阻塞，CPU一直全速运行
 *     2.CPU密集型只有真正的多核CPU才能得到加速（通过多线程）
 *     3.CPU密集型任务配置成尽量少的线程数，一般的计算公式是：CPU核数+1个线程的线程池。
 *   4.如果是IO密集型一下1，2两种计算方式：
 *     1.由于IO密集型的任务并不是一直都在执行的任务，应配置尽可能多的线程，如CPU核数*2
 *       Nthreads = Ncpu x Ucpu x (1 + W/C)。Ncpu cpu的个数，Ucpu cpu的利用率，W/C=等待时间/计算时间
 *       IO密集型是Ucpu接近于0，计算时间接近0，所有时间几乎都是IO消耗，那么W/C趋近于1 所以结果为2N。
 *     2.参考公式为：CPU核数/1-阻塞系数 （阻塞系数一般是0，8~0.9）阻塞系数的计算公式是：阻塞系数=阻塞时间/(阻塞时间+计算时间)
 *   5.设置线程数除了考虑任务的CPU操作系数，IO操作耗时之外，还需要考虑内存资源，硬盘资源，网络带宽等等。
 *
 */
public class ThreadPoolExecutorDemo1 {
    public static void main(String[] args) {
        Executors.newSingleThreadExecutor();
    }
}
