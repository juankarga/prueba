package com.prueba.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.prueba.service.IRecommendationService;
import com.prueba.utils.ConvertUtils;

@Service
public class RecommendationServiceImpl implements IRecommendationService {

	@Value("${trm}")
	private Float trm;

	@Override
	public List<String> calculate(Map<String, Float> items, Float amountIn) {

		Float amount = ConvertUtils.convertPesosToDollar(amountIn, trm);

		int wt[] = new int[items.size()];
		int val[] = new int[items.size()];
		Map<Integer, String> equivalent = new HashMap<Integer, String>();
		int i = 0;
		for (String name : items.keySet()) {
			equivalent.put(i, name);
			wt[i] = (int) Math.floor(ConvertUtils.convertPesosToDollar(items.get(name), trm) * 100);
			val[i] = 1;
			i++;
		}

		int W = (int) Math.floor(amount * 100);
		int n = val.length;

		return solveknapSack(W, wt, val, n, equivalent);
	}

	private List<String> solveknapSack(int W, int[] wt, int[] val, int n, Map<Integer, String> equivalent) {
		List<String> result = new ArrayList<String>();
		int i, w;
		int K[][] = new int[n + 1][W + 1];
		for (i = 0; i <= n; i++) {
			for (w = 0; w <= W; w++) {
				if (i == 0 || w == 0)
					K[i][w] = 0;
				else if (wt[i - 1] <= w)
					K[i][w] = Math.max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]);
				else
					K[i][w] = K[i - 1][w];
			}
		}
		int res = K[n][W];
		w = W;
		for (i = n; i > 0 && res > 0; i--) {
			if (res == K[i - 1][w])
				continue;
			else {
				result.add(equivalent.get(i - 1));
				res = res - val[i - 1];
				w = w - wt[i - 1];
			}
		}
		return result;
	}
}
