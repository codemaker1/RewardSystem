package com.charter.demo.rewards.controller;


import com.charter.demo.rewards.model.RewardPoints;
import com.charter.demo.rewards.model.Transaction;
import com.charter.demo.rewards.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RewardsController {
    @Autowired
    RewardsService rewardsService;

    @GetMapping("/test")
    public String testEndPoint() {
        return "Welcome to Reward Project";
    }

    @PostMapping("/calculate/points")
    public int calculateRewards(@RequestBody List<Transaction> transactions) {
        return rewardsService.calculateRewards(transactions);
    }

    @PostMapping("/add/transaction")
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return rewardsService.saveTransaction(transaction);
    }

    @GetMapping("/get/transactions/{userId}")
    public List<Transaction> getTransactionByUserId(@PathVariable int userId) {
        return rewardsService.getTransactionbyUserId(userId);
    }

    @GetMapping("/get/points")
    public List<RewardPoints> getRewardPoints(){
        return rewardsService.getRewardPointsForAll();
    }

    @GetMapping("/get/points/{userId}")
    public List<RewardPoints> getRewardPointsForUser(@PathVariable int userId){
        return rewardsService.getRewardPointsForUser(userId);
    }


}
