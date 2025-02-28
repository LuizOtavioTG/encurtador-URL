package com.example.EncurtadorURL.repository;

import com.example.EncurtadorURL.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
}
