package com.yedam.app.emp.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data

/*@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode*/

public class EmpVO {
	private Integer employeeId; // null 값 처리
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date hireDate;
	private String jobId;
	private double salary;
	private double commissionPct;
	private int managerId;
	private int deparmentId;
}