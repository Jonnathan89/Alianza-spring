/**
 * 
 */
package com.micro.alianza.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micro.alianza.client.entity.Client;

/**
 * @author psych
 *
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

}
