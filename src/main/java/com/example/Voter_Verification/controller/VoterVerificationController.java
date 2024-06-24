package com.example.Voter_Verification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Voter_Verification.Entity.VoterRequest;
import com.example.Voter_Verification.Service.VoterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/saswat")
public class VoterVerificationController {

	@Autowired
	VoterService service;

	@PostMapping("/voter-id/verification")
	public String getVerify(@RequestBody VoterRequest dto, HttpServletRequest request1, HttpServletResponse response)  {
		return service.getVerify(dto,request1,response);
	}

}
