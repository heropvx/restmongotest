package springtests.restmongotest;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProductRepository extends MongoRepository<Product, String> {

	public Product findByName(String name);
	
	@Query(fields = "{ 'description':0, 'params':0 }")
	public List<Product> findByNameLike(String name);
	
	@Query(value = "{ ?0:?1 }")
	public List<Product> findAllByKeyAndValue(String key, String value);
}
