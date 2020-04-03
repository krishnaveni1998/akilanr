package com.sample.ms.expenditurecrudservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ExpenditureRepository extends JpaRepository<Expenditure, Integer>{
	
	Expenditure findByExpenditureIdAndDeleteStatus(int expenditureId, boolean deleteStatus);
	
}