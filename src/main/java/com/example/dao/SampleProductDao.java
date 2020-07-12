package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.model.SampleProductModel;

public class SampleProductDao {

	public List<SampleProductModel> findByAll() {

		List<SampleProductModel> result = new ArrayList<>();
		result.add(new SampleProductModel("のり弁", 40000));
		result.add(new SampleProductModel("幕ノ内弁当", 500));
		result.add(new SampleProductModel("のり弁", 400));
		result.add(new SampleProductModel("幕ノ内弁当", 500));
		result.add(new SampleProductModel("のり弁", 400));
		result.add(new SampleProductModel("幕ノ内弁当", 500));
		result.add(new SampleProductModel("のり弁", 400));
		result.add(new SampleProductModel("幕ノ内弁当", 500));
		result.add(new SampleProductModel("のり弁", 400));
		result.add(new SampleProductModel("幕ノ内弁当", 500));
		result.add(new SampleProductModel("のり弁", 400));
		return result;
	}
}
