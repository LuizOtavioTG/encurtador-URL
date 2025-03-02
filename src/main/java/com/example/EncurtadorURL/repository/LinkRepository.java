package com.example.EncurtadorURL.repository;

import com.example.EncurtadorURL.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByUrlShort(String urlShort);


}
