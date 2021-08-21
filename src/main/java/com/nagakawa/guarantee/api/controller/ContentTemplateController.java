package com.nagakawa.guarantee.api.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.nagakawa.guarantee.api.util.PaginationUtil;
import com.nagakawa.guarantee.model.dto.ContentTemplateDTO;
import com.nagakawa.guarantee.service.ContentTemplateService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/content-template/admin")
@RequiredArgsConstructor
public class ContentTemplateController {

	private final ContentTemplateService contentTemplateService;

	@GetMapping("/all")
	@PreAuthorize("hasPrivilege('CONTENTTEMPLATE_READ')")
	public ResponseEntity<List<ContentTemplateDTO>> getAllConttentTemplate(Pageable pageable) {
		Page<ContentTemplateDTO> contentTemplateList = contentTemplateService.getAllContentTemplate(pageable);

		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
				ServletUriComponentsBuilder.fromCurrentRequestUri(), contentTemplateList);

		return new ResponseEntity<List<ContentTemplateDTO>>(contentTemplateList.getContent(), headers, HttpStatus.OK);
	}

	@GetMapping("/search")
	@PreAuthorize("hasPrivilege('CONTENTTEMPLATE_READ')")
	public ResponseEntity<ContentTemplateDTO> getContenTemplateByName(@RequestParam(name = "code") String code) {

		ContentTemplateDTO contentTemplate = contentTemplateService.findTemplateByTemplateCode(code, 1);

		return ResponseEntity.ok().body(contentTemplate);

	}

	@PostMapping("/update")
	@PreAuthorize("hasPrivilege('CONTENTTEMPLATE_WRITE')")
	public ResponseEntity<ContentTemplateDTO> update(@RequestBody ContentTemplateDTO contentTemplate) {
		ContentTemplateDTO tmp = contentTemplateService.update(contentTemplate);

		return ResponseEntity.ok().body(tmp);
	}
}
