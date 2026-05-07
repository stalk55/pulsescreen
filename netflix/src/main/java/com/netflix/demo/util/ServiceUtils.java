package com.netflix.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.demo.dao.UserRepository;
import com.netflix.demo.dao.VideoRepository;
import com.netflix.demo.entity.User;
import com.netflix.demo.entity.Video;
import com.netflix.demo.exception.ResourceNotFoundException;

@Service
public class ServiceUtils {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    public User getUserByEmailOrThrow(String email){
        return userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " +email));
    }

    public User getUserByIdOrThrow(Long id){
        return userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id:"+id));
    }

    public Video getVideoByIdOrThrow(Long id){
        return videoRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Video not found with id"+id));
    }

}
