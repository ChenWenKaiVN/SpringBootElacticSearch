//package com.example.demo;
//
//import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//import java.util.Random;
//
//import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
//import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.get.MultiGetItemResponse;
//import org.elasticsearch.action.get.MultiGetResponse;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequestBuilder;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.IndicesAdminClient;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.TransportAddress;
//import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.common.xcontent.XContentFactory;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.aggregations.Aggregation;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.BucketOrder;
//import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
//import org.elasticsearch.search.aggregations.bucket.terms.Terms;
//import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
//import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
//import org.elasticsearch.search.sort.SortOrder;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
//
//public class VehicleTest {
//	
//	private TransportClient client = null;
//	
//	Random r = new Random();
//	
//	String[] vehicleIdArr = new String[]{"京A00000","京A00001","京A00002","京A00003","京A00004"};
//	
//	//@Test
//	public String produceVehicleId() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("京A");
//		for(int i=0; i<5; i++){
//			sb.append(r.nextInt(10));
//		}
//		System.out.println(sb.toString());
//		return sb.toString();
//	}
//	
//	
//	//  //116:28E 39:54N
//	public String produceLongitude(){
//		StringBuilder sb = new StringBuilder();
//		return sb.append("116:").append(r.nextInt(60)).append("E").toString();
//	}
//	
//	public String produceLatitude(){
//		StringBuilder sb = new StringBuilder();
//		return sb.append("39:").append(r.nextInt(60)).append("N").toString();
//	}
//	
//    //在所有的测试方法之前执行
//    @Before
//    public void init() throws Exception {
//        //设置集群名称
//        Settings settings = Settings.builder().
//        		put("cluster.name", "es")       
//                //.put("client.transport.sniff", true) //自动感知的功能（可以通过当前指定的节点获取所有es节点的信息）
//        		.build();
//        //创建client
//        client = new PreBuiltTransportClient(settings).addTransportAddresses(
//                new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
//
//    }
//    
//    @Test
//    public void testCreateVehicleInfo() throws IOException, Exception {
//    	while(true){
//    		SearchRequestBuilder srb = client.prepareSearch("vehicle_list").setTypes("vehicle");
//    	    SearchResponse sr = srb.setQuery(QueryBuilders.matchAllQuery()).setSize(100).execute().actionGet(); // 查询所有
//    	    SearchHits hits = sr.getHits();
//    	    //System.out.println(hits.getTotalHits());
//    	    for(int i=0; i<hits.getTotalHits(); i++){
//    	    	//System.out.println(i);
//    	        //System.out.println(hits.getAt(i).getSourceAsString());
//    	        Map<String, Object> map = hits.getAt(i).getSourceAsMap(); 
//                //System.out.println(map.get("id"));
//                IndexResponse response = client.prepareIndex("vehicle_info", "vehicle")
//                        .setSource(
//                                jsonBuilder()
//                                        .startObject()
//                                            .field("id", map.get("id"))
//                                            .field("current_time", new Date().getTime())
//                                            .field("current_speed", r.nextInt(50)+100)
//                                            .field("longitude", produceLongitude())   //116:28E 39:54N
//                                            .field("latitude", produceLatitude())
//                                        .endObject()
//                        ).get();
//                System.out.println(map.get("id"));
//        		Thread.sleep(1000);
//    	    }    		
//    	}     
//    }
//    
//    @Test
//    public void testCreateVehicleInfoById() throws IOException, Exception {
//    	String id = "京A25906";
//    	while(true){    		
//                IndexResponse response = client.prepareIndex("vehicle_info", "vehicle")
//                        .setSource(
//                                jsonBuilder()
//                                        .startObject()
//                                            .field("id", id)
//                                            .field("current_time", new Date().getTime())
//                                            .field("current_speed", r.nextInt(50)+100)
//                                            .field("longitude", produceLongitude())   //116:28E 39:54N
//                                            .field("latitude", produceLatitude())
//                                        .endObject()
//                        ).get();
//                System.out.println(id + new Date());
//        		Thread.sleep(1000);
//    	    }    		    
//    }
//    
//    
//    @Test
//    public void testCreateVehicleList() throws IOException {
//    	for(int i=0; i<100; i++){
//    		IndexResponse response = client.prepareIndex("vehicle_list", "vehicle")
//                    .setSource(
//                            jsonBuilder()
//                                    .startObject()
//                                        .field("id", produceVehicleId())
//                                        .field("ifOnline", 0)
//                                        .field("online_time", new Date().getTime())
//                                        .field("ifActive", 0)
//                                        .field("active_time", new Date().getTime())
//                                    .endObject()
//                    ).get();
//    	}     
//    }
//    
//    
//	@Test
//	public void searchAll()throws Exception{
//	    SearchRequestBuilder srb = client.prepareSearch("vehicle_info").setTypes("vehicle");
//	    SearchResponse sr = srb.setQuery(QueryBuilders.matchAllQuery()).setSize(1000).execute().actionGet(); // 查询所有
//	    SearchHits hits = sr.getHits();
//	    System.out.println(hits.getTotalHits());
//	    for(int i=0; i<hits.getTotalHits(); i++){
//	    	//System.out.println(i);
//	        System.out.println(hits.getAt(i).getSourceAsString());
//	        //Map<String, Object> map = hits.getAt(i).getSourceAsMap(); 
//            //System.out.println(map.get("id"));
//	    }
//	}
//	
//	@Test
//	public void queryAllVehicleList() throws Exception{
//	    SearchRequestBuilder srb = client.prepareSearch("vehicle_list").setTypes("vehicle");
//	    SearchResponse sr = srb.setQuery(QueryBuilders.matchAllQuery())
//	    						.setFrom(0)
//	    						.setSize(10)
//					    		.execute().actionGet(); // 查询所有
//	    SearchHits hits = sr.getHits();
//	    System.out.println(hits.getTotalHits());
//	    for(int i=0; i<hits.getTotalHits(); i++){
//	    	//System.out.println(i);
//	        System.out.println(hits.getAt(i).getSourceAsString());
//	        Map<String, Object> map = hits.getAt(i).getSourceAsMap(); 	                  
//	    }
//	}
//	
//	@Test
//	public void queryVehicleInfoById() throws Exception {
//		String id = "京A25906";
//		SearchRequestBuilder srb = client.prepareSearch("vehicle_info").setTypes("vehicle");
//	    SearchResponse sr = srb.setQuery(QueryBuilders.commonTermsQuery("id", id))
//					    		.addSort("current_time", SortOrder.DESC)
//					    		.setSize(1000)
//					    		.setFrom(0)
//					    		.setSize(10)
//					    		.execute().actionGet(); // 查询所有
//	    SearchHits hits = sr.getHits();
//	    System.out.println(hits.getTotalHits());
//	    for(int i=0; i<hits.getTotalHits(); i++){
//	    	//System.out.println(i);
//	        System.out.println(hits.getAt(i).getSourceAsString());
//	        //Map<String, Object> map = hits.getAt(i).getSourceAsMap(); 
//            //System.out.println(map.get("id"));
//	    }
//	}
//
//    
//    @Test
//    public void testGet() throws IOException {
//        GetResponse response = client.prepareGet("vehicle_info", "vehicle", "1").get();
//        System.out.println(response.getSourceAsString());
//    }
//    
//    //查找多条
//    @Test
//    public void testMultiGet() throws IOException {
//        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
//                .add("vehicle_info", "vehicle", "2") 
//                .add("vehicle_info", "vehicle", "3") 
//                .add("vehicle_info", "vehicle", "4") 
//                .add("vehicle_info", "vehicle", "5") 
//                .get();
//
//        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
//            GetResponse response = itemResponse.getResponse();
//            if (response.isExists()) {
//                String json = response.getSourceAsString();
//                System.out.println("--->" + json);
//            }
//        }
//    }
//    
//    @Test
//    public void testSettingVehicleListMappings() throws IOException {
//    	//1:settings
//        HashMap<String, Object> settings_map = new HashMap<String, Object>(2);
//        settings_map.put("number_of_shards", 3);
//        settings_map.put("number_of_replicas", 1);
//
//        //2:mappings
//        XContentBuilder builder = XContentFactory.jsonBuilder()
//                //开始设置
//        		.startObject()//
//                    .field("dynamic", "true")
//                    //只设置字段 不传参
//                    .startObject("properties")
//                        .startObject("id")
//                            .field("type", "text")
//                            .field("index", "true")
//                            //.field("store", "yes")
//                        .endObject()
//                        .startObject("ifOnline")   // 0在线 1 不在线
//                            .field("type", "integer")
//                        .endObject()
//                        .startObject("online_time")
//                            .field("type", "long")
//                        .endObject()
//                        .startObject("ifActive")  //0激活  1不激活
//                            .field("type", "integer")
//                        .endObject()
//                        .startObject("active_time")
//	                        .field("type", "long")
//                        .endObject()
//                    .endObject()
//                .endObject();
//
//        CreateIndexRequestBuilder prepareCreate = client.admin().indices().prepareCreate("vehicle_list");
//        prepareCreate.setSettings(settings_map).addMapping("vehicle", builder).get();
//    }
//    
//    @Test
//    public void testSettingsVehicleInfoMappings() throws IOException {
//        //1:settings
//        HashMap<String, Object> settings_map = new HashMap<String, Object>(2);
//        settings_map.put("number_of_shards", 3);
//        settings_map.put("number_of_replicas", 1);
//
//        //2:mappings
//        XContentBuilder builder = XContentFactory.jsonBuilder()
//                //开始设置
//        		.startObject()//
//                    .field("dynamic", "true")
//                    //只设置字段 不传参
//                    .startObject("properties")
//                        .startObject("id")
//                            .field("type", "text")
//                            .field("index", "true")
//                            //.field("store", "yes")
//                        .endObject()
//                        .startObject("current_time")
//                            .field("type", "long")
//                        .endObject()
//                        .startObject("current_speed")
//                            .field("type", "double")
//                        .endObject()
//                        .startObject("longitude")
//                            .field("type", "text")
//                        .endObject()
//                        .startObject("latitude")
//	                        .field("type", "text")
//                        .endObject()
//                    .endObject()
//                .endObject();
//
//        CreateIndexRequestBuilder prepareCreate = client.admin().indices().prepareCreate("vehicle_info");
//        prepareCreate.setSettings(settings_map).addMapping("vehicle", builder).get();
//
//    }
//
//    @Test
//    public void testDeleteIndex() {
//		IndicesAdminClient indicesAdminClient = client.admin().indices();
//		DeleteIndexResponse response = indicesAdminClient
//				.prepareDelete("vehicle_info").execute().actionGet();
//		System.out.println(response.isAcknowledged());
//	}
//    
//    @Test
//    public void testGetIndex() {
//    	GetSettingsResponse response = client.admin().indices()
//    	        .prepareGetSettings("vehicle_info", "vehicle").get();                           
//    	for (ObjectObjectCursor<String, Settings> cursor : response.getIndexToSettings()) { 
//    	    String index = cursor.key;                                                      
//    	    Settings settings = cursor.value;
//    	    System.out.println(index + "----" + settings.toString());
//    	    Integer shards = settings.getAsInt("index.number_of_shards", null);             
//    	    Integer replicas = settings.getAsInt("index.number_of_replicas", null);         
//    	}
//    }
//     
//}
