package com.charter.demo.rewards.service;


import com.charter.demo.rewards.model.MonthlyPoints;
import com.charter.demo.rewards.model.RewardPoints;
import com.charter.demo.rewards.model.Transaction;
import com.charter.demo.rewards.repo.TransactionRepo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RewardsService {

    @Autowired
    TransactionRepo transactionRepo;

    public int calculateRewards(List<Transaction> transactions) {
        int points = 0;
        if (CollectionUtils.isNotEmpty(transactions)) {
            for (Transaction transaction : transactions) {
                int amount = transaction.getAmount();
                if (amount > 100) {
                    points += 2 * (amount - 100) + 50;
                } else if (amount > 50 && amount <= 100) {
                    points += amount - 50;
                }
            }
        }

        return points;
    }

    public List<Transaction> getTransactionbyUserId(int userId) {
        return transactionRepo.findByUserId(userId);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepo.save(transaction);
    }

    public List<RewardPoints> getRewardPoints() {
        List<RewardPoints> res = new ArrayList<>();
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);

        List<Transaction> lastThreeMonthTrans = transactionRepo.findByDateAfter(threeMonthsAgo);

        Map<Integer, List<Transaction>> userTransaction = lastThreeMonthTrans.stream().
                collect(Collectors.groupingBy(Transaction::getUserId));

        userTransaction.entrySet().stream().forEach(
                entry -> {
                    Map<String, List<Transaction>> groupByMonth = entry.getValue().stream().collect(Collectors.groupingBy(
                            t -> t.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM"))));

                    List<MonthlyPoints> monthlyPointsList = groupByMonth.entrySet().stream().map(monthlyEntry -> {
                        int points = calculateRewards(monthlyEntry.getValue());

                        MonthlyPoints mp = new MonthlyPoints();
                        mp.setMonth(monthlyEntry.getKey());
                        mp.setPoints(points);
                        return mp;
                    }).collect(Collectors.toList());
                    RewardPoints rewardPoints = new RewardPoints();
                    rewardPoints.setUserId(entry.getKey());
                    rewardPoints.setMonthlyPoints(monthlyPointsList);
                    rewardPoints.setTotal(monthlyPointsList.stream().mapToInt(mp -> mp.getPoints()).sum());
                    res.add(rewardPoints);

                }
        );
        return res;
    }
}
