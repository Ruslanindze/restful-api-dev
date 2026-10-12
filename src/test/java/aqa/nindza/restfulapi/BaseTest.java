package aqa.nindza.restfulapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.databind.ObjectMapper;


@SpringBootTest
@Slf4j
public abstract class BaseTest {

    @Autowired
    protected ObjectMapper mapper;
}
