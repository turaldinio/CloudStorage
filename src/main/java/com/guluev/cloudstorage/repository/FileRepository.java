package com.guluev.cloudstorage.repository;

import com.guluev.cloudstorage.entity.UserFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository  extends JpaRepository<UserFiles,Long> {
    int deleteByFileNameAndUserId(String fileName, long userId);

    Optional<UserFiles> getByFileNameAndUserId(String fileName, long userId);

    @Modifying
    @Query(value = "update UserFiles uf set uf.fileName=?2 where uf.fileName=?1 and uf.userId=?3")
    int update(String oldName, String newName, long userId);
}
