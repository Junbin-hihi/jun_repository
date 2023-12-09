package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.Data;

/*
 * Lombok(롬복)
 * - 자동 코드 생성 라이브러리
 * - 반복되는 getter, setter, toString등의 메소드 작성코드를 줄여주는 코드 다이어트 라이브러리
 * 
 * Lombok설치방법
 * 1. 라이브러리 다운 후 적용(Maven pom.xml)
 * 2. 다운로드 된 jar파일을 찾아서 설치(작업할 IDE 체크)
 * 3. IDE 재실행
 * 
 * @NoArgsConstructor == 기본생성자 만들어주는 애노테이션
 * @AllArgsConstrutor == 모든 매개변수를 가진 생성자
 * @Getter == getter
 * @Setter == setter
 * @ToString == toString
 * @Data == 싹다 만들어줌( 단 , 매개변수를 가진 생성자는 안만들어줌)
 * 
 * Lombok 사용 시 주의사항
 * - uName, bTitle 같이 앞글자가 외자인 필드명은 만들지 않는것이 원칙임.
 * 
 * => EL표기법을 이용할 때 내부적으로 getter를 찾을 때!!
 * getuName(), getbTitle() 이라는 메소드를 내부적으로 찾아서 호출하게됨.
 * ${ loginUser.uName }
 * 내부적으로는 loginUser.uName == loginUser.getuName();
 * 
 * Lombok에서는 uName(외자)로 만들게 되면 getUName()으로 만들게됨. / getBTitle()
 * Lombok이 만든 getter를 EL구문이 찾을 수 없게됨.
 * 즉, 필드명 작성 시 최소 소문자 두글자 이상으로 시작할것!
 */

@Data
public class Member {
	
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String gender;
	private String age;
	private String phone;
	private String address;
	private Date enrollDate;
	private Date modifyDate;
	private String status;
	
}
