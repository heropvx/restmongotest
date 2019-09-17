package springtests.restmongotest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Product> searchProducts(@RequestParam(name = "name", defaultValue = "") String name, 
			@RequestParam(name = "paramName", defaultValue = "") String paramName, 
			@RequestParam(name = "paramValue", defaultValue = "") String paramValue) 
	{
		Criteria criteria = new Criteria();
		
		if (!name.isEmpty()) { 
			criteria.and("name").regex(name);
		}
		
		if (!paramName.isEmpty()) {		
			criteria.and("params." + paramName).is(paramValue);
		}
		
		Query query = new Query(criteria);
		query.fields().include("id").include("name");
		
		return mongoTemplate.find(query, Product.class);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Product getProductById(@PathVariable("id") String id) {
		return repository.findById(id).get();
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void createOrUpdateProduct(@RequestBody Product product) {
		repository.save(product);
	}
	
	
}
