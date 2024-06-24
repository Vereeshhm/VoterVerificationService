package com.example.Voter_Verification.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Voter_Verification.Utils.ApiLog;

public interface ApiLogRepository extends JpaRepository<ApiLog, String> {

}
