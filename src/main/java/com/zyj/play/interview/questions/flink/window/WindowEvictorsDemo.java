package com.zyj.play.interview.questions.flink.window;

/**
 * @author zhangyingjie
 *
 * Flink provides no guarantees about the order of the elements within a window.
 * This implies that although an evictor may remove elements from the beginning of the window,
 * these are not necessarily the ones that arrive first or last.
 *
 * 详情可以查看
 * https://ci.apache.org/projects/flink/flink-docs-release-1.13/docs/dev/datastream/operators/windows/#evictors
 */
public class WindowEvictorsDemo {
    //该方法已经在WindowTriggerDemo 中应用
}
