package com.charter.demo.rewards.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RewardPoints {

    private int userId;
    private List<MonthlyPoints> monthlyPoints;

    private int total;

}
