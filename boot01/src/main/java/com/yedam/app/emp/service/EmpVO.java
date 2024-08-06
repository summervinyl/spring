package com.yedam.app.emp.service;

import java.util.Date;

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
	private Date hireDate;
	private String jobId;
	private double salary;
	private double commissionPct;
	private int managerId;
	private int deparmentId;
}