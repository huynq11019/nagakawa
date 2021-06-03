/**
 * Evotek QLNS
 */
package com.nagakawa.guarantee.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagakawa.guarantee.model.FileEntry;
import com.nagakawa.guarantee.repository.extend.FileEntryRepositoryExtend;

/**
 * @author LinhLH
 *
 */
@Repository
public interface FileEntryRepository extends JpaRepository<FileEntry, Long>, FileEntryRepositoryExtend {
	List<FileEntry> findByClassNameAndClassPk(String className, Long classPk);
	
	List<FileEntry> findByIdIn(Collection<Long> ids);
}
