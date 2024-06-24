package com.example.Voter_Verification.Service;

import com.example.Voter_Verification.Entity.VoterRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface VoterService {

	public String getVerify(VoterRequest dto, HttpServletRequest request1, HttpServletResponse response) ;

}
