package org.example.items_k8s_service.application.infrastructure.persistence;

import org.example.items_k8s_service.application.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> 
{
    
}
