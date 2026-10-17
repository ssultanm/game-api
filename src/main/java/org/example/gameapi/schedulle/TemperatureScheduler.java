//package org.example.gameapi.schedulle;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.Random;
//
//@Service
//public class TemperatureScheduler {
//    int temperature;
//    double sum=0;
//    int count=0;
//    @Scheduled(fixedRate = 5000)
//    public void generateTemperature()
//    {
//        Random rand = new Random();
//        temperature = rand.nextInt(51)-10;
//        sum=sum+temperature;
//        count++;
//        System.out.println("temperature:"+temperature);
//    }
//    @Scheduled(fixedDelay = 5000)
//    public void printAvarageTemperature()
//    {
//        System.out.println("avarage temperature:"+sum/count);
//    }
//}
