/**
 * Evotek QLNS
 */
package com.nagakawa.guarantee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagakawa.guarantee.model.FolderEntry;
import com.nagakawa.guarantee.repository.extend.FolderEntryRepositoryExtend;

/**
 * @author LinhLH
 *
 */
@Repository
public interface FolderEntryRepository extends JpaRepository<FolderEntry, Long>, FolderEntryRepositoryExtend {
}
