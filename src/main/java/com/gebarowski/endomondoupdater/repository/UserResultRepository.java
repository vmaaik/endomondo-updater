package com.gebarowski.endomondoupdater.repository;


import com.gebarowski.endomondoupdater.model.UserResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResultRepository extends CrudRepository<UserResult, Long> {
    @Query(value = "SELECT sum(a.distance) FROM (SELECT distance FROM user_result ORDER BY created DESC LIMIT 15) a", nativeQuery = true)
    int countLatestTotal();

    @Query(value = "SELECT sum(distance) FROM (SELECT * FROM user_result WHERE endomondo_profile_id IN (33133062, 40942739, 28564030, 40996195, 16158521, 25196849) ORDER BY created DESC LIMIT 6) as a", nativeQuery = true)
    Double getGroupA();

    @Query(value = "SELECT sum(distance) FROM (SELECT * FROM user_result WHERE endomondo_profile_id IN (2090913,4435173, 16158883, 19943873, 41976560, 5850966, 16605548) ORDER BY created DESC LIMIT 6) as a", nativeQuery = true)
    Double getGroupB();
}
