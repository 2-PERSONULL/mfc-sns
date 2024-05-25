package com.mfc.sns.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseResponseStatus {

	/**
	 * 200: 요청 성공
	 **/
	SUCCESS(HttpStatus.OK, true, 200, "요청에 성공했습니다."),

	/**
	 * 400 : security 에러
	 */
	WRONG_JWT_TOKEN(HttpStatus.UNAUTHORIZED, false, 401, "다시 로그인 해주세요"),

	// Members
	DUPLICATED_NICKNAME(HttpStatus.CONFLICT, false, 2100, "사용중인 닉네임입니다."),
	DUPLICATED_MEMBERS(HttpStatus.CONFLICT, false, 409, "이미 가입된 멤버입니다."),
	MASSAGE_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, false, 2102, "인증번호 전송에 실패했습니다."),
	MESSAGE_VALID_FAILED(HttpStatus.UNAUTHORIZED, false, 401, "인증번호가 일치하지 않습니다."),
	FAILED_TO_LOGIN(HttpStatus.UNAUTHORIZED, false, 401, "아이디 또는 패스워드를 다시 확인하세요."),
	WITHDRAWAL_MEMBERS(HttpStatus.FORBIDDEN, false, 2105, "탈퇴한 회원입니다."),
	NO_EXIT_ROLE(HttpStatus.BAD_REQUEST, false, 400, "잘못된 접근입니다."),
	NO_REQUIRED_HEADER(HttpStatus.BAD_REQUEST, false, 400, "헤더에 UUID 혹은 Role이 존재하지 않습니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, false, 404, "존재하지 않는 회원입니다.");


	private final HttpStatusCode httpStatusCode;
	private final boolean isSuccess;
	private final int code;
	private final String message;

}