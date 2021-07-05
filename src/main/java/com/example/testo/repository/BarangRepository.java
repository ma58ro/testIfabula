package com.example.testo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.testo.entity.Barang;

@Repository
public interface BarangRepository extends JpaRepository<Barang, Long>, JpaSpecificationExecutor<Barang>{

}
