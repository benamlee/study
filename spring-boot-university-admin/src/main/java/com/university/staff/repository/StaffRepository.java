package com.university.staff.repository;

import com.university.staff.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Spring Data JPA 自动生成实现类
// 不需要写任何 CRUD 代码！
public interface StaffRepository extends JpaRepository<Staff, Long> {
    // JpaRepository<Staff, Long> 继承自：
    //   CrudRepository   ← 基础 CRUD
    //   PagingAndSortingRepository ← 分页排序
    //   QueryByExampleExecutor ← 按示例查询
    //
    // 因此 StaffRepository 拥有全套方法：
    //   findAll() → List<Staff>
    //   findById(Long id) → Optional<Staff>
    //   save(Staff) → Staff
    //   deleteById(Long id)
    //   count() → long
    //   ...
    //
    // 如果需要按部门查询，只需加一行：
    //   List<Staff> findByDepartment(String department);
    // Spring Data JPA 自动解析方法名生成 JPQL！
}
