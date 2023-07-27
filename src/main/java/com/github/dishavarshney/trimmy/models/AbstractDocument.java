package com.github.dishavarshney.trimmy.documents;


import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@ToString
public abstract class AbstractDocument {

	@Id
	private String id;
	
}
