package org.example;

import org.example.calculator.domain.Calculator;
import org.example.calculator.domain.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CustomWebApplicationServer {
    private final int port; // port 값
    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);
    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(port)) { // 해당하는 포트로 서버를 띄운다.
            logger.info("[CustomWebApplicationServer] started port.", port);

            Socket clientSocket; // 클라이언트 소켓을 대기로 한다.
            logger.info("[CustomWebApplicationServer] waiting for client.");

            while ((clientSocket = serverSocket.accept()) != null) { // 클라이언트 소켓이 null이 아니면
                logger.info("[CustomWebApplicationServer] client connected!"); // 클라이언트가 연결되었다는 의미이다.

                /*
                 * Step 1 - 사용자 요청을 메인 Thread가 처리하도록 한다.
                 */

                try(InputStream in = clientSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                    // InputStream을 Reader로 바꾸기 위해서는 BufferedReader가 필요하다.
                    // 바꾸는 이유는 Line By Line 으로 읽기 위해서이다.
                    DataOutputStream dos = new DataOutputStream(out);

                    HttpRequest httpRequest = new HttpRequest(br);

                    if (httpRequest.isGetRequest() && httpRequest.matchPath("/calculate")) {
                        QueryStrings queryStrings = httpRequest.getQueryString();

                        int operand1 = Integer.parseInt(queryStrings.getValue("operand1"));
                        String operator = queryStrings.getValue("operator");
                        int operand2 = Integer.parseInt(queryStrings.getValue("operand2"));

                        int result = Calculator.calculate(new PositiveNumber(operand1), operator, new PositiveNumber(operand2));
                        byte[] body = String.valueOf(result).getBytes();

                        HttpResponse response = new HttpResponse(dos);
                        response.response200Header("application/json", body.length); // 헤더값 설정
                        response.responseBody(body);
                    }

                }
            }
        }
    }
}
