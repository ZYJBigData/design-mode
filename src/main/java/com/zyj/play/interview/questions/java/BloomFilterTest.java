package com.zyj.play.interview.questions.java;


import org.apache.flink.shaded.guava18.com.google.common.hash.BloomFilter;
import org.apache.flink.shaded.guava18.com.google.common.hash.Funnels;

import java.nio.charset.StandardCharsets;

public class BloomFilterTest {
    public static void main(String[] args) {
        BloomFilter<CharSequence> charSequenceBloomFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), 20, 0.03);
        charSequenceBloomFilter.put("a");
        charSequenceBloomFilter.put("c");
        BloomFilter<CharSequence> charSequenceBloomFilter1 = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), 20, 0.03);
        charSequenceBloomFilter1.put("b");
        charSequenceBloomFilter.putAll(charSequenceBloomFilter1);
        charSequenceBloomFilter.put("a");
        System.out.println(charSequenceBloomFilter.mightContain("b"));
        System.out.println(charSequenceBloomFilter.mightContain("d"));
        System.out.println(charSequenceBloomFilter.mightContain("a"));
    }
}
