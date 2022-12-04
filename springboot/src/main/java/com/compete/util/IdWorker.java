package com.compete.util;

public class IdWorker {
    /**
     * 这个就是代表了机器id
     */
    private long workerId;
    // 这个就是代表了机房id
    private long datacenterId;
    // 这个就是代表了一毫秒内生成的多个id的最新序号
    private long sequence = 0L;
    // 注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
    private long twepoch = 1581647829999L;


    // 机器占的位数
    private long workerIdBits = 5L;
    // 机房占的位数
    private long datacenterIdBits = 5L;
    // 表示的序号，就是某个机房某台机器上这一毫秒内同时生成的id的序号位数
    private long sequenceBits = 12L;

    // 这个是二进制运算，就是5 bit最多只能有31个数字，也就是说机器id最多只能是32以内
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);

    // 这个是一个意思，就是5 bit最多只能有31个数字，机房id最多只能是32以内
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    // 这个是二进制运算，就是12 bit随机数字随机数 只能在这个数字只能4095以内
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    // 机器id移位
    private long workerIdShift = sequenceBits;
    // 机房id移位=
    private long datacenterIdShift = sequenceBits + workerIdBits;
    // 时间位移
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 最近一次生成id的时间戳，单位是毫秒
     */
    private long lastTimestamp = -1L;

    /**
     * @param workerId     机器id
     * @param datacenterId 业务id
     * @param sequence     一毫秒内生成的多个id的最新序号
     */
    public IdWorker(long workerId, long datacenterId) {

        // sanity check for workerId
        // 要求就是你传递进来的机器id不能超过32，不能小于0
        if (workerId > maxWorkerId || workerId < 0) {

            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        // 要求就是你传递进来的机房id不能超过32，不能小于0
        if (datacenterId > maxDatacenterId || datacenterId < 0) {

            throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }

        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public long getWorkerId() {
        return workerId;
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    // 这个是核心方法，通过调用nextId()方法，让当前这台机器上的snowflake算法程序生成一个全局唯一的id
    public synchronized long nextId() {

        // 这儿就是获取当前时间戳，单位是毫秒
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            System.err.printf("clock is moving backwards. Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(String.format(
                    "Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 下面是说假设在同一个毫秒内，又发送了一个请求生成一个id
        // 这个时候就得把seqence序号给递增1，最多就是4096
        if (lastTimestamp == timestamp) {

            // 这个意思是说一个毫秒内最多只能有4096个数字，无论你传递多少进来，
            // 这个位运算保证始终就是在4096这个范围内，避免你自己传递个sequence超过了4096这个范围

            System.out.println("sequence-1:" + sequence);

            sequence = (sequence + 1) & sequenceMask;

            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
            System.out.println("sequence:" + sequence);

        } else {
            sequence = 0;
        }

        // 这儿记录一下最近一次生成id的时间戳，单位是毫秒
        lastTimestamp = timestamp;

        // 这儿就是最核心的二进制位运算操作，生成一个64bit的id
        // 先将当前时间戳左移，放到41 bit那儿；将机房id左移放到5 bit那儿；将机器id左移放到5 bit那儿；将序号放最后12 bit
        // 最后拼接起来成一个64 bit的二进制数字，转换成10进制就是个long型
        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {

        long timestamp = timeGen();

        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        IdWorker idWorker = new IdWorker(1,1);
        System.out.println("idWorker:"+idWorker.nextId());
    }
}