package com.mybatis3.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mybatis3.domain.Student;

/**
 * @author Siva
 *
 */
public interface StudentMapper
{
	
	@Select("select * from STUDENTS")
	@Results({
			@Result(id=true, column="stud_id", property="studId"),
			@Result(column="name", property="name"),
			@Result(column="email", property="email"),
			@Result(column="addr_id", property="address.addrId")			
	})
	List<Student> findAllStudents();

	@Select("select stud_id as studId, name, email, addr_id as 'address.addrId', phone from STUDENTS")
	List<Map<String,Object>> findAllStudentsMap();
	
	@Select("select stud_id as studId, name, email, addr_id as 'address.addrId', phone from STUDENTS where stud_id=#{id}")
	Student findStudentById(Integer id);
	
	@Select("select stud_id as studId, name, email, addr_id as 'address.addrId', phone from STUDENTS where stud_id=#{id}")
	Map<String,Object> findStudentMapById(Integer id);

	@Select("select stud_id, name, email, a.addr_id, street, city, state, zip, country"+
  		" FROM STUDENTS s left outer join ADDRESSES a on s.addr_id=a.addr_id"+
		" where stud_id=#{studId} ")
	@ResultMap("com.mybatis3.mappers.StudentMapper.StudentWithAddressResult")
	Student selectStudentWithAddress(int studId);
	
	@Insert("insert into STUDENTS(name,email,addr_id, phone) values(#{name},#{email},#{address.addrId},#{phone})")
	@Options(useGeneratedKeys=true, keyProperty="studId")
	void insertStudent(Student student);
	
	@Insert("insert into STUDENTS(name,email,addr_id, phone) values(#{name},#{email},#{address.addrId},#{phone})")
	@Options(useGeneratedKeys=true, keyProperty="studId")
	void insertStudentWithMap(Map<String, Object> map);

	@Update("update STUDENTS set name=#{name}, email=#{email}, phone=#{phone} where stud_id=#{studId}")
	void updateStudent(Student student);
	
	@Delete("delete from STUDENTS where stud_id=#{studId}")
	int deleteStudent(int studId);
	
}
