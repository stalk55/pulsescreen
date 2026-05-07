package com.netflix.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netflix.demo.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long>{

}
