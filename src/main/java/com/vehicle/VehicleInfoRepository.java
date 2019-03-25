package com.vehicle;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
//@Repository
public interface VehicleInfoRepository extends ElasticsearchRepository<VehicleInfo, String> {
	
}
