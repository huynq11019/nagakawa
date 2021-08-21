package com.nagakawa.guarantee.service.impl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nagakawa.guarantee.api.exception.BadRequestAlertException;
import com.nagakawa.guarantee.api.util.ApiConstants;
import com.nagakawa.guarantee.messages.LabelKey;
import com.nagakawa.guarantee.messages.Labels;
import com.nagakawa.guarantee.model.ContentTemplate;
import com.nagakawa.guarantee.model.dto.ContentTemplateDTO;
import com.nagakawa.guarantee.repository.ContentTemplateRepository;
import com.nagakawa.guarantee.service.ContentTemplateService;
import com.nagakawa.guarantee.service.mapper.ContentTemplateMapper;
import com.nagakawa.guarantee.util.Constants.EntityStatus;
import com.nagakawa.guarantee.util.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ContentTemplateServiceImpl implements ContentTemplateService {

	private final ContentTemplateRepository contentTemplateRepository;

	private final ContentTemplateMapper contentTemplateMapper;

	@Override
	public Page<ContentTemplateDTO> getAllContentTemplate(Pageable pageable) {
		List<ContentTemplate> listRepo =
				contentTemplateRepository.search(EntityStatus.DELETED, pageable);

		List<ContentTemplateDTO> listResult = this.contentTemplateMapper.toDto(listRepo);

		return new PageImpl<ContentTemplateDTO>(listResult, pageable,
				this.contentTemplateRepository.count(EntityStatus.DELETED));
	}

	@Override
	public ContentTemplateDTO findTemplateByTemplateCode(String templateCode, int status) {
		if (Validator.isNull(templateCode)) {
			return null;
		}

		return this.contentTemplateMapper
				.toDto(contentTemplateRepository.findContentTemplateByTemplateCodeAndStatus(templateCode, status));
	}

	@Override
	public ContentTemplateDTO update(ContentTemplateDTO contentTemplateDTO) {
		if (!this.contentTemplateRepository.existsById(contentTemplateDTO.getTemplateCode())) {
			throw new BadRequestAlertException(LabelKey.ERROR_DATA_DOES_NOT_EXIST,
					ApiConstants.EntityName.CONTENT_TEMPLATE, Labels.getLabels(LabelKey.ERROR_DATA_DOES_NOT_EXIST));
		}

		if (Validator.isNull(contentTemplateDTO.getTemplateCode())) {
			throw new BadRequestAlertException(LabelKey.ERROR_INPUT_CANNOT_BE_EMPTY,
					ApiConstants.EntityName.CONTENT_TEMPLATE, Labels.getLabels(LabelKey.ERROR_INPUT_CANNOT_BE_EMPTY));
		}

		if (Validator.isNull(contentTemplateDTO.getName())) {
			throw new BadRequestAlertException(LabelKey.ERROR_INPUT_CANNOT_BE_EMPTY,
					ApiConstants.EntityName.CONTENT_TEMPLATE, Labels.getLabels(LabelKey.ERROR_INPUT_CANNOT_BE_EMPTY));
		}

		if (Validator.isNull(contentTemplateDTO.getStatus())) {
			throw new BadRequestAlertException(LabelKey.ERROR_INPUT_CANNOT_BE_EMPTY,
					ApiConstants.EntityName.CONTENT_TEMPLATE, Labels.getLabels(LabelKey.ERROR_INPUT_CANNOT_BE_EMPTY));
		}

		ContentTemplate contentTemplate = this.contentTemplateMapper.toEntity(contentTemplateDTO);

		return this.contentTemplateMapper.toDto(this.contentTemplateRepository.save(contentTemplate));
	}

}
