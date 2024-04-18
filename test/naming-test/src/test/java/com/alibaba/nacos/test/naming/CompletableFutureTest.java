package com.alibaba.nacos.test.naming;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTest {
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "cf1";
        });
        
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "cf2";
        });
        
        CompletableFuture<String> cf3 = cf1.thenApply(result1 -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("cf3:" + result1);
            return "cf3";
        });
        
        CompletableFuture<String> cf5 = cf2.thenApply(result1 -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("cf5:" + result1);
            return "cf5";
        });
        
        CompletableFuture<String> cf4 = cf1.thenCombine(cf2, (result1, result2) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("cf4,result1:" + result1 + ",result2:" + result2);
            return "cf4";
        });
        CompletableFuture<Void> cf6 = CompletableFuture.allOf(cf3, cf4, cf5);
        
        CompletableFuture<String> result6 = cf6.thenApply(v -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String result3 = cf3.join();
            String result4 = cf4.join();
            String result5 = cf5.join();
            System.out.println("cf6:result3:" + result3 + ",result4:" + result4 + ",result5:" + result5);
            return "cf6";
        });
    
        System.out.println(result6.get());
    }
    
}
