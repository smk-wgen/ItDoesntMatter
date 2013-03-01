package com.example.itdoesntmatter.services;

import java.util.List;

import android.content.Intent;

import com.example.itdoesntmatter.model.Trend;

public interface TrendService {
	List<Trend> getAllTrends();
	List<Trend> getNonPromotedTrends();
	
}
