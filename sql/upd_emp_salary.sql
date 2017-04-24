DROP PROCEDURE IF EXISTS upd_emp_salary;

DELIMITER $$
CREATE PROCEDURE upd_emp_salary (
	IN p_salary	decimal(10,2),
	IN p_empno 	decimal(4)
)
BEGIN
	UPDATE employee SET salary = p_salary WHERE empno = p_empno;
END
