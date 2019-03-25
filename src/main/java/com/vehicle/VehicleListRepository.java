package com.vehicle;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface VehicleListRepository extends ElasticsearchRepository<VehicleList, String> {
	
	public long countByIfActive(Integer ifActive);
	
	public long countByIfOnline(Integer ifOnline);
	
}
