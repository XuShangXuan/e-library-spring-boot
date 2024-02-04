package com.esunbank.e_library_spring_boot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esunbank.e_library_spring_boot.entity.Inventory;

@Repository
public interface InventoryInfoDao extends JpaRepository<Inventory, Long> {
	
	List<Inventory> findByInventoryIDAndStatus(long inventoryID, String status);
	
}
