package com.heyutang.blog.dao;

import com.heyutang.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);

    Type findByName(String name);
}
