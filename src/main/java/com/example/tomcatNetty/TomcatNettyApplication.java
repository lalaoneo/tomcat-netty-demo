package com.example.tomcatNetty;


import com.example.tomcatNetty.netty.tcp.HelloWordNetty;
import com.example.tomcatNetty.netty.udp.UdpNettyDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TomcatNettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TomcatNettyApplication.class,args);

        /**
         * 在web服务开启TCP及UDP
         *
         * 可以在不同的端口上进行通信
         *
         */

        new HelloWordNetty().run();

        new UdpNettyDemo().run();

        System.out.println("application started");
    }
}
