package aqa.nindza.restfulapi.tests;

import aqa.nindza.restfulapi.BaseTest;
import aqa.nindza.restfulapi.infra.clients.RestfulApiPublicClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class GetObjectTest extends BaseTest {
    @Autowired
    private RestfulApiPublicClient restfulApiPublicClient;

    @Test
    void test() {
        var id = "1";
        var response = restfulApiPublicClient.getObject(id);

        assertThat(response).isNotNull();
    }
}
