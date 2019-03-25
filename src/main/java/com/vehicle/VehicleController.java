package com.vehicle;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@RestController
public class VehicleController {

    @Autowired
    private VehicleInfoRepository vehicleInfoRepository;
    
    @Autowired
    private VehicleListRepository vehicleListRepository;
    
    
    Random r = new Random();
    
    public String produceVehicleId() {
		StringBuilder sb = new StringBuilder();
		sb.append("京A");
		for(int i=0; i<5; i++){
			sb.append(r.nextInt(10));
		}
		log.info(sb.toString());
		return sb.toString();
	}
	
	//  //116:28E 39:54N
	public String produceLongitude(){
		StringBuilder sb = new StringBuilder();
		return sb.append("116:").append(r.nextInt(60)).append("E").toString();
	}
	
	public String produceLatitude(){
		StringBuilder sb = new StringBuilder();
		return sb.append("39:").append(r.nextInt(60)).append("N").toString();
	}

    //http://localhost:8888/saveVehicleInfo
    @GetMapping("saveVehicleInfo")
    public String saveVehicleInfo() throws Exception{
    	int i=0;
    	while(true){
    		Iterator<VehicleList> vl = vehicleListRepository.findAll().iterator();    	
        	while(vl.hasNext()){
        		String vehicleId = vl.next().getId();
        		VehicleInfo v = new VehicleInfo();
            	v.setId(vehicleId + System.currentTimeMillis());
            	v.setVehicleId(vehicleId);
            	v.setCurrentTime(System.currentTimeMillis());
            	v.setCurrentSpeed(r.nextInt(50)+100);
            	v.setLatitude(produceLatitude());
            	v.setLongitude(produceLongitude());        
                vehicleInfoRepository.save(v);
                log.info(vehicleId + " === " + v.getCurrentTime());
                Thread.sleep(200);                          
        	} 
        	i++;
        	if(i==4){
        		return "saveVehicleInfo success";
        	}
    	}
//    	String vehicleId = "京A25906";
//    	while(true){    				
//    		VehicleInfo v = new VehicleInfo();
//    		v.setId(vehicleId + System.currentTimeMillis());
//        	v.setVehicleId(vehicleId);
//        	v.setCurrentTime(System.currentTimeMillis());
//        	v.setCurrentSpeed(r.nextInt(50)+100);
//        	v.setLatitude(produceLatitude());
//        	v.setLongitude(produceLongitude());        
//            vehicleInfoRepository.save(v);
//            log.info(vehicleId + " === " + v.getCurrentTime());
//            Thread.sleep(200);      
//    	}
    	
    	//return "saveVehicleInfo success";
    }
    
  //http://localhost:8888/saveVehicleList
    @GetMapping("saveVehicleList")
    public String saveVehicleList(){  	    	
    	for(int i=0; i<100; i++){
    		VehicleList v = new VehicleList();
        	v.setId(produceVehicleId());
        	v.setIfActive(r.nextInt(2));
        	v.setIfOnline(r.nextInt(2));
        	v.setOnlineTime(System.currentTimeMillis());
        	v.setActiveTime(System.currentTimeMillis());
            vehicleListRepository.save(v);
    	}    	
        return "saveVehicleList success";
    }
    
    //http://localhost:8888/getVehicleList?pageNumber=0&pageSize=20
    @GetMapping("getVehicleList")
    public List<VehicleList> getVehicleList(Integer pageNumber, Integer pageSize){         
        Page<VehicleList> vehicleListPage = vehicleListRepository.search(
        		QueryBuilders.matchAllQuery(), new PageRequest(pageNumber, pageSize));
        //log.info("totalNum = " + vehicleListPage.getTotalElements());
        return vehicleListPage.getContent();
    }
    
    //http://localhost:8888/getVehicleInfoList?pageNumber=0&pageSize=20&vehicleId='京A25906'
    @GetMapping("getVehicleInfoList")
    public List<VehicleInfo> getVehicleInfoList(String vehicleId, Integer pageNumber, Integer pageSize){   
        Page<VehicleInfo> vehicleInfoPage = vehicleInfoRepository.search(
        		QueryBuilders.commonTermsQuery("vehicleId", vehicleId), new PageRequest(pageNumber, pageSize));       
        //log.info("totalNum = " + vehicleInfoPage.getTotalElements());
        return vehicleInfoPage.getContent();
    }
    
    @GetMapping("updateVehicleList")
    public String updateVehicleList(String vehicleId){
    	//vehicleListRepository.save(entity)
    	return "";
    }
    
    //http://localhost:8888/getVehicleTotalInfo
    @GetMapping("getVehicleTotalInfo")
    public String getVehicleTotalInfo(){
    	Map<String, Object> map = new HashMap<>();
    	long totalNum = vehicleListRepository.count();
    	long activeNum = vehicleListRepository.countByIfActive(1);
    	long onlineNum = vehicleListRepository.countByIfOnline(1);
    	map.put("totalNum", totalNum);
    	map.put("activeNum", activeNum);
    	map.put("onlineNum", onlineNum);
    	return map.toString();
    }    
}

