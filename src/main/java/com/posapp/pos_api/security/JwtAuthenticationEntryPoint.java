// package com.posapp.pos_api.security;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.web.AuthenticationEntryPoint;
// import org.springframework.stereotype.Component;
// import com.fasterxml.jackson.databind.ObjectMapper;

// import java.io.IOException;
// import java.util.HashMap;
// import java.util.Map;

// @Component
// public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

//     @Override
//     public void commence(HttpServletRequest request, HttpServletResponse response,
//                          AuthenticationException authException) throws IOException, ServletException {
//         response.setContentType("application/json");
//         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

//         Map<String, Object> responseBody = new HashMap<>();
//         responseBody.put("message", "Token tidak valid atau tidak ada!");
//         responseBody.put("status", 401);

//         // Mengirim respons JSON
//         response.getOutputStream().println(new ObjectMapper().writeValueAsString(responseBody));
//     }
// }
