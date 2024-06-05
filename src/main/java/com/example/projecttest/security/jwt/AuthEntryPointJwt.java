package com.example.projecttest.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Đoạn mã này định nghĩa một lớp AuthEntryPointJwt được sử dụng để xử lý các trường hợp khi người dùng
// không được phép truy cập (unauthorized access) trong một ứng dụng Spring Boot sử dụng Spring Security
// và JWT (JSON Web Token). Lớp này thực hiện giao diện AuthenticationEntryPoint của Spring Security.
@Component //@Component: Đánh dấu lớp này như một bean Spring để Spring có thể quản lý nó và sử dụng ở những nơi cần thiết.

// AuthEntryPointJwt implements AuthenticationEntryPoint: Lớp này sẽ định nghĩa cách xử lý khi một người dùng không được phép truy cập tài nguyên.
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);


  // commence là phương thức được ghi đè từ AuthenticationEntryPoint. Phương thức này sẽ được gọi khi một
  // ngoại lệ xác thực xảy ra (ví dụ, khi người dùng không cung cấp JWT hợp lệ).
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
          throws IOException, ServletException {
    logger.error("Unauthorized error: {}", authException.getMessage()); // Ghi lại thông tin lỗi vào log với mức độ error. Thông điệp log bao gồm thông điệp từ ngoại lệ authException.

    response.setContentType(MediaType.APPLICATION_JSON_VALUE); // Đặt kiểu nội dung của phản hồi là JSON.
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Đặt mã trạng thái HTTP của phản hồi là 401 (Unauthorized).

    final Map<String, Object> body = new HashMap<>(); // Tạo một map body để chứa các thông tin chi tiết về lỗi
    body.put("status", HttpServletResponse.SC_UNAUTHORIZED); // Mã trạng thái HTTP (401)
    body.put("error", "Unauthorized"); // Chuỗi thông báo lỗi ("Unauthorized").
    body.put("message", authException.getMessage()); // Thông điệp lỗi từ ngoại lệ authException
    body.put("path", request.getServletPath()); // Đường dẫn của yêu cầu HTTP gây ra lỗi.

    final ObjectMapper mapper = new ObjectMapper(); // Tạo một ObjectMapper từ thư viện Jackson để chuyển đổi body thành JSON.
    mapper.writeValue(response.getOutputStream(), body); // Ghi đối tượng body dưới dạng JSON vào output stream của phản hồi HTTP.
  }
}
