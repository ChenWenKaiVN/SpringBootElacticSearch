//package com.example.demo;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.http.Header;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.Requests;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.stereotype.Service;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
////@Service
//public class VehicleService {
//	
//    @Autowired
//    RestHighLevelClient highLevelClient;
//
//
//    /**
//     * 使用方式
//     *
//     * @return test is passed
//     */
//    //@Test
//    public boolean testEsRestClient() {
//        SearchRequest searchRequest = new SearchRequest("vehicle_info");
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(QueryBuilders.termQuery("id", "京A25906"));
//        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        searchRequest.source(sourceBuilder);
//        try {
//			SearchResponse response = highLevelClient.search(searchRequest);
//            Arrays.stream(response.getHits().getHits())
//                    .forEach(i -> {
//                        log.info(i.getIndex());
//                        log.info(i.getSourceAsString());
//                        log.info(i.getType());
//                    });
//            log.info("total:{}", response.getHits().totalHits);
//            return true;
//        } catch (IOException e) {
//            log.error("test failed", e);
//            return false;
//        }
//    }
//
//}
