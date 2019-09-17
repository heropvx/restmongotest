package springtests.restmongotest;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestmongotestApplicationTests {

	@Test
	public void testPoductSerializers() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String productJsonString = "{\"id\":\"123\",\"name\":\"Nokia 3310\",\"description\":\"Nokia phone\"}";
		Product product = mapper.readValue(productJsonString, Product.class);
		System.out.print(product);
		Assert.assertEquals(productJsonString, mapper.writeValueAsString(product));
	}
	@Test
	public void testPoductSerializersWithParam() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String productJsonString = "{\"id\":\"123\",\"name\":\"Nokia 3310\",\"description\":\"Nokia phone\",\"params\":[{\"price\":\"100\"}]}";
		Product product = mapper.readValue(productJsonString, Product.class);
		Assert.assertEquals(productJsonString, mapper.writeValueAsString(product));
	}

//	@Autowired
//	private ProductRepository rep;
//	
//	@Test
//	public void testConnection() {
//		Product p1 = new Product("Nokia 3110", "Nokia phone")
//				.addParam("manufacturer", "Nokia")
//				.addParam("weight", "56")
//				.addParam("price", "100");
//		Product p12 = new Product("Nokia 6600", "Nokia phone")
//				.addParam("manufacturer", "Nokia")
//				.addParam("weight", "120")
//				.addParam("price", "144");
//		Product p2 = new Product("Samsung 2", "Samsung phone")
//				.addParam("manufacturer", "Samsung")
//				.addParam("price", "144");
//		Product p3 = new Product("SimplePhone", "Simple phone")
//				.addParam("manufacturer", "Simple")
//				.addParam("price", "80");
//		
//		rep.deleteAll();
//		
//		rep.save(p1);
//		rep.save(p12);
//		rep.save(p2);
//		rep.save(p3);
//		
//		Assert.assertEquals(rep.count(), 4);
//	}
}
