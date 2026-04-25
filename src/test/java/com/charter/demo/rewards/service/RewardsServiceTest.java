package com.charter.demo.rewards.service;

import com.charter.demo.rewards.model.Transaction;
import com.charter.demo.rewards.repo.TransactionRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardsServiceTest {

    @Mock
    private TransactionRepo transactionRepo;

    @InjectMocks
    private RewardsService rewardsService;

    //Test reward calculation logic
    @Test
    void testCalculateRewards() {
        Transaction t1 = new Transaction();
        t1.setAmount(120);  // 2*(20) + 50 = 90

        Transaction t2 = new Transaction();
        t2.setAmount(70);   // 20

        List<Transaction> list = Arrays.asList(t1, t2);

        int result = rewardsService.calculateRewards(list);

        assertEquals(110, result);
    }

    // Test getRewardPointsForAll
    @Test
    void testGetRewardPointsForAll() {

        Transaction t1 = buildTxn(1, 120, LocalDateTime.now().minusDays(10));
        Transaction t2 = buildTxn(1, 70, LocalDateTime.now().minusDays(20));
        Transaction t3 = buildTxn(2, 200, LocalDateTime.now().minusDays(5));

        when(transactionRepo.findByDateAfter(org.mockito.ArgumentMatchers.any()))
                .thenReturn(Arrays.asList(t1, t2, t3));

        var result = rewardsService.getRewardPointsForAll();

        assertEquals(2, result.size()); // 2 users
    }

    //  Test getRewardPointsForUser
    @Test
    void testGetRewardPointsForUser() {

        Transaction t1 = buildTxn(1, 120, LocalDateTime.now().minusDays(10));
        Transaction t2 = buildTxn(1, 70, LocalDateTime.now().minusDays(20));

        when(transactionRepo.findByUserIdAndDateAfter(
                org.mockito.ArgumentMatchers.eq(1),
                org.mockito.ArgumentMatchers.any()))
                .thenReturn(Arrays.asList(t1, t2));

        var result = rewardsService.getRewardPointsForUser(1);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getUserId());
        assertTrue(result.get(0).getTotal() > 0);
    }

    // Test grouping by month
    @Test
    void testMonthlyGrouping() {

        Transaction t1 = buildTxn(1, 120, LocalDateTime.of(2026, 4, 10, 10, 0));
        Transaction t2 = buildTxn(1, 70, LocalDateTime.of(2026, 4, 15, 10, 0));
        Transaction t3 = buildTxn(1, 200, LocalDateTime.of(2026, 3, 10, 10, 0));

        List<Transaction> list = Arrays.asList(t1, t2, t3);

        var result = rewardsService.getRewardPoints(list);

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getMonthlyPoints().size()); // 2 months
    }

    // helper method
    private Transaction buildTxn(int userId, int amount, LocalDateTime date) {
        Transaction t = new Transaction();
        t.setUserId(userId);
        t.setAmount(amount);
        t.setDate(date);
        return t;
    }
}