package com.quitq.ECom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quitq.ECom.model.Warehouse;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Integer>
{
    Warehouse findByCity(String city);
}