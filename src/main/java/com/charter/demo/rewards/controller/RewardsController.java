package com.charter.demo.rewards.controller;


import com.charter.demo.rewards.model.RewardPoints;
import com.charter.demo.rewards.model.Transaction;
import com.charter.demo.rewards.service.RewardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Rewards API", description = "Operations for reward system")
@RestController
@RequestMapping("/api/v1")
public class RewardsController {
    @Autowired
    RewardsService rewardsService;

    @Operation(summary = "Test Api")
    @GetMapping("/test")
    public String testEndPoint() {
        return "Welcome to Reward Project";
    }

    @Operation(summary = "Calculate the rewards points based on the transactions done")
    @PostMapping("/calculate/points")
    public int calculateRewards(@RequestBody List<Transaction> transactions) {
        return rewardsService.calculateRewards(transactions);
    }

    @Operation(summary = "Add the transaction done by the user")
    @PostMapping("/add/transaction")
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return rewardsService.saveTransaction(transaction);
    }

    @Operation(summary = "Get all transactions done  by user")
    @GetMapping("/get/transactions/{userId}")
    public List<Transaction> getTransactionByUserId(@PathVariable int userId) {
        return rewardsService.getTransactionbyUserId(userId);
    }

    @Operation(summary = "Get reward points for all users for last 3 months")
    @GetMapping("/get/points")
    public List<RewardPoints> getRewardPoints() {
        return rewardsService.getRewardPointsForAll();
    }

    @Operation(summary = "Get reward points for particular users for last 3 months")
    @GetMapping("/get/points/{userId}")
    public List<RewardPoints> getRewardPointsForUser(@PathVariable int userId) {
        return rewardsService.getRewardPointsForUser(userId);
    }


}
