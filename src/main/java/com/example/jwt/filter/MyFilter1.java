package com.example.jwt.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFilter1 implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if(req.getMethod().equals("POST")){
			System.out.println("POST required");
			String headerAuth = req.getHeader("Authorization");
			System.out.println("FILTER_1");
			System.out.println(headerAuth);

			if (headerAuth.equals("cos")){  //정확하게 들어올 때
				chain.doFilter(request, response);
			}else{
				PrintWriter out = res.getWriter();
				out.println("fail");
				System.out.println("fail");
			}
		}
	}
}
