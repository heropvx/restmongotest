package springtests.restmongotest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Document(collection = "items")
@JsonSerialize(using = Product.CustomSerializer.class)
@JsonDeserialize(using = Product.CustomDeserializer.class)
public class Product {
	public static class CustomSerializer extends JsonSerializer<Product> {
		@Override
		public void serialize(Product p, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeStartObject();
			gen.writeStringField("id", p.id);
			gen.writeStringField("name", p.name);			
			if (p.description != null)
				gen.writeStringField("description", p.description);
			if (p.params != null) {
				gen.writeArrayFieldStart("params");
				for (Map.Entry<String, String> e : p.params.entrySet())
					gen.writeObject(e);
				gen.writeEndArray();
			}
			gen.writeEndObject();
		}
	}
	public static class CustomDeserializer extends JsonDeserializer<Product> {
		@Override
		public Product deserialize(JsonParser p, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			JsonNode node = p.getCodec().readTree(p);
			Product pr = new Product();
			node.fields().forEachRemaining(field -> {
				if (field.getKey().equals("id")) pr.id = field.getValue().asText();
				if (field.getKey().equals("name")) pr.name = field.getValue().asText();
				if (field.getKey().equals("description")) pr.description = field.getValue().asText();
				if (field.getKey().equals("params")) {
					pr.params = new HashMap<>();
					field.getValue().elements().forEachRemaining(item -> {
						Map.Entry<String, JsonNode> entry = item.fields().next();
						pr.params.put(entry.getKey(), entry.getValue().asText());
					});
				}
				
			});			
			return pr;
		}
	}
	
	@Id
	private String id;
	
	private String name;
	
	private String description;
	
	private Map<String, String> params;	
	
	public Product() {
	}

	public Product(String name, String description) {
		this(name, description, null);
	}

	public Product(String name, String description, Map<String, String> params) {
		super();
		this.name = name;
		this.description = description;
		this.params = params;
	}

	public Product addParam(String key, String value) {
		if (params == null) params = new HashMap<>();
		params.put(key, value);
		return this;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	
}
