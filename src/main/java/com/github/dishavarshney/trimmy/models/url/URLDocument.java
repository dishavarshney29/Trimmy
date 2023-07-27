package com.github.dishavarshney.trimmy.models.url;

import com.github.dishavarshney.trimmy.constants.UrlStatus;
import com.github.dishavarshney.trimmy.models.AbstractDocument;
import com.github.dishavarshney.trimmy.models.PrePersistListener;
import com.github.dishavarshney.trimmy.models.PreUpdateListener;
import com.github.dishavarshney.trimmy.utils.Utils;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Version;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

@Document(collection = "urls")
@Getter
@ToString
public class URLDocument extends AbstractDocument implements PrePersistListener, PreUpdateListener {

	@NotNull
	@Setter
	private String originalUrl;

	@Setter
	@Indexed(unique = true)
	private String shortUrlKey;

	@Setter
	@Indexed
	private UrlStatus status;

	private Date createdAt;

	private Date updatedAt;

	private String createdBy;

	private String updatedBy;

	@Setter
	@Field
	@Indexed
	@NonNull
	@NotNull
	private Date validTill;

	@Version
	private Long version;

	@Setter
	@Indexed(unique = true, sparse = true)
	private String customShortUrl;

	public URLDocument() {
		originalUrl = null;
	}

	public URLDocument(String originalUrl, String customShortUrl, Date validTill) {
		this.originalUrl = originalUrl;
		this.customShortUrl = customShortUrl;
		this.validTill = validTill;
	}

	public URLDocument(String originalUrl, Date validTill) {
		this.originalUrl = originalUrl;
		this.validTill = validTill;
	}

	@Override
	public void onPrePersist() {
		status = UrlStatus.ACTIVE;
		createdBy = Utils.getUserPrincipal();
		createdAt = Timestamp.from(Instant.now());
	}

	@Override
	public void onPreUpdate() {
		updatedBy = Utils.getUserPrincipal();
		updatedAt = Timestamp.from(Instant.now());
	}

	public HashMap getReturn(HttpServletRequest request) {
		HashMap lHashMap = new HashMap();
		lHashMap.put("originalUrl", originalUrl);
		lHashMap.put("shortenUrl", Utils.getShortUrl(request, shortUrlKey));
		lHashMap.put("user", createdBy);
		lHashMap.put("validTill", validTill.toString());
		lHashMap.put("status", status.name());
		return lHashMap;
	}
}