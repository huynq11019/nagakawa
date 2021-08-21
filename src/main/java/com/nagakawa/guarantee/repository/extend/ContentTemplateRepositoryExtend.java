package com.nagakawa.guarantee.repository.extend;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.nagakawa.guarantee.model.ContentTemplate;

public interface ContentTemplateRepositoryExtend {
	List<ContentTemplate> search(int status, Pageable pageable);

	Long count(int status);
}
