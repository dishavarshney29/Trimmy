package com.github.dishavarshney.trimmy.documents.url;

import com.github.dishavarshney.trimmy.constants.UrlStatus;
import com.github.dishavarshney.trimmy.documents.AbstractDocument;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document("urls")

@Getter
@ToString
@RequiredArgsConstructor
public class URLDocument extends AbstractDocument {
	
	@NotNull
	private final String originalUrl;
	
	@Setter
	@Indexed(unique = true)
	private String shortUrlKey;
	
	@Setter
	@Indexed
	private UrlStatus status;
	
	private Date createdAt = new Date();
	
	@Setter
	@Field
	@Indexed
	@NonNull
	@NotNull
	private Date validTill;
	
	@Setter
	private Date expiredAt;

}
