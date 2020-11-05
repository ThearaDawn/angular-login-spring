package com.ums.models.dao;

import com.ums.models.entity.Client;
import com.ums.models.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientDao extends JpaRepository<Client, Long>{

	@Query("from Region")
	public List<Region> findAllRegion();
}
