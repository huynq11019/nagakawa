package com.nagakawa.guarantee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nagakawa.guarantee.model.ContentTemplate;
import com.nagakawa.guarantee.repository.extend.ContentTemplateRepositoryExtend;

@Repository
public interface ContentTemplateRepository
		extends JpaRepository<ContentTemplate, String>, ContentTemplateRepositoryExtend {
	ContentTemplate findContentTemplateByTemplateCodeAndStatus(String templateCode, int status);

}
