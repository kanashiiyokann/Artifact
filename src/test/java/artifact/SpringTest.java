package artifact;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringTest {

//    @Resource
//    private Mongot userService;

    @Test
    public void doTest() {

//        List<User> users=userService.list(null,"age nulls_last");
//        System.out.println(users.toString());
    }

    public void doTest2(){

//        BoolQueryBuilder match = QueryBuilders.boolQuery();
//        match.must(QueryBuilders.termQuery("companyId.keyword", "123"));
//
//
//        SearchSourceBuilder search = new SearchSourceBuilder();
//        search.query(match);
//        search.from(0).size(10000);
//        TermsAggregationBuilder group = AggregationBuilders.terms("groupAlias").field("subjectName.keyword");
//        SumAggregationBuilder sum1 = AggregationBuilders.sum("sumAlias1").field("debitAmount");
//        SumAggregationBuilder sum2 = AggregationBuilders.sum("sumAlias2").field("creditAmount");
//        group.subAggregation(sum1);
//        group.subAggregation(sum2);
//        search.aggregation(group);
//
//        SearchResponse response = esTemplate.query(search, EsContant.TYPE_VOUCHER_DETAIL, EsContant.INDEX_VOUCHER_DETAIL + EsIndexManager.getIndex());
//        Terms terms = response.getAggregations().get("groupAlias");
//        for (Terms.Bucket bucket : terms.getBuckets()) {
//            String key = bucket.getKey().toString();
//            Sum inSum = bucket.getAggregations().get("sumAlias1");
//            Sum outSum = bucket.getAggregations().get("sumAlias2");
//            System.out.println(String.format("key:%s debit:%s credit:%s", key, ((ParsedSum) inSum).getValue(), ((ParsedSum) outSum).getValue()));
//
//        }
//    }

}






}
