package com.marcus.profile.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.marcus.profile.entity.UserProfile;

public interface UserProfileRepository extends Neo4jRepository<UserProfile, String> {}
