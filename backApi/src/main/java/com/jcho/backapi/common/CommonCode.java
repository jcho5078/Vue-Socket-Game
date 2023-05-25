package com.jcho.backapi.common;

public class CommonCode {

    public static final String JWT_KEY = "JsonKeyDJKAMCLSWNDSLDLSFMAFNFDHSJKSNCJQUEYTRNBXMCVSKZNMCQWEFSADCVZFNFDHSJKSNCJQUEYTRNBXMCNMCQWEFSADCLSHSJKSNCJQUEYTRNBMCQWEFSADC";
    public static final String JWT_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String[] ALLOW_URLS = {"/user/login", "/user/signUp", "/user/info", "/board/list", "/board/view"};
    public static final String[] AUTH_URLS = {"/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**", "swagger-ui/index.html"};
}
