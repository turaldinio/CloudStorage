package com.guluev.cloudstorage.repository;

import com.guluev.cloudstorage.entity.UserFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository  extends JpaRepository<UserFiles,Long> {
}
