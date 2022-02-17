package org.sample;

import org.apache.commons.beanutils.BeanUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.reflect.InvocationTargetException;

public class MyBenchmark {

    private static UserInfo userInfo;

    static {
        userInfo = new UserInfo();
        userInfo.setId("1");
        userInfo.setNickName("2");
        userInfo.setAvatarUrl("3");
    }

    @Benchmark
    public void directSet() {
        UserInfoDto dto = new UserInfoDto();
        dto.setId(userInfo.getId());
        dto.setNickName(userInfo.getNickName());
        dto.setAvatarUrl(userInfo.getAvatarUrl());
    }

    @Benchmark
    public void beanCopy() throws InvocationTargetException, IllegalAccessException {
        UserInfoDto dto = new UserInfoDto();
        BeanUtils.copyProperties(dto, userInfo);
    }

    @Benchmark
    public void mapStruct() {
        UserInfoDto dto = UserInfoMapper.INSTANCE.toDto(userInfo);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
