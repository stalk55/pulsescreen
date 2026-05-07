package com.netflix.demo.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "videos")
@Data
@Getter
@Setter
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titel;

    @Column(length = 4000)
    private String description;

    @Column(name = "poster")
    @JsonIgnore
    private String posterVvid;

    private Integer year;
    private String rating;
    private Integer duration;

    @Column(name = "src")
    @JsonIgnore
    private String srcVvid;

    @Column(nullable = false)
    private boolean published = false;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "video_categories" , joinColumns = @JoinColumn(name = "video_id"))
    @Column(name = "category")
    private List<String> categories = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false , updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    @Transient
    @JsonProperty("isInWatchList")
    private Boolean isInWatchList;

    @JsonProperty("src")
    public String getSrc(){
        if(srcVvid != null && !srcVvid.isEmpty()){
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
            return baseUrl+"/api/files/video/"+srcVvid;
        }
        return null;
    }

    @JsonProperty("poster")
    public String getPoster(){

        if (posterVvid == null && !posterVvid.isEmpty()) {
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
            return baseUrl+"/api/files/image"+srcVvid;
        }
        return null;
    }

}
