package com.example.Application.service;

import org.redisson.api.RScript;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class RedisSetService {

    private final RedissonClient redissonClient;
    private static final int SIZE = 50;

    public RedisSetService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public RSet<Long> getSet(String setName) {
        return redissonClient.getSet(setName);
    }

    public boolean addElement(String setName, Long studentId) {
        RSet<Long> set = getSet(setName);

        if (set.size() >= SIZE) {
            return false;
        }

        return set.add(studentId);
    }

    public boolean addElementWithLimit(String setName, Long studentId) {
        String script =
                "local currentSize = redis.call('SCARD', KEYS[1]) " +
                        "local maxSize = tonumber(ARGV[1]) " +
                        "if redis.call('SISMEMBER', KEYS[1], ARGV[2]) == 1 then " +
                        "    return 0 " +
                        "end " +
                        "if currentSize >= maxSize then " +
                        "    return 0 " +
                        "end " +
                        "redis.call('SADD', KEYS[1], ARGV[2]) " +
                        "return 1 ";


        Long result = redissonClient.getScript(StringCodec.INSTANCE)
                .eval(RScript.Mode.READ_WRITE, script, RScript.ReturnType.INTEGER,
                        Collections.singletonList(setName), SIZE, studentId);

        return result == 1L;
    }

    public boolean removeElement(String setName, Long studentId) {
        RSet<Long> set = getSet(setName);
        return set.remove(studentId);
    }


    public Set<Long> getAllElements(String setName) {
        RSet<Long> set = getSet(setName);
        return set.readAll();
    }

}
